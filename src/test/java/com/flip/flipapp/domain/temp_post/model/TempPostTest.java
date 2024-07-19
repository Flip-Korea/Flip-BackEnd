package com.flip.flipapp.domain.temp_post.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TempPostTest {

  @Test
  void should_content_is_empty_string_when_input_is_blank() {
    TempPost tempPost = TempPost.builder()
                                .content(" ")
                                .build();

    assertThat(tempPost.getContent()).isEmpty();
  }
}