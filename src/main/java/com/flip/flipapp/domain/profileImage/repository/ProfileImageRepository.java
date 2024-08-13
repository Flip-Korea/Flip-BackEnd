package com.flip.flipapp.domain.profileImage.repository;

import com.flip.flipapp.domain.profileImage.model.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

}
