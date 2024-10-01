package com.flip.flipapp.domain.blame.repository;

import com.flip.flipapp.domain.blame.model.Blame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlameRepository extends JpaRepository<Blame, Long>, BlameRepositoryCustom {

}
