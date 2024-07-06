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
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
class GetCommentsOfPostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @WithMockUser
  @Sql("commentsOfPost.sql")
  @DisplayName("첫 페이지 요청시 포스트에 해당하는 댓글 목록을 limit에 맞게 반환한다")
  void should_return_status_200_with_comments_of_post_and_totalCount_when_first_page_request()
      throws Exception {
    mockMvc.perform(get("/api/v1/posts/{postId}/comments", 1L)
               .header("Authorization", "Bearer access-token")
               .param("limit", "5"))
           .andExpectAll(
               status().isOk(),
               jsonPath("$.comments.size()").value(5),
               jsonPath("$.totalCount").value(6)
           )
           .andDo(
               document("getCommentsOfPostFirstPage",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   pathParameters(
                       parameterWithName("postId")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("포스트 ID")
                   ),
                   queryParameters(
                       parameterWithName("limit")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("한 페이지에 보여줄 댓글 개수")
                   ),
                   responseFields(
                       fieldWithPath("comments[].commentId").type(JsonFieldType.NUMBER)
                                                            .description("댓글 ID"),
                       fieldWithPath("comments[].content").type(JsonFieldType.STRING)
                                                          .description("댓글 내용"),
                       fieldWithPath("comments[].commentAt").type(JsonFieldType.STRING)
                                                            .description("댓글 작성 시간"),
                       fieldWithPath("comments[].profileId").type(JsonFieldType.NUMBER)
                                                            .description("프로필 ID"),
                       fieldWithPath("comments[].nickname").type(JsonFieldType.STRING)
                                                           .description("작성자 닉네임"),
                       fieldWithPath("comments[].profileImageUrl").type(JsonFieldType.STRING)
                                                                  .optional()
                                                                  .description("작성자 프로필 이미지 URL"),
                       fieldWithPath("totalCount").type(JsonFieldType.NUMBER).description("총 댓글 개수")
                   )
               ));
  }

  @Test
  @WithMockUser
  @Sql("commentsOfPost.sql")
  @DisplayName("다음 페이지 요청시 포스트에 해당하는 댓글 목록을 limit에 맞게 반환한다")
  void should_return_status_200_with_comments_of_post_when_next_page_request()
      throws Exception {
    mockMvc.perform(get("/api/v1/posts/{postId}/comments", 1L)
               .header("Authorization", "Bearer access-token")
               .param("cursor", "5")
               .param("limit", "5"))
           .andExpectAll(
               status().isOk(),
               jsonPath("$.comments.size()").value(1),
               jsonPath("$.totalCount").value(0)
           )
           .andDo(
               document("getCommentsOfPostNextPage",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   pathParameters(
                       parameterWithName("postId")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("포스트 ID")
                   ),
                   queryParameters(
                       parameterWithName("cursor")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .optional().description("마지막 댓글 ID")
                       ,
                       parameterWithName("limit")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("한 페이지에 보여줄 댓글 개수")
                   ),
                   responseFields(
                       fieldWithPath("comments[].commentId").type(JsonFieldType.NUMBER)
                                                            .description("댓글 ID"),
                       fieldWithPath("comments[].content").type(JsonFieldType.STRING)
                                                          .description("댓글 내용"),
                       fieldWithPath("comments[].commentAt").type(JsonFieldType.STRING)
                                                            .description("댓글 작성 시간"),
                       fieldWithPath("comments[].profileId").type(JsonFieldType.NUMBER)
                                                            .description("프로필 ID"),
                       fieldWithPath("comments[].nickname").type(JsonFieldType.STRING)
                                                           .description("작성자 닉네임"),
                       fieldWithPath("comments[].profileImageUrl").type(JsonFieldType.STRING)
                                                                  .optional()
                                                                  .description("작성자 프로필 이미지 URL"),
                       fieldWithPath("totalCount").type(JsonFieldType.NUMBER).description("총 댓글 개수, 첫페이지 아닐 경우 0")
                   )
               ));
  }
}