package com.flip.flipapp.domain.post.controller;

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
import com.flip.flipapp.domain.post.exception.NotPostWriterException;
import com.flip.flipapp.domain.post.exception.PostNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class DeletePostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @WithMockUser(username = "1")
  @Sql("deletePost.sql")
  @DisplayName("요청이 유효하면 포스트를 삭제하고 204 응답을 한다")
  void should_return_status_204_when_request_is_valid() throws Exception {
    mockMvc.perform(
               delete("/api/v1/posts/{postId}", 1L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
           )
           .andExpect(status().isNoContent())
           .andDo(
               document("deletePost",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName(HeaderConstants.ACCESS_TOKEN.getKey())
                           .description("access token")
                   ),
                   pathParameters(
                       parameterWithName("postId")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .optional().description("댓글의 포스트 ID")
                   )
               )
           );
  }

  @Test
  @WithMockUser(username = "1")
  @DisplayName("해당 포스트가 존재하지 않으면 400 응답을 한다")
  void should_return_status_400_when_post_dont_exist() throws Exception {
    mockMvc.perform(
               delete("/api/v1/posts/{postId}", 1L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(PostNotFoundException.class)
           );
  }

  @Test
  @WithMockUser(username = "2")
  @Sql("deletePost.sql")
  @DisplayName("글쓴이가 아니라면 400 응답을 한다")
  void should_return_status_400_when_it_is_not_writer() throws Exception {
    mockMvc.perform(
               delete("/api/v1/posts/{postId}", 1L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(NotPostWriterException.class)
           );
  }
}