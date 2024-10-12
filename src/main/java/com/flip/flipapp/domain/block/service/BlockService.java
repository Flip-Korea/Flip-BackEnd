package com.flip.flipapp.domain.block.service;

import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.block.exception.DuplicatedBlockException;
import com.flip.flipapp.domain.block.exception.SelfBlockException;
import com.flip.flipapp.domain.block.model.Block;
import com.flip.flipapp.domain.block.repository.BlockRepository;
import com.flip.flipapp.domain.block.service.dto.BlockCommand;
import com.flip.flipapp.domain.profile.exception.ProfileNotFoundException;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlockService {

  private final BlockRepository blockRepository;
  private final ProfileRepository profileRepository;

  @Transactional
  public Block block(BlockCommand blockCommand) {
    Account blocker = findAccountByProfileId(blockCommand.blockerProfileId());
    Account blocked = findAccountByProfileId(blockCommand.blockedProfileId());

    if (isSelfBlock(blocker, blocked)) {
      throw new SelfBlockException();
    }

    if (blockRepository.existsByBlockerAndBlocked(blocker, blocked)) {
      throw new DuplicatedBlockException();
    }

    Block newBlock = Block.builder()
        .blocker(blocker)
        .blocked(blocked)
        .build();

    try {
      return blockRepository.save(newBlock);
    } catch (DataIntegrityViolationException e) {
      throw new DuplicatedBlockException();
    }
  }

  private boolean isSelfBlock(Account blocker, Account blocked) {
    return blocked.getAccountId().equals(blocker.getAccountId());
  }

  private Account findAccountByProfileId(Long profileId) {
    Profile profile = profileRepository.findById(profileId)
        .orElseThrow(ProfileNotFoundException::new);
    return profile.getAccount();
  }
}
