package com.flip.flipapp.domain.post;

import com.flip.flipapp.domain.category.Category;
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
@Table(name = "post")
@Getter
@NoArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id", nullable = false, columnDefinition = "bigint")
  private Long postId;

  @Column(name = "title", nullable = false, columnDefinition = "varchar(100)")
  private String title;

  @Column(name = "content", nullable = false, columnDefinition = "text")
  private String content;

  @Column(name = "post_at", nullable = false, columnDefinition = "datetime")
  private LocalDateTime postAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "bg_color", nullable = false, columnDefinition = "varchar(25)")
  private BgColorType bgColor;

  @Enumerated(EnumType.STRING)
  @Column(name = "font_style", nullable = false, columnDefinition = "varchar(25)")
  private FontStyleType fontStyle;

  @Column(name = "like_cnt", nullable = false, columnDefinition = "bigint")
  private Long likeCnt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false, columnDefinition = "bigint")
  private Profile profileId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false, columnDefinition = "bigint")
  private Category categoryId;

}