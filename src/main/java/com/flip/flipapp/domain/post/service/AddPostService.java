package com.flip.flipapp.domain.post.service;

import com.flip.flipapp.domain.category.exception.CategoryNotFoundException;
import com.flip.flipapp.domain.category.model.Category;
import com.flip.flipapp.domain.category.repository.CategoryRepository;
import com.flip.flipapp.domain.post.model.Post;
import com.flip.flipapp.domain.post.model.PostState;
import com.flip.flipapp.domain.post.repository.PostRepository;
import com.flip.flipapp.domain.post.service.dto.AddPostCommand;
import com.flip.flipapp.domain.post_tag.model.PostTag;
import com.flip.flipapp.domain.post_tag.repository.PostTagRepository;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.tag.model.Tag;
import com.flip.flipapp.domain.tag.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddPostService {

  private final PostRepository postRepository;
  private final CategoryRepository categoryRepository;
  private final TagRepository tagRepository;
  private final PostTagRepository postTagRepository;

  @Transactional
  public Post addPost(AddPostCommand command) {
    Post newPost = Post.builder()
                       .title(command.title())
                       .content(command.content())
                       .fontStyle(command.fontStyleType())
                       .bgColor(command.bgColorType())
                       .likeCnt(0L)
                       .profile(Profile.getAuthenticatedProfile(command.profileId()))
                       .category(findCategory(command.categoryId()))
                       .postState(PostState.CREATED)
                       .build();

    Post savedPost = postRepository.save(newPost);

    // 태그가 없으면 저장하지 않음
    if (command.tags().isEmpty()) {
      return savedPost;
    }

    /*
     * 태그 중에 이미 존재하는 태그와 존재하지 않는 태그를 구분하여 저장
     */
    List<Tag> existTags = tagRepository.findTagsIn(command.tags());

    Set<String> existTagNames = existTags.stream()
                                         .map(Tag::getTagName)
                                         .collect(Collectors.toUnmodifiableSet());

    List<Tag> newTags = command.tags().stream()
                               .filter(tn -> !existTagNames.contains(tn))
                               .map(tn -> Tag.builder().tagName(tn).build())
                               .toList();

    List<Tag> savedTags = tagRepository.saveAll(newTags);

    List<PostTag> newPostTags = new ArrayList<>();

    existTags.forEach(tag -> newPostTags.add(PostTag.builder().post(savedPost).tag(tag).build()));
    savedTags.forEach(tag -> newPostTags.add(PostTag.builder().post(savedPost).tag(tag).build()));

    postTagRepository.saveAll(newPostTags);

    return savedPost;
  }

  private Category findCategory(Long categoryId) {
    return categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
  }
}
