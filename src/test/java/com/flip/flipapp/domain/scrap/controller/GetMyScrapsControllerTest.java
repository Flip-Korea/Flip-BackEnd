package com.flip.flipapp.domain.scrap.controller;

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
class GetMyScrapsControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @Sql("myScrap.sql")
  @DisplayName("첫 페이지 요청을 하면 나의 스크랩 목록과 총 개수를 반환한다")
  @WithMockUser(username = "1")
  void should_return_status_200_with_myscraps_and_totalCount_when_first_page_request()
      throws Exception {
    mockMvc.perform(get("/api/v1/my/scraps")
               .param("limit", "3")
               .header("Authorization", "Bearer access-token"))
           .andExpectAll(
               status().isOk(),
               jsonPath("$.scraps.size()").value(3),
               jsonPath("$.totalCount").value(4)
           )
           .andDo(
               document("getMyScrapsFirstPage",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   queryParameters(
                       parameterWithName("cursor")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .optional().description("이전 페이지의 마지막 스크랩 ID, 첫 페이지 요청시 필요없음"),
                       parameterWithName("limit")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("한 페이지에 보여줄 스크랩 개수")
                   ),
                   responseFields(
                       fieldWithPath("scraps[].scrapId").type(JsonFieldType.NUMBER)
                                                        .description("스크랩 ID"),
                       fieldWithPath("scraps[].scrapComment").type(JsonFieldType.STRING)
                                                             .description("스크랩 코멘트"),
                       fieldWithPath("scraps[].scrapAt").type(JsonFieldType.STRING)
                                                        .description("스크랩 작성 시간"),
                       fieldWithPath("scraps[].postTitle").type(JsonFieldType.STRING)
                                                          .description("포스트 제목"),
                       fieldWithPath("scraps[].postWriterNickname").type(JsonFieldType.STRING)
                                                                   .description("포스트 작성자 닉네임"),
                       fieldWithPath("totalCount").type(JsonFieldType.NUMBER)
                                                  .description("총 스크랩 개수")
                   ))
           );
  }

  @Test
  @Sql("myScrap.sql")
  @DisplayName("다음 페이지 요청을 하면 나의 스크랩 목록을 반환한다")
  @WithMockUser(username = "1")
  void should_return_status_200_with_myscraps_when_next_page_request() throws Exception {
    mockMvc.perform(get("/api/v1/my/scraps")
               .param("limit", "3")
               .param("cursor", "3")
               .header("Authorization", "Bearer access-token"))
           .andExpectAll(
               status().isOk(),
               jsonPath("$.scraps.size()").value(1),
               jsonPath("$.totalCount").value(0)
           )
           .andDo(
               document("getMyScrapsNextPage",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   queryParameters(
                       parameterWithName("cursor")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .optional().description("이전 페이지의 마지막 스크랩 ID, 첫 페이지 요청시 필요없음"),
                       parameterWithName("limit")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("한 페이지에 보여줄 스크랩 개수")
                   ),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   responseFields(
                       fieldWithPath("scraps[].scrapId").type(JsonFieldType.NUMBER)
                                                        .description("스크랩 ID"),
                       fieldWithPath("scraps[].scrapComment").type(JsonFieldType.STRING)
                                                            .description("스크랩 코멘트"),
                       fieldWithPath("scraps[].scrapAt").type(JsonFieldType.STRING)
                                                        .description("스크랩 작성 시간"),
                       fieldWithPath("scraps[].postTitle").type(JsonFieldType.STRING)
                                                          .description("포스트 제목"),
                       fieldWithPath("scraps[].postWriterNickname").type(JsonFieldType.STRING)
                                                                   .description("포스트 작성자 닉네임"),
                       fieldWithPath("totalCount").type(JsonFieldType.NUMBER)
                                                  .description("총 스크랩 개수")
                   ))
           );
  }
}