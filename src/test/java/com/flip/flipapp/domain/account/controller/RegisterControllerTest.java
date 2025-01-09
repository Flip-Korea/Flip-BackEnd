package com.flip.flipapp.domain.account.controller;

import static com.flip.flipapp.docs.RestDocsAttributeFactory.constraintsField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.domain.account.controller.dto.request.RegisterRequest;
import com.flip.flipapp.domain.account.controller.dto.request.RegisterRequest.UserProfile;
import com.flip.flipapp.domain.account.exception.DuplicateOauthIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class RegisterControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  RegisterRequest validRequest = new RegisterRequest(
      "kakao",
      "oauth123",
      true,
      new UserProfile("user123", "nickname123", "https://flip-storage-server.com/11")
  );

  RegisterRequest duplicateRequest = new RegisterRequest(
      "kakao",
      "oauth456",
      true,
      new UserProfile("user456", "nickname456", "https://flip-storage-server.com/12")
  );

  @Test
  @DisplayName("유효한 요청으로 회원가입을 하면 200 응답을 반환한다")
  void should_return_200_when_request_is_valid() throws Exception {
    mockMvc.perform(post("/api/v1/accounts")
            .content(objectMapper.writeValueAsBytes(validRequest))
            .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document("register",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("provider").attributes(
                            constraintsField("google, kakao, naver, apple 중 하나여야 함"))
                        .type(JsonFieldType.STRING)
                        .description("소셜 로그인 공급자"),
                    fieldWithPath("oauthId").attributes(constraintsField("널, 공백 또는 빈 문자열 X"))
                        .type(JsonFieldType.STRING)
                        .description("OAuth ID"),
                    fieldWithPath("adsAgree").attributes(constraintsField("true 또는 false"))
                        .type(JsonFieldType.BOOLEAN)
                        .description("광고 수신 동의 여부"),
                    fieldWithPath("profile.userId").attributes(constraintsField("널, 공백 또는 빈 문자열 X"))
                        .type(JsonFieldType.STRING)
                        .description("사용자 ID"),
                    fieldWithPath("profile.nickname").attributes(
                            constraintsField("널, 공백 또는 빈 문자열 X"))
                        .type(JsonFieldType.STRING)
                        .description("사용자 닉네임"),
                    fieldWithPath("profile.imageUrl").attributes(
                            constraintsField("프로필 사진 미설정 시 null 으로 전달"))
                        .type(JsonFieldType.STRING)
                        .description("프로필 사진 URL")
                )
            )
        );
  }

  @Test
  @DisplayName("이미 가입된 회원인 경우 409 응답을 반환한다")
  @Sql("RegisterControllerTest.sql")
  void should_return_409_when_user_already_exists() throws Exception {
    mockMvc.perform(post("/api/v1/accounts")
            .content(objectMapper.writeValueAsBytes(duplicateRequest))
            .contentType(APPLICATION_JSON))
        .andExpectAll(
            status().isConflict(),
            result -> assertThat(result.getResolvedException()).isInstanceOf(
                DuplicateOauthIdException.class)
        );
  }
}
