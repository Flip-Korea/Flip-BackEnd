package com.flip.flipapp.domain.profile.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.docs.RestDocsAttributeFactory;
import com.flip.flipapp.domain.profile.controller.dto.request.CheckUserIdRequest;
import com.flip.flipapp.domain.profile.exception.ProfileErrorCode;
import com.flip.flipapp.global.exception.CommonErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTestWithRestDocs
class CheckUserIdControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  CheckUserIdRequest validUserId = new CheckUserIdRequest("newUser");
  CheckUserIdRequest invalidUserId = new CheckUserIdRequest("invalid@User!");
  CheckUserIdRequest existingUserId = new CheckUserIdRequest("user");

  @Test
  @DisplayName("유효한 사용자 ID일 때 200 응답을 한다")
  void should_status_is_200_when_userId_is_valid() throws Exception {

    mockMvc
        .perform(post("/api/v1/validations/user-id")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(validUserId)))
        .andExpect(
            status().isOk()
        )
        .andDo(
            document("checkUserId",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userId")
                        .attributes(
                            RestDocsAttributeFactory.constraintsField("영문 또는 . _ 입력, 최대 16글자"))
                        .type(JsonFieldType.STRING)
                        .description("검증할 아이디")
                )
            )
        );
  }

  @Test
  @DisplayName("유효하지 않은 사용자 ID일 때 400 응답을 한다")
  void should_status_is_400_when_userId_is_invalid() throws Exception {

    mockMvc.perform(post("/api/v1/validations/user-id")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidUserId)))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.code").value(CommonErrorCode.INVALID_INPUT_VALUE.getCode())
        );
  }

  @Test
  @DisplayName("이미 존재하는 사용자 ID일 때 409 응답을 한다")
  @Sql("checkUserIdTest.sql")
  void should_status_is_409_when_userId_already_exists() throws Exception {

    mockMvc.perform(post("/api/v1/validations/user-id")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(existingUserId)))
        .andExpectAll(
            status().isConflict(),
            jsonPath("$.code").value(ProfileErrorCode.PROFILE_DUPLICATE_USER_ID.getCode())
        );
  }

}
