package com.flip.flipapp.domain.scrap.service;

import com.flip.flipapp.domain.scrap.exception.NotScrapOwnerException;
import com.flip.flipapp.domain.scrap.exception.ScrapNotFoundException;
import com.flip.flipapp.domain.scrap.model.Scrap;
import com.flip.flipapp.domain.scrap.repository.ScrapRepository;
import com.flip.flipapp.domain.scrap.service.dto.DeleteScrapCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteScrapService {

  private final ScrapRepository scrapRepository;

  @Transactional
  public void deleteScrap(DeleteScrapCommand command) {
    Scrap scrap = findScrap(command.scrapId());

    if (!scrap.isOwner(command.profileId())) {
      throw new NotScrapOwnerException();
    }

    scrapRepository.delete(scrap);
  }

  private Scrap findScrap(Long scrapId) {
    return scrapRepository.findById(scrapId).orElseThrow(ScrapNotFoundException::new);
  }
}
