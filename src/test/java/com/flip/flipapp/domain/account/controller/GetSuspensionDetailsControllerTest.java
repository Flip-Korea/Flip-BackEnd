package com.flip.flipapp.domain.account.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.domain.account.controller.dto.request.OauthIdRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class GetSuspensionDetailsControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  OauthIdRequest validRequest = new OauthIdRequest("kakao", "oauth2");
  OauthIdRequest invalidRequest = new OauthIdRequest("unknown", "unknown");

  @Test
  @DisplayName("요청이 유효하면 계정 정지 정보을 반환하고 200 응답을 한다")
  @Sql("GetSuspensionDetailsControllerTest.sql")
  void should_return_status_200_with_suspension_details_when_request_is_valid() throws Exception {
    mockMvc.perform(post("/api/v1/suspension-details")
            .content(objectMapper.writeValueAsBytes(validRequest))
            .contentType(APPLICATION_JSON))
        .andExpect(
            status().isOk()
        )
        .andDo(
            document("getSuspensionDetails",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("provider").type(JsonFieldType.STRING).description("소셜 로그인 제공자"),
                    fieldWithPath("oauthId").type(JsonFieldType.STRING)
                        .description("Oauth Id(sub) 값")
                ),
                responseFields(
                    fieldWithPath("suspendedAt").type(JsonFieldType.STRING)
                        .description("계정이 정지된 시간")
                        .optional(),
                    fieldWithPath("accountState").type(JsonFieldType.STRING).description("계정 상태"),
                    fieldWithPath("blameTypes").type(JsonFieldType.ARRAY).description("정지 이유 목록")
                        .optional()
                )
            )
        );
  }

  @Test
  @DisplayName("잘못된 요청이 오면 400 응답을 반환한다")
  @Sql("GetSuspensionDetailsControllerTest.sql")
  void should_return_status_400_when_request_is_invalid() throws Exception {
    mockMvc.perform(post("/api/v1/suspension-details")
            .content(objectMapper.writeValueAsBytes(invalidRequest))
            .contentType(APPLICATION_JSON))
        .andExpectAll(
            status().isBadRequest()
        );
  }

}
