package com.flip.flipapp.domain.temp_post.model;

import com.flip.flipapp.domain.category.model.Category;
import com.flip.flipapp.domain.post.model.BgColorType;
import com.flip.flipapp.domain.post.model.FontStyleType;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.global.persistence.StringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "temp_post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
  @CreationTimestamp
  private LocalDateTime postAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "bg_color", nullable = false, columnDefinition = "varchar(25)")
  private BgColorType bgColor;

  @Enumerated(EnumType.STRING)
  @Column(name = "font_style", nullable = false, columnDefinition = "varchar(25)")
  private FontStyleType fontStyle;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false, columnDefinition = "bigint")
  private Profile profile;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false, columnDefinition = "bigint")
  private Category category;

  @Convert(converter = StringListConverter.class)
  @Column(name = "tags", nullable = false, columnDefinition = "varchar(255)")
  private List<String> tags;

  public static class TempPostBuilder {

    public TempPostBuilder content(String content) {
      if (content.isBlank()) {
        this.content = "";
      } else {
        this.content = content;
      }

      return this;
    }
  }

  public boolean isWriter(Long profileId) {
    return profile.getProfileId().equals(profileId);
  }

  public void modifyTo(TempPost newTempPost){
    this.title = newTempPost.title;
    this.content = newTempPost.content;
    this.bgColor = newTempPost.bgColor;
    this.fontStyle = newTempPost.fontStyle;
    this.category = newTempPost.category;
    this.tags = newTempPost.tags;
    this.postAt = LocalDateTime.now();
  }

}