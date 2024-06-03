package com.flip.flipapp.domain.account.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account", nullable = false, columnDefinition = "bigint")
  private Long accountId;

  @Column(name = "oauth_id", nullable = false, columnDefinition = "varchar(255)", unique = true)
  private String oauthId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AccountState accountState = AccountState.ACTIVE;

  @Column(name = "recent_login", nullable = false, columnDefinition = "bigint")
  private Long recentLogin;

}