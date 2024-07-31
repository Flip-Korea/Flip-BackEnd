package com.flip.flipapp.domain.scrap.service;

import com.flip.flipapp.domain.post.exception.PostNotFoundException;
import com.flip.flipapp.domain.post.model.Post;
import com.flip.flipapp.domain.post.repository.PostRepository;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.scrap.exception.DuplicatedScrapException;
import com.flip.flipapp.domain.scrap.exception.SelfScrapException;
import com.flip.flipapp.domain.scrap.model.Scrap;
import com.flip.flipapp.domain.scrap.repository.ScrapRepository;
import com.flip.flipapp.domain.scrap.service.dto.AddScrapCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddScrapService {

  private final ScrapRepository scrapRepository;
  private final PostRepository postRepository;

  @Transactional
  public Scrap addScrap(AddScrapCommand command) {
    Post post = findPost(command.postId());
    Profile profile = Profile.getAuthenticatedProfile(command.profileId());
    if (post.isWriter(command.profileId())) {
      throw new SelfScrapException();
    }

    if (scrapRepository.existsByProfileAndPost(profile, post)) {
      throw new DuplicatedScrapException();
    }

    Scrap newScrap = Scrap.builder()
                          .scrapComment(command.scrapComment())
                          .profile(profile)
                          .post(post)
                          .build();

    return scrapRepository.save(newScrap);
  }

  private Post findPost(Long postId) {
    return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
  }
}
