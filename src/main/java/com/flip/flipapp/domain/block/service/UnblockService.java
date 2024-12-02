package com.flip.flipapp.domain.block.service;

import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.block.exception.BlockNotFoundException;
import com.flip.flipapp.domain.block.exception.NotBlockerException;
import com.flip.flipapp.domain.block.model.Block;
import com.flip.flipapp.domain.block.repository.BlockRepository;
import com.flip.flipapp.domain.block.service.dto.UnblockCommand;
import com.flip.flipapp.domain.profile.exception.ProfileNotFoundException;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnblockService {

  private final BlockRepository blockRepository;
  private final ProfileRepository profileRepository;

  @Transactional
  public void unblock(UnblockCommand unblockCommand) {
    Account blocker = findAccountByProfileId(unblockCommand.blockerProfileId());
    Block block = findBlock(unblockCommand.blockId());

    if (!block.isBlocker(blocker.getAccountId())) {
      throw new NotBlockerException();
    }

    blockRepository.delete(block);
  }

  private Block findBlock(Long blockId) {
    return blockRepository.findById(blockId)
        .orElseThrow(BlockNotFoundException::new);
  }

  private Account findAccountByProfileId(Long profileId) {
    Profile profile = profileRepository.findById(profileId)
        .orElseThrow(ProfileNotFoundException::new);
    return profile.getAccount();
  }
}
