package com.flip.flipapp.domain.Scrap;

import com.flip.flipapp.domain.Post.Post;
import com.flip.flipapp.domain.Profile.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scrap")
@Getter
@NoArgsConstructor
public class Scrap {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "scrap_id", nullable = false, columnDefinition = "bigint")
  private Long scrapId;

  @Column(name = "scrap_comment", nullable = false, columnDefinition = "text")
  private String scrapComment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false, columnDefinition = "bigint")
  private Profile profileId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false, columnDefinition = "bigint")
  private Post postId;

}