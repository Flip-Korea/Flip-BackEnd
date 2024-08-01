package com.flip.flipapp.domain.scrap.controller;

import static com.flip.flipapp.docs.RestDocsAttributeFactory.constraintsField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.flipapp.common.HeaderConstants;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.domain.scrap.controller.dto.request.AddScrapRequest;
import com.flip.flipapp.domain.scrap.exception.DuplicatedScrapException;
import com.flip.flipapp.domain.scrap.exception.SelfScrapException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class AddScrapControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  AddScrapRequest VALID_REQUEST = new AddScrapRequest("scrap", 1L);
  AddScrapRequest DUPLICATED_REQUEST = new AddScrapRequest("scrap", 2L);

  @Test
  @Sql("addScrap.sql")
  @WithMockUser(username = "1")
  @DisplayName("요청이 유효하면 스크랩을 저장하고 201 응답을 한다")
  void should_return_201_status_when_request_is_valid() throws Exception {
    mockMvc.perform(
               post("/api/v1/scraps")
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsBytes(VALID_REQUEST))
           )
           .andExpectAll(
               status().isCreated(),
               header().string("Location", containsString("/api/v1/scraps"))
           )
           .andDo(
               document("addScrap",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   requestFields(
                       fieldWithPath("scrapComment").attributes(constraintsField("널 X"))
                                                    .type(JsonFieldType.STRING)
                                                    .description("스크랩 코멘트"),
                       fieldWithPath("postId").attributes(constraintsField("널 X, MIN 1"))
                                              .type(JsonFieldType.NUMBER)
                                              .description("포스트 ID")
                   ),
                   responseHeaders(
                       headerWithName("Location").description("생성된 게시글 URI")
                   )
               )
           );
  }

  @Test
  @Sql("addScrap.sql")
  @WithMockUser(username = "2")
  @DisplayName("자신의 포스트를 스크랩하면 400 응답을 한다")
  void should_return_400_status_when_it_is_self_scrap() throws Exception {
    mockMvc.perform(
               post("/api/v1/scraps")
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsBytes(VALID_REQUEST))
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(SelfScrapException.class)
           );
  }

  @Test
  @Sql("addScrap.sql")
  @WithMockUser(username = "2")
  @DisplayName("이미 스크랩이 되어있으면 400 응답을 한다")
  void should_return_400_status_when_it_is_duplicated() throws Exception {
    mockMvc.perform(
               post("/api/v1/scraps")
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsBytes(DUPLICATED_REQUEST))
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(DuplicatedScrapException.class)
           );
  }
}