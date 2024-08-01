package com.flip.flipapp.domain.post.service;

import com.flip.flipapp.domain.category.exception.CategoryNotFoundException;
import com.flip.flipapp.domain.category.model.Category;
import com.flip.flipapp.domain.category.repository.CategoryRepository;
import com.flip.flipapp.domain.post.exception.NotPostWriterException;
import com.flip.flipapp.domain.post.exception.PostNotFoundException;
import com.flip.flipapp.domain.post.model.Post;
import com.flip.flipapp.domain.post.repository.PostRepository;
import com.flip.flipapp.domain.post.service.dto.ModifyPostCommand;
import com.flip.flipapp.domain.post_tag.exception.PostTagOverMaxCountException;
import com.flip.flipapp.domain.post_tag.model.PostTag;
import com.flip.flipapp.domain.post_tag.repository.PostTagRepository;
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
public class ModifyPostService {

  private final PostRepository postRepository;
  private final PostTagRepository postTagRepository;
  private final TagRepository tagRepository;
  private final CategoryRepository categoryRepository;


  @Transactional
  public void modifyPost(ModifyPostCommand command) {
    Post existingPost = findPost(command.postId());

    if (!existingPost.isWriter(command.profileId())) {
      throw new NotPostWriterException();
    }

    // 포스트 수정
    Post newPost;
    if (isNewCategory(existingPost.getCategory().getCategoryId(), command.categoryId())) {
      Category newCategory = findCategory(command.categoryId());
      newPost = command.toPost(newCategory);
    } else {
      newPost = command.toPost(existingPost.getCategory());
    }

    existingPost.modifyTo(newPost);

    // tag 최대 개수 검증
    List<PostTag> existingPostTags = findPostTags(existingPost);

    if (isOverMaxTagCount(existingPostTags.size(), command.newTags().size(),
        command.deletePostTagIds().size())) {
      throw new PostTagOverMaxCountException();
    }

    // deleteTags 삭제
    postTagRepository.deleteAllByIdInBatch(command.deletePostTagIds());

    // newTags 추가
    List<Tag> existTags = tagRepository.findTagsIn(command.newTags());

    Set<String> existTagNames = existTags.stream()
                                         .map(Tag::getTagName)
                                         .collect(Collectors.toUnmodifiableSet());

    List<Tag> newTags = command.newTags().stream()
                               .filter(tn -> !existTagNames.contains(tn))
                               .map(tn -> Tag.builder().tagName(tn).build())
                               .toList();

    List<Tag> savedTags = tagRepository.saveAll(newTags);

    List<PostTag> newPostTags = new ArrayList<>();

    existTags.forEach(
        tag -> newPostTags.add(PostTag.builder().post(existingPost).tag(tag).build()));
    savedTags.forEach(
        tag -> newPostTags.add(PostTag.builder().post(existingPost).tag(tag).build()));

    postTagRepository.saveAll(newPostTags);
  }

  public boolean isOverMaxTagCount(int existingTagPostCount, int newTagCount,
      int deletePostTagCount) {
    return existingTagPostCount + newTagCount - deletePostTagCount > PostTag.MAX_TAG_COUNT;
  }

  public boolean isNewCategory(Long categoryId, Long newCategoryId) {
    return !categoryId.equals(newCategoryId);
  }

  public Category findCategory(Long categoryId) {
    return categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
  }

  public Post findPost(Long postId) {
    return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
  }

  public List<PostTag> findPostTags(Post post) {
    return postTagRepository.findAllByPost(post);
  }
}
