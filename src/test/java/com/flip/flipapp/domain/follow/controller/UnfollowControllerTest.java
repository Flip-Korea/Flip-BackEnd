package com.flip.flipapp.domain.follow.controller;

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
import com.flip.flipapp.domain.follow.exception.FollowNotFoundException;
import com.flip.flipapp.domain.follow.exception.NotFollowerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class UnfollowControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @WithMockUser(username = "1")
  @Sql("unfollow.sql")
  @DisplayName("요청이 유효한 경우 성공적으로 언팔로우를 하고 204 응답을 한다")
  void should_return_204_status_when_request_is_valid() throws Exception {
    mockMvc.perform(
               delete("/api/v1/follows/{followId}", 1L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
           )
           .andExpect(status().isNoContent())
           .andDo(
               document("unfollow",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName(HeaderConstants.ACCESS_TOKEN.getKey())
                           .description(HeaderConstants.ACCESS_TOKEN.getValue())
                   ),
                   pathParameters(
                       parameterWithName("followId")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("언팔로우 ID")
                   )));
  }

  @Test
  @WithMockUser(username = "1")
  @Sql("unfollow.sql")
  @DisplayName("ID에 해당하는 팔로우가 없는 경우 400 응답을 한다")
  void should_return_400_status_when_follow_is_not_exist() throws Exception {
    mockMvc.perform(
               delete("/api/v1/follows/{followId}", 2L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(FollowNotFoundException.class)
           );
  }

  @Test
  @WithMockUser(username = "3")
  @Sql("unfollow.sql")
  @DisplayName("해당 팔로우의 팔로워가 아닌 경우 400 응답을 한다")
  void should_return_204_status_when_not_follower() throws Exception {
    mockMvc.perform(
               delete("/api/v1/follows/{followId}", 1L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(NotFollowerException.class)
           );
  }
}