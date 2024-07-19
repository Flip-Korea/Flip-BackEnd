package com.flip.flipapp.domain.temp_post.controller;

import static com.flip.flipapp.common.HeaderConstants.ACCESS_TOKEN;
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

import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.docs.RestDocsAttributeFactory;
import com.flip.flipapp.domain.temp_post.exception.NotTempPostWriterException;
import com.flip.flipapp.domain.temp_post.exception.TempPostNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class DeleteTempPostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @Sql("deleteTempPost.sql")
  @DisplayName("요청이 유효하면 댓글을 삭제하고 204 응답을 한다")
  @WithMockUser(username = "1")
  void should_return_status_204_when_request_is_valid() throws Exception {
    mockMvc.perform(
               delete("/api/v1/temp-posts/{tempPostId}", 1L)
                   .header(ACCESS_TOKEN.getKey(), ACCESS_TOKEN.getValue()))
           .andExpect(status().isNoContent())
           .andDo(
               document("deleteTempPost",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   pathParameters(
                       parameterWithName("tempPostId")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("삭제할 임시 포스트 ID")
                   )
               )
           );
  }

  @Test
  @WithMockUser(username = "1")
  @DisplayName("ID에 해당하는 임시 포스트가 없으면 400 응답을 한다")
  void should_return_status_400_when_temp_post_dont_exist() throws Exception {
    mockMvc.perform(
               delete("/api/v1/temp-posts/{tempPostId}", 1L)
                   .header(ACCESS_TOKEN.getKey(), ACCESS_TOKEN.getValue()))
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(TempPostNotFoundException.class)
           );
  }

  @Test
  @Sql("deleteTempPost.sql")
  @WithMockUser(username = "2")
  @DisplayName("글쓴이가 아니라면 400 응답을 한다")
  void should_return_status_204_when_it_is_not_writer() throws Exception {
    mockMvc.perform(
               delete("/api/v1/temp-posts/{tempPostId}", 1L)
                   .header(ACCESS_TOKEN.getKey(), ACCESS_TOKEN.getValue()))
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(NotTempPostWriterException.class)
           );
  }
}