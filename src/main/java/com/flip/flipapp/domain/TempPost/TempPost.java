package com.flip.flipapp.domain.TempPost;

import com.flip.flipapp.domain.Category.Category;
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
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "temp_post")
@Getter
@NoArgsConstructor
public class TempPost {

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

  @Column(name = "bg_color", nullable = false, columnDefinition = "varchar(25)")
  private String bgColor;

  @Column(name = "font_style", nullable = false, columnDefinition = "varchar(25)")
  private String fontStyle;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false, columnDefinition = "bigint")
  private Profile profileId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false, columnDefinition = "bigint")
  private Category categoryId;

}