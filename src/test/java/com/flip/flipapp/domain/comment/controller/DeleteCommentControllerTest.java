package com.flip.flipapp.domain.comment.controller;

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
import com.flip.flipapp.domain.comment.exception.CommentNotFoundException;
import com.flip.flipapp.domain.comment.exception.InvalidPostOfComment;
import com.flip.flipapp.domain.comment.exception.NotCommentWriterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class DeleteCommentControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("요청이 유효하면 댓글을 삭제하고 204 응답을 한다")
  @WithMockUser(username = "1")
  @Sql("deleteComment.sql")
  void should_return_status_204_when_request_is_valid() throws Exception {
    mockMvc.perform(delete("/api/v1/posts/{postId}/comments/{commentId}", 1L, 1L)
               .header("Authorization", "Bearer access-token"))
           .andExpect(status().isNoContent())
           .andDo(
               document("deleteComment",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   pathParameters(
                       parameterWithName("postId")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .optional().description("댓글의 포스트 ID")
                       ,
                       parameterWithName("commentId")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("삭제할 댓글 ID")
                   )));
  }

  @Test
  @DisplayName("글쓴이가 아니면 400 응답을 한다")
  @WithMockUser(username = "2")
  @Sql("deleteComment.sql")
  void should_return_status_400_when_not_writer() throws Exception {
    mockMvc.perform(delete("/api/v1/posts/{postId}/comments/{commentId}", 1L, 1L)
               .header("Authorization", "Bearer access-token"))
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(NotCommentWriterException.class));
  }

  @Test
  @DisplayName("포스트와 댓글의 관계가 맞지 않으면 400 응답을 한다")
  @WithMockUser(username = "2")
  @Sql("deleteComment.sql")
  void should_return_status_400_when_invalid_postId() throws Exception {
    mockMvc.perform(delete("/api/v1/posts/{postId}/comments/{commentId}", 2L, 1L)
               .header("Authorization", "Bearer access-token"))
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(InvalidPostOfComment.class));
  }

  @Test
  @DisplayName("ID에 해당하는 댓글이 존재하지 않으면 400 응답을 한다")
  @WithMockUser(username = "2")
  void should_return_status_400_when_commentId_is_not_exists() throws Exception {
    mockMvc.perform(delete("/api/v1/posts/{postId}/comments/{commentId}", 1L, 1L)
               .header("Authorization", "Bearer access-token"))
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(CommentNotFoundException.class));
  }
}