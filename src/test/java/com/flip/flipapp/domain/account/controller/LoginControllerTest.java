package com.flip.flipapp.domain.account.controller;

import static com.flip.flipapp.docs.RestDocsAttributeFactory.constraintsField;
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
import com.flip.flipapp.domain.account.controller.dto.request.LoginRequest;
import com.flip.flipapp.global.security.jwt.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class LoginControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  JwtProvider jwtProvider;

  LoginRequest validLoginRequest = new LoginRequest("kakao", "oauth1");

  LoginRequest suspendedLoginRequest = new LoginRequest("kakao", "oauth2");

  LoginRequest nonExistentLoginRequest = new LoginRequest("kakao", "oauth3");

  @Test
  @DisplayName("유효한 요청 시 토큰을 발급받는다")
  @Sql("LoginControllerTest.sql")
  void should_return_200_and_issue_tokens_when_login_is_valid() throws Exception {
    mockMvc.perform(post("/api/v1/account/login")
            .content(objectMapper.writeValueAsBytes(validLoginRequest))
            .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document("login/valid",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("provider").attributes(constraintsField("널, 공백 또는 빈 문자열 X"))
                        .type(JsonFieldType.STRING)
                        .description("OAuth 공급자 (예: kakao, google)"),
                    fieldWithPath("oauthId").attributes(constraintsField("널, 공백 또는 빈 문자열 X"))
                        .type(JsonFieldType.STRING)
                        .description("OAuth ID")
                )
            )
        );
  }

  @Test
  @DisplayName("정지된 계정으로 로그인 시 403 응답을 반환한다")
  @Sql("LoginControllerTest.sql")
  void should_return_403_when_account_is_suspended() throws Exception {
    mockMvc.perform(post("/api/v1/account/login")
            .content(objectMapper.writeValueAsBytes(suspendedLoginRequest))
            .contentType(APPLICATION_JSON))
        .andExpect(status().isForbidden())
        .andDo(
            document("login/suspended",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            )
        );
  }

  @Test
  @DisplayName("존재하지 않는 계정으로 로그인 시 404 응답을 반환한다")
  void should_return_404_when_account_does_not_exist() throws Exception {
    mockMvc.perform(post("/api/v1/account/login")
            .content(objectMapper.writeValueAsBytes(nonExistentLoginRequest))
            .contentType(APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andDo(
            document("login/not-found",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
            )
        );
  }
}
