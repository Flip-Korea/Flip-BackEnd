package com.flip.flipapp.domain.account.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.flipapp.common.HeaderConstants;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.domain.account.exception.AccountSuspendedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
@Sql("ReissueControllerTest.sql")
class ReissueControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;


  @Test
  @WithMockUser(username = "1")
  @DisplayName("유효한 요청 시 JWT 토큰을 재발급한다")
  void should_reissue_jwt_tokens_when_request_is_valid() throws Exception {

    mockMvc.perform(
            post("/api/v1/reissue")
                .header(HeaderConstants.REFRESH_TOKEN.getKey(), HeaderConstants.REFRESH_TOKEN.getValue())
                .contentType(APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andDo(
            document("reissue",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HeaderConstants.REFRESH_TOKEN.getKey())
                        .description(HeaderConstants.REFRESH_TOKEN.getValue())
                ),
                responseFields(
                    fieldWithPath("accessToken").type(JsonFieldType.STRING)
                        .description("새로 발급된 Access Token"),
                    fieldWithPath("refreshToken").type(JsonFieldType.STRING)
                        .description("새로 발급된 Refresh Token")
                )
            )
        );
  }


  @Test
  @WithMockUser(username = "2")
  @DisplayName("정지된 계정으로 토큰 재발급 요청 시 403 응답을 반환한다")
  void should_return_403_when_account_is_suspended() throws Exception {
    mockMvc.perform(post("/api/v1/reissue")
            .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
            .contentType(APPLICATION_JSON)
        )
        .andExpectAll(
            status().isForbidden(),
            result -> assertThat(result.getResolvedException())
                .isInstanceOf(AccountSuspendedException.class)
        );
  }
}
