package com.flip.flipapp.domain.temp_post.controller;

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
class GetTempPostsControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @Sql("getTempPosts.sql")
  @WithMockUser(username = "1")
  @DisplayName("첫 페이지 요청시 임시포스트 목록을 limit에 맞게 반환한다")
  void should_return_status_200_with_temp_post_and_totalCount_when_first_page_request()
      throws Exception {
    mockMvc.perform(
               get("/api/v1/temp-posts")
                   .header("Authorization", "Bearer access-token")
                   .param("limit", "3")
           )
           .andExpectAll(
               status().isOk(),
               jsonPath("$.tempPosts.size()").value(3),
               jsonPath("$.totalCount").value(4)
           )
           .andDo(
               document("getTempPostsFirstPage",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   queryParameters(
                       parameterWithName("cursor")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .optional().description("이전 페이지의 마지막 임시포스트 ID, 첫 페이지 요청시 필요없음"),
                       parameterWithName("limit")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("한 페이지에 보여줄 임시포스트 개수")
                   ),
                   responseFields(
                       fieldWithPath("tempPosts[].tempPostId").type(JsonFieldType.NUMBER)
                                                              .description("임시포스트 ID"),
                       fieldWithPath("tempPosts[].title").type(JsonFieldType.STRING)
                                                         .description("임시포스트 제목"),
                       fieldWithPath("tempPosts[].content").type(JsonFieldType.STRING)
                                                           .description("임시포스트 내용"),
                       fieldWithPath("tempPosts[].bgColorType").type(JsonFieldType.STRING)
                                                               .description("임시포스트 배경색"),
                       fieldWithPath("tempPosts[].fontStyleType").type(JsonFieldType.STRING)
                                                                 .description("임시포스트 글씨체"),
                       fieldWithPath("tempPosts[].postAt").type(JsonFieldType.STRING)
                                                          .description("임시포스트 작성 시간"),
                       fieldWithPath("tempPosts[].categoryId").type(JsonFieldType.NUMBER)
                                                              .description("카테고리 ID"),
                       fieldWithPath("tempPosts[].categoryName").type(JsonFieldType.STRING)
                                                                .description("카테고리명"),
                       fieldWithPath("tempPosts[].tags").type(JsonFieldType.ARRAY)
                                                        .description("임시포스트 태그 목록"),
                       fieldWithPath("totalCount").type(JsonFieldType.NUMBER)
                                                  .description("총 임시포스트 개수")
                   ))

           );

  }

  @Test
  @Sql("getTempPosts.sql")
  @WithMockUser(username = "1")
  @DisplayName("다음 페이지 요청시 임시포스트 목록을 limit에 맞게 반환한다")
  void should_return_status_200_with_temp_post_when_next_page_request()
      throws Exception {
    mockMvc.perform(
               get("/api/v1/temp-posts")
                   .header("Authorization", "Bearer access-token")
                   .param("limit", "3")
                   .param("cursor", "2")
           )
           .andExpectAll(
               status().isOk(),
               jsonPath("$.tempPosts.size()").value(3),
               jsonPath("$.totalCount").value(0)
           )
           .andDo(
               document("getTempPostsNextPage",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   queryParameters(
                       parameterWithName("cursor")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .optional().description("이전 페이지의 마지막 임시포스트 ID, 첫 페이지 요청시 필요없음"),
                       parameterWithName("limit")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("한 페이지에 보여줄 임시포스트 개수")
                   ),
                   responseFields(
                       fieldWithPath("tempPosts[].tempPostId").type(JsonFieldType.NUMBER)
                                                              .description("임시포스트 ID"),
                       fieldWithPath("tempPosts[].title").type(JsonFieldType.STRING)
                                                         .description("임시포스트 제목"),
                       fieldWithPath("tempPosts[].content").type(JsonFieldType.STRING)
                                                           .description("임시포스트 내용"),
                       fieldWithPath("tempPosts[].bgColorType").type(JsonFieldType.STRING)
                                                               .description("임시포스트 배경색"),
                       fieldWithPath("tempPosts[].fontStyleType").type(JsonFieldType.STRING)
                                                                 .description("임시포스트 글씨체"),
                       fieldWithPath("tempPosts[].postAt").type(JsonFieldType.STRING)
                                                          .description("임시포스트 작성 시간"),
                       fieldWithPath("tempPosts[].categoryId").type(JsonFieldType.NUMBER)
                                                              .description("카테고리 ID"),
                       fieldWithPath("tempPosts[].categoryName").type(JsonFieldType.STRING)
                                                                .description("카테고리명"),
                       fieldWithPath("tempPosts[].tags").type(JsonFieldType.ARRAY)
                                                        .description("임시포스트 태그 목록"),
                       fieldWithPath("totalCount").type(JsonFieldType.NUMBER)
                                                  .description("첫 페이지 요청이 아니면 0")
                   ))

           );
  }
}