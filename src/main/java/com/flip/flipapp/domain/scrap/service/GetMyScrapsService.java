package com.flip.flipapp.domain.scrap.service;

import com.flip.flipapp.domain.scrap.repository.ScrapRepository;
import com.flip.flipapp.domain.scrap.repository.dto.ScrapPageDto;
import com.flip.flipapp.domain.scrap.service.dto.GetMyScrapsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetMyScrapsService {

  private final ScrapRepository scrapRepository;

  @Transactional(readOnly = true)
  public Page<ScrapPageDto> getMyScraps(GetMyScrapsQuery query) {

    return scrapRepository.findScrapsPage(query.toCondition());
  }
}
