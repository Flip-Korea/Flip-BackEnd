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
import com.flip.flipapp.domain.account.controller.dto.request.RegisterRequest;
import com.flip.flipapp.domain.account.controller.dto.request.RegisterRequest.UserProfile;
import java.util.List;
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

  RegisterRequest validRequest = new RegisterRequest("oauth123",
      List.of(1L, 2L, 3L),
      new UserProfile("user123", "nickname123", "https://maybe-storage-server.com/11"));

  RegisterRequest invalidCategoryRequest = new RegisterRequest("oauth123",
      List.of(1L, 2L, 7L),
      new UserProfile("user123", "nickname123", "https://maybe-storage-server.com/11"));

  @Test
  @DisplayName("유효한 요청으로 회원가입을 하면 200 응답을 반환한다")
  @Sql("RegisterControllerTest.sql")
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
                    fieldWithPath("oauthId").attributes(constraintsField("널, 공백 또는 빈 문자열 X"))
                        .type(JsonFieldType.STRING)
                        .description("OAuth ID"),
                    fieldWithPath("categories").attributes(constraintsField("유효한 카테고리명만 허용됨"))
                        .type(JsonFieldType.ARRAY)
                        .description("관심 카테고리 목록"),
                    fieldWithPath("profile.userId").attributes(constraintsField("널, 공백 또는 빈 문자열 X"))
                        .type(JsonFieldType.STRING)
                        .description("사용자 ID"),
                    fieldWithPath("profile.nickname").attributes(
                            constraintsField("널, 공백 또는 빈 문자열 X"))
                        .type(JsonFieldType.STRING)
                        .description("사용자 닉네임"),
                    fieldWithPath("profile.photoUrl").attributes(
                            constraintsField("프로필 사진 미설정 시 null 으로 전달"))
                        .type(JsonFieldType.STRING)
                        .description("프로필 사진 URL")
                )
            )
        );
  }

  @Test
  @DisplayName("존재하지 않는 카테고리로 요청 시 400 응답을 반환한다")
  @Sql("RegisterControllerTest.sql")
  void should_return_400_when_category_is_invalid() throws Exception {
    mockMvc.perform(post("/api/v1/accounts")
            .content(objectMapper.writeValueAsBytes(invalidCategoryRequest))
            .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
