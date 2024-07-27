package com.flip.flipapp.domain.post.service;

import com.flip.flipapp.domain.post.exception.NotPostWriterException;
import com.flip.flipapp.domain.post.exception.PostNotFoundException;
import com.flip.flipapp.domain.post.model.Post;
import com.flip.flipapp.domain.post.repository.PostRepository;
import com.flip.flipapp.domain.post.service.dto.DeletePostCommand;
import com.flip.flipapp.domain.post_tag.repository.PostTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletePostService {

  private final PostRepository postRepository;
  private final PostTagRepository postTagRepository;

  @Transactional
  public void deletePost(DeletePostCommand command) {
    Post post = findPost(command.postId());

    if (!post.isWriter(command.profileId())) {
      throw new NotPostWriterException();
    }

    post.delete();
    postTagRepository.deleteAllByPost(post);
  }

  private Post findPost(Long postId) {
    return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
  }
}
