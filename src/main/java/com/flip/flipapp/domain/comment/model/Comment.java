package com.flip.flipapp.domain.comment.model;

import com.flip.flipapp.domain.comment.exception.InvalidPostOfCommentException;
import com.flip.flipapp.domain.comment.exception.NotCommentWriterException;
import com.flip.flipapp.domain.post.model.Post;
import com.flip.flipapp.domain.profile.model.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id", nullable = false, columnDefinition = "bigint")
  private Long commentId;

  @Column(name = "content", nullable = false, columnDefinition = "text")
  private String content;

  @CreationTimestamp
  @Column(name = "comment_at", nullable = false, columnDefinition = "datetime", updatable = false)
  private LocalDateTime commentAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false, columnDefinition = "bigint")
  private Profile profile;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false, columnDefinition = "bigint")
  private Post post;

  public void checkWriter(Long profileId) {
    if (!profile.getProfileId().equals(profileId)) {
      throw new NotCommentWriterException();
    }
  }

  public void checkPostRelation(Long postId) {
    if (!post.getPostId().equals(postId)) {
      throw new InvalidPostOfCommentException();
    }
  }
}