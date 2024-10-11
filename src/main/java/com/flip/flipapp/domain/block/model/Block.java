package com.flip.flipapp.domain.block.model;

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
@Table(name = "block")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Block {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "block_id", nullable = false, columnDefinition = "bigint")
  private Long blockId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "blocked_id", nullable = false, columnDefinition = "bigint")
  private Account blocked;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "blocker_id", nullable = false, columnDefinition = "bigint")
  private Account blocker;

  public boolean isBlocker(Long accountId){
    return blocker.getAccountId().equals(accountId);
  }
}