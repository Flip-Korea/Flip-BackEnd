package com.flip.flipapp.domain.temp_post.repository;

import com.flip.flipapp.domain.temp_post.model.TempPost;
import com.flip.flipapp.domain.temp_post.repository.dto.TempPostPageCondition;
import org.springframework.data.domain.Page;

public interface TempPostRepositoryCustom {
  Page<TempPost> findTempPostsPage(TempPostPageCondition condition);
}
