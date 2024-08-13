package com.flip.flipapp.domain.profileImage.model;

import com.flip.flipapp.domain.profile.model.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profile_image")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileImage {

  @Id
  @Column(name = "profile_id", nullable = false, columnDefinition = "bigint")
  private Long profileId;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @Column(name = "image_url", nullable = false, columnDefinition = "varchar(255)")
  private String imageUrl;

}