package com.flip.flipapp.domain.follow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "follow")
@Getter
@NoArgsConstructor
public class Follow {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "follow_id", nullable = false, columnDefinition = "bigint")
  private Long followId;

  @Column(name = "following_id", nullable = false, columnDefinition = "varchar(20)")
  private String followingId;

  @Column(name = "follower_id", nullable = false, columnDefinition = "varchar(20)")
  private String followerId;

  @Column(name = "fallow_at", nullable = false, columnDefinition = "datetime")
  private LocalDateTime fallowAt;

}