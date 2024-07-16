package com.flip.flipapp.domain.tag.repository;

import com.flip.flipapp.domain.tag.model.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {

  @Query("select t from Tag t where t.tagName in :tagNames")
  List<Tag> findTagsIn(List<String> tagNames);
}
