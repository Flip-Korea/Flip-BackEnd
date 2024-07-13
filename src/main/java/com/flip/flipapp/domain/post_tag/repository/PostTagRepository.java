package com.flip.flipapp.domain.post_tag.repository;

import com.flip.flipapp.domain.post_tag.model.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

}
