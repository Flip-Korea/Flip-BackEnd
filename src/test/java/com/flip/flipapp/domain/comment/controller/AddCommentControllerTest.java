package com.flip.flipapp.domain.comment.controller;

import static com.flip.flipapp.docs.RestDocsAttributeFactory.constraintsField;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.domain.comment.controller.dto.request.AddCommentRequest;
import com.flip.flipapp.global.error.CommonErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class AddCommentControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  AddCommentRequest validRequest = new AddCommentRequest("content");
  AddCommentRequest invalidRequest = new AddCommentRequest("");

  @Test
  @DisplayName("요청이 유효하고 인증된 사용자면 댓글을 저장하고 201 응답을 한다")
  @Sql("addCommentTest.sql")
  @WithMockUser(username = "1")
  void should_status_is_201_when_user_is_authenticated_and_request_is_valid() throws Exception {
    mockMvc.perform(post("/api/v1/posts/{postId}/comments", 1)
               .header("Authorization", "Bearer access-token")
               .content(objectMapper.writeValueAsBytes(validRequest))
               .contentType(APPLICATION_JSON))
           .andExpect(
               status().isCreated()
           )
           .andDo(
               document("addComment",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   requestFields(
                       fieldWithPath("content").attributes(constraintsField("널, 공백 또는 빈 문자열 X"))
                                               .type(JsonFieldType.STRING)
                                               .description("댓글 내용")
                   )
               )
           );
  }

  @Test
  @DisplayName("인증되지 않은 사용자면 401 응답을 한다")
  void should_status_is_401_when_user_is_not_authenticated() throws Exception {
    mockMvc.perform(post("/api/v1/posts/{postId}/comments", 1)
               .contentType(APPLICATION_JSON))
           .andExpectAll(
               status().isUnauthorized(),
               jsonPath("$.code").value(CommonErrorCode.UNAUTHENTICATED.getCode())
           );
  }

  @Test
  @DisplayName("postId가 1보다 작으면 400 응답을 한다")
  @WithMockUser(username = "1")
  void should_status_is_400_when_post_id_is_less_than_1() throws Exception {
    mockMvc.perform(post("/api/v1/posts/{postId}/comments", 0)
               .content(objectMapper.writeValueAsBytes(validRequest))
               .contentType(APPLICATION_JSON))
           .andExpectAll(
               status().isBadRequest(),
               jsonPath("$.code").value(CommonErrorCode.INVALID_REQUEST.getCode())
           );
  }

  @Test
  @DisplayName("요청이 유효하지 않으면 400 응답을 한다")
  @WithMockUser(username = "1")
  void should_status_is_400_when_request_content_is_invalid() throws Exception {
    mockMvc.perform(post("/api/v1/posts/{postId}/comments", 1)
               .content(objectMapper.writeValueAsBytes(invalidRequest))
               .contentType(APPLICATION_JSON))
           .andExpectAll(
               status().isBadRequest(),
               jsonPath("$.code").value(CommonErrorCode.INVALID_INPUT_VALUE.getCode())
           );
  }
}