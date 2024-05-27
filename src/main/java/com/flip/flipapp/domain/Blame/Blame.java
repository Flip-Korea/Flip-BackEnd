package com.flip.flipapp.domain.Blame;

import com.flip.flipapp.domain.Comment.Comment;
import com.flip.flipapp.domain.Post.Post;
import com.flip.flipapp.domain.Profile.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blame")
@Getter
@NoArgsConstructor
public class Blame {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "blame_id", nullable = false, columnDefinition = "bigint")
  private Long blameId;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, columnDefinition = "varchar(25)")
  private BlameType type;

  @Column(name = "blame_at", nullable = false, columnDefinition = "datetime")
  private LocalDateTime blameAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = true, columnDefinition = "bigint")
  private Post postId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "comment_id", nullable = true, columnDefinition = "bigint")
  private Comment commentId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reporter_id", nullable = false, columnDefinition = "bigint")
  private Profile reporterId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_id", nullable = false, columnDefinition = "bigint")
  private Profile reportedId;

}
