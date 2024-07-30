package com.flip.flipapp.domain.post_tag.model;

import com.flip.flipapp.domain.post.model.Post;
import com.flip.flipapp.domain.tag.model.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class PostTag {

  public static final int MAX_TAG_COUNT = 10;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_tag_id", nullable = false, columnDefinition = "bigint")
  private Long postTagId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false, columnDefinition = "bigint")
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tag_id", nullable = false, columnDefinition = "bigint")
  private Tag tag;

}

