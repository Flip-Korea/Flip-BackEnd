package com.flip.flipapp.domain.temp_post.service;

import com.flip.flipapp.domain.temp_post.exception.NotTempPostWriterException;
import com.flip.flipapp.domain.temp_post.exception.TempPostNotFoundException;
import com.flip.flipapp.domain.temp_post.model.TempPost;
import com.flip.flipapp.domain.temp_post.repository.TempPostRepository;
import com.flip.flipapp.domain.temp_post.service.dto.DeleteTempPostCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTempPostService {

  private final TempPostRepository tempPostRepository;

  public void deleteTempPost(DeleteTempPostCommand command) {
    TempPost tempPost = findTempPost(command.tempPostId());

    if (!tempPost.isWriter(command.profileId())) {
      throw new NotTempPostWriterException();
    }

    tempPostRepository.delete(tempPost);
  }

  private TempPost findTempPost(Long tempPostId) {
    return tempPostRepository.findById(tempPostId).orElseThrow(TempPostNotFoundException::new);
  }
}
