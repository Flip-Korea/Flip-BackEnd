package com.flip.flipapp.domain.profile.model;

import com.flip.flipapp.domain.account.model.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Profile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "profile_id", nullable = false, columnDefinition = "bigint")
  private Long profileId;

  @Column(name = "user_id", nullable = false, columnDefinition = "varchar(20)")
  private String userId;

  @Column(name = "nickname", nullable = false, columnDefinition = "varchar(40)")
  private String nickname;

  @Column(name = "introduce", nullable = false, columnDefinition = "text")
  private String introduce;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false, columnDefinition = "bigint")
  private Account account;

  public static Profile getAuthenticatedProfile(Long profileId) {
    return Profile.builder().profileId(profileId).build();
  }

}
