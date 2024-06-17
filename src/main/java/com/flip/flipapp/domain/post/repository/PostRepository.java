package com.flip.flipapp.domain.post.repository;

import com.flip.flipapp.domain.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
