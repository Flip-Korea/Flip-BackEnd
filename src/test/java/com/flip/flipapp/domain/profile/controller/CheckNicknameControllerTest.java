package com.flip.flipapp.domain.profile.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class CheckNicknameControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("닉네임이 유효할 때 200 응답을 한다")
  void should_status_is_200_when_nickname_is_valid() throws Exception {
    String validNickname = "어스름늑대";

    mockMvc.perform(get("/api/v1/profile/check/nickname/{nickname}", validNickname)
            .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document("check-nickname-valid",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            )
        );
  }

  @Test
  @DisplayName("닉네임이 유효하지 않을 때 400 응답을 한다")
  void should_status_is_400_when_nickname_is_invalid() throws Exception {
    String invalidNickname = "invalid@Nick";

    mockMvc.perform(get("/api/v1/profile/check/nickname/{nickname}", invalidNickname)
            .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(
            document("check-nickname-invalid",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            )
        );
  }

  @Test
  @DisplayName("닉네임이 너무 길 때 400 응답을 한다")
  void should_status_is_400_when_nickname_is_too_long() throws Exception {
    String tooLongNickname = "thisNicknameIsWayTooLong";

    mockMvc.perform(get("/api/v1/profile/check/nickname/{nickname}", tooLongNickname)
            .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(
            document("check-nickname-too-long",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            )
        );
  }
}