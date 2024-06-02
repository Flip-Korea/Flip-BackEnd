package com.flip.flipapp.domain.follow.model;

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "following_id", nullable = false, columnDefinition = "bigint")
  private Profile followingId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "follower_id", nullable = false, columnDefinition = "bigint")
  private Profile followerId;

  @Column(name = "fallow_at", nullable = false, columnDefinition = "datetime")
  private LocalDateTime fallowAt;

}