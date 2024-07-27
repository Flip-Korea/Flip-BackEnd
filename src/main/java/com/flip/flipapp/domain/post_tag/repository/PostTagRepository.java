package com.flip.flipapp.domain.post_tag.repository;

import com.flip.flipapp.domain.post.model.Post;
import com.flip.flipapp.domain.post_tag.model.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

  @Modifying
  @Query("delete from PostTag pt where pt.post = :post")
  void deleteAllByPost(Post post);
}
