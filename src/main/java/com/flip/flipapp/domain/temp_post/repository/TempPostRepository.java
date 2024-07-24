package com.flip.flipapp.domain.temp_post.repository;

import com.flip.flipapp.domain.temp_post.model.TempPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempPostRepository extends JpaRepository<TempPost, Long>, TempPostRepositoryCustom {

}
