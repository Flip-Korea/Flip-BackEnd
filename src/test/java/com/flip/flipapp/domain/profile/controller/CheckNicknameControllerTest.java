package com.flip.flipapp.domain.profile.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.docs.RestDocsAttributeFactory;
import com.flip.flipapp.domain.profile.controller.dto.request.CheckNicknameRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class CheckNicknameControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  @DisplayName("닉네임이 유효할 때 200 응답을 한다")
  void should_status_is_200_when_nickname_is_valid() throws Exception {
    CheckNicknameRequest request = new CheckNicknameRequest("어스름늑대");

    mockMvc.perform(post("/api/v1/validations/nickname")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(
            status().isOk()
        )
        .andDo(
            document("checkNickname",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("nickname").attributes(
                            RestDocsAttributeFactory.constraintsField("2-12자리 한글 및 영문"))
                        .type(JsonFieldType.STRING)
                        .description("검증할 닉네임")
                )
            )
        );
  }

  @Test
  @DisplayName("닉네임이 공백일 때 400 응답을 한다")
  void should_status_is_400_when_nickname_is_blank() throws Exception {
    CheckNicknameRequest request = new CheckNicknameRequest("");

    mockMvc.perform(post("/api/v1/validations/nickname")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("닉네임이 유효하지 않을 때 400 응답을 한다")
  void should_status_is_400_when_nickname_is_invalid() throws Exception {
    CheckNicknameRequest request = new CheckNicknameRequest("어스름_늑대");

    mockMvc.perform(post("/api/v1/validations/nickname")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("닉네임이 너무 길 때 400 응답을 한다")
  void should_status_is_400_when_nickname_is_too_long() throws Exception {
    CheckNicknameRequest request = new CheckNicknameRequest("thisNicknameIsWayTooLong");

    mockMvc.perform(post("/api/v1/validations/nickname")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("닉네임이 null일 때 400 응답을 한다")
  void should_status_is_400_when_nickname_is_null() throws Exception {
    String nullNickname = "{\"nickname\":null}";

    mockMvc.perform(post("/api/v1/validations/nickname")
            .contentType(APPLICATION_JSON)
            .content(nullNickname))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("닉네임이 한글자일 때 400 응답을 한다")
  void should_status_is_400_when_nickname_is_single_character() throws Exception {
    CheckNicknameRequest request = new CheckNicknameRequest("준");

    mockMvc.perform(post("/api/v1/validations/nickname")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }
}
