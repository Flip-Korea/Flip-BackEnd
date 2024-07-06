package com.flip.flipapp.domain.comment.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.docs.RestDocsAttributeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class GetMyCommentsControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @Sql("myComments.sql")
  @DisplayName("첫 페이지 요청을 하면 나의 댓글 목록과 총 개수를 반환한다")
  @WithMockUser(username = "1")
  void should_return_status_200_with_mycomments_and_totalCount_when_first_page_request()
      throws Exception {
    mockMvc.perform(get("/api/v1/my/comments")
               .param("limit", "5")
               .header("Authorization", "Bearer access-token"))
           .andExpectAll(
               status().isOk(),
               jsonPath("$.comments.size()").value(5),
               jsonPath("$.totalCount").value(20)
           )
           .andDo(
               document("getMyCommentsFirstPage",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   queryParameters(
                       parameterWithName("cursor")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .optional().description("이전 페이지의 마지막 댓글 ID, 첫 페이지 요청시 필요없음"),
                       parameterWithName("limit")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("한 페이지에 보여줄 댓글 개수")
                   ),
                   responseFields(
                       fieldWithPath("comments[].postId").type(JsonFieldType.NUMBER)
                                                         .description("포스트 ID"),
                       fieldWithPath("comments[].postWriterNickname").type(JsonFieldType.STRING)
                                                                     .description("포스트 작성자 닉네임"),
                       fieldWithPath("comments[].postTitle").type(JsonFieldType.STRING)
                                                            .description("포스트 제목"),
                       fieldWithPath("comments[].commentId").type(JsonFieldType.NUMBER)
                                                            .description("댓글 ID"),
                       fieldWithPath("comments[].commentContent").type(JsonFieldType.STRING)
                                                                 .description("댓글 내용"),
                       fieldWithPath("comments[].commentAt").type(JsonFieldType.STRING)
                                                            .description("댓글 작성 시간"),
                       fieldWithPath("totalCount").type(JsonFieldType.NUMBER).description("총 댓글 개수")
                   ))
           );
  }

  @Test
  @Sql("myComments.sql")
  @DisplayName("다음 페이지 요청을 하면 나의 댓글 목록을 반환한다")
  @WithMockUser(username = "1")
  void should_return_status_200_with_mycomments_when_next_page_request() throws Exception {
    mockMvc.perform(get("/api/v1/my/comments")
               .param("limit", "5")
               .param("cursor", "5")
               .header("Authorization", "Bearer access-token"))
           .andExpectAll(
               status().isOk(),
               jsonPath("$.comments.size()").value(4),
               jsonPath("$.totalCount").value(0)
           )
           .andDo(
               document("getMyCommentsNextPage",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   queryParameters(
                       parameterWithName("cursor")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .optional().description("이전 페이지의 마지막 댓글 ID, 첫 페이지 요청시 필요없음"),
                       parameterWithName("limit")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("한 페이지에 보여줄 댓글 개수")
                   ),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   responseFields(
                       fieldWithPath("comments[].postId").type(JsonFieldType.NUMBER)
                                                         .description("포스트 ID"),
                       fieldWithPath("comments[].postWriterNickname").type(JsonFieldType.STRING)
                                                                     .description("포스트 작성자 닉네임"),
                       fieldWithPath("comments[].postTitle").type(JsonFieldType.STRING)
                                                            .description("포스트 제목"),
                       fieldWithPath("comments[].commentId").type(JsonFieldType.NUMBER)
                                                            .description("댓글 ID"),
                       fieldWithPath("comments[].commentContent").type(JsonFieldType.STRING)
                                                                 .description("댓글 내용"),
                       fieldWithPath("comments[].commentAt").type(JsonFieldType.STRING)
                                                            .description("댓글 작성 시간"),
                       fieldWithPath("totalCount").type(JsonFieldType.NUMBER)
                                                  .description("첫 페이지 요청이 아니면 0")
                   ))
           );
  }
}