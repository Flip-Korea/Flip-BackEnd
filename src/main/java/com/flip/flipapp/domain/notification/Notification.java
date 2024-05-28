package com.flip.flipapp.domain.notification;

import com.flip.flipapp.domain.comment.Comment;
import com.flip.flipapp.domain.post.Post;
import com.flip.flipapp.domain.profile.Profile;
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
@Table(name = "notification")
@Getter
@NoArgsConstructor
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "noti_id", nullable = false, columnDefinition = "bigint")
  private Long notiId;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, columnDefinition = "varchar(25)")
  private NotiType type;

  @Column(name = "noti_at", nullable = false, columnDefinition = "datetime")
  private LocalDateTime notiAt;

  @Column(name = "is_read", nullable = false, columnDefinition = "boolean")
  private Boolean isRead;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_id", nullable = false, columnDefinition = "bigint")
  private Profile senderId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_id", nullable = false, columnDefinition = "bigint")
  private Profile creatorId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "comment_id", nullable = true, columnDefinition = "bigint")
  private Comment commentId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false, columnDefinition = "bigint")
  private Post postId;

}