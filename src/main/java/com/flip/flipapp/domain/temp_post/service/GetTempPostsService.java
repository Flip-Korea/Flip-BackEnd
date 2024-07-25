package com.flip.flipapp.domain.temp_post.service;

import com.flip.flipapp.domain.temp_post.model.TempPost;
import com.flip.flipapp.domain.temp_post.repository.TempPostRepository;
import com.flip.flipapp.domain.temp_post.service.dto.GetTempPostsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTempPostsService {

  private final TempPostRepository tempPostRepository;

  public Page<TempPost> getTempPosts(GetTempPostsQuery query) {
    return tempPostRepository.findTempPostsPage(query.toCondition());
  }
}
