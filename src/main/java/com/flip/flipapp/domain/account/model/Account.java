package com.flip.flipapp.domain.account.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  @Column(name = "account_state", nullable = false, columnDefinition = "varchar(25)")
  private String accountState;

  @Column(name = "recent_login", nullable = false, columnDefinition = "bigint")
  private Long recentLogin;

}