package com.flip.flipapp.domain.scrap.repository;

import com.flip.flipapp.domain.post.model.Post;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.scrap.model.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

  boolean existsByProfileAndPost(Profile profile, Post post);
}
