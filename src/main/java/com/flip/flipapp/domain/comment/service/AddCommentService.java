package com.flip.flipapp.domain.comment.service;

import com.flip.flipapp.domain.comment.model.Comment;
import com.flip.flipapp.domain.comment.repository.CommentRepository;
import com.flip.flipapp.domain.comment.service.dto.AddCommentCommand;
import com.flip.flipapp.domain.post.exception.PostNotFoundException;
import com.flip.flipapp.domain.post.model.Post;
import com.flip.flipapp.domain.post.repository.PostRepository;
import com.flip.flipapp.domain.profile.model.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  public void addComment(AddCommentCommand command) {
    Post post = findPost(command.postId());

    Comment comment = Comment.builder()
                             .post(post)
                             .profile(Profile.getAuthenticatedProfile(command.profileId()))
                             .content(command.content()).build();

    commentRepository.save(comment);
  }

  private Post findPost(Long postId) {
    return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
  }
}
