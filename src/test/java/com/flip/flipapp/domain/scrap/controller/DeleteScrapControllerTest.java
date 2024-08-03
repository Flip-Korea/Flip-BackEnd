package com.flip.flipapp.domain.scrap.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.flip.flipapp.common.HeaderConstants;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.docs.RestDocsAttributeFactory;
import com.flip.flipapp.domain.scrap.exception.NotScrapOwnerException;
import com.flip.flipapp.domain.scrap.exception.ScrapNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class DeleteScrapControllerTest {

  @Autowired
  MockMvc mockMvc;

  final Long VALID_ID = 1L;

  @Test
  @Sql("deleteScrap.sql")
  @DisplayName("요청이 유효하면 스크랩을 삭제하고 204 응답을 한다")
  @WithMockUser(username = "1")
  void should_return_204_status_when_request_is_valid() throws Exception {
    mockMvc.perform(
               delete("/api/v1/scraps/{scrapId}", VALID_ID)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue()))
           .andExpect(status().isNoContent())
           .andDo(
               document("deleteScrap",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName(HeaderConstants.ACCESS_TOKEN.getKey())
                           .description("access token")
                   ),
                   pathParameters(
                       parameterWithName("scrapId")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .optional().description("스크랩 ID")
                   )
               )
           );
  }

  @Test
  @Sql("deleteScrap.sql")
  @DisplayName("스크랩한 유저가 아니면 400 응답을 한다")
  @WithMockUser(username = "2")
  void should_return_400_status_when_it_is_not_owner() throws Exception {
    mockMvc.perform(
               delete("/api/v1/scraps/{scrapId}", VALID_ID)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue()))
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(NotScrapOwnerException.class)
           );
  }

  @Test
  @Sql("deleteScrap.sql")
  @DisplayName("ID에 해당하는 스크랩이 없으면 400 응답을 한다")
  @WithMockUser(username = "1")
  void should_return_400_status_when_id_is_invalid() throws Exception {
    mockMvc.perform(
               delete("/api/v1/scraps/{scrapId}", 2L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue()))
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(ScrapNotFoundException.class)
           );
  }

  @Test
  @DisplayName("인증된 유저가 아니면 401 응답을 한다")
  void should_return_401_status_when_not_authenticated() throws Exception {
    mockMvc.perform(
               delete("/api/v1/scraps/{scrapId}", 2L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue()))
           .andExpect(status().isUnauthorized());
  }
}