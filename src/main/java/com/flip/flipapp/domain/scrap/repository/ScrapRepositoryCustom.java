package com.flip.flipapp.domain.scrap.repository;

import com.flip.flipapp.domain.scrap.repository.dto.ScrapPageCondition;
import com.flip.flipapp.domain.scrap.repository.dto.ScrapPageDto;
import org.springframework.data.domain.Page;

public interface ScrapRepositoryCustom {

  Page<ScrapPageDto> findScrapsPage(ScrapPageCondition condition);
}
