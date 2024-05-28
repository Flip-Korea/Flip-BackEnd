package com.flip.flipapp.domain.postTag;

import com.flip.flipapp.domain.post.Post;
import com.flip.flipapp.domain.tag.Tag;
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
@Table(name = "post_tag")
@Getter
@NoArgsConstructor
public class PostTag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_tag_id", nullable = false, columnDefinition = "bigint")
  private Long postTagId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false, columnDefinition = "bigint")
  private Post postId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tag_id", nullable = false, columnDefinition = "bigint")
  private Tag tagId;

}

