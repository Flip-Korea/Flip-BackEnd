package com.flip.flipapp.domain.follow.controller;

import static com.flip.flipapp.docs.RestDocsAttributeFactory.constraintsField;
import static org.assertj.core.api.Assertions.assertThat;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.flipapp.common.HeaderConstants;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.domain.follow.controller.dto.request.FollowRequest;
import com.flip.flipapp.domain.follow.exception.DuplicatedFollowException;
import com.flip.flipapp.domain.follow.exception.SelfFollowException;
import com.flip.flipapp.domain.profile.exception.ProfileNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class FollowControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  FollowRequest VALID_REQUEST = new FollowRequest(2L);
  FollowRequest SELF_FOLLOW_REQUEST = new FollowRequest(1L);
  FollowRequest DUPLICATED_FOLLOW_REQUEST = new FollowRequest(1L);
  FollowRequest NOT_EXIST_USER_REQUEST = new FollowRequest(3L);

  @Test
  @DisplayName("요청이 유효한 경우 성공적으로 팔로우하고 201 응답을 한다")
  @WithMockUser(username = "1")
  @Sql("follow.sql")
  void should_return_201_status_when_request_is_valid() throws Exception {
    mockMvc.perform(
               post("/api/v1/follows")
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsBytes(VALID_REQUEST))
           )
           .andExpect(status().isCreated())
           .andDo(
               document("follow",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName(HeaderConstants.ACCESS_TOKEN.getKey())
                           .description(HeaderConstants.ACCESS_TOKEN.getValue())
                   ),
                   requestFields(
                       fieldWithPath("followId").attributes(constraintsField("널 X, MIN 1"))
                                                .type(JsonFieldType.NUMBER)
                                                .description("팔로우 ID")
                   ),
                   responseHeaders(
                       headerWithName("Location").description("추가된 팔로우 URI")
                   )
               ));
  }

  @Test
  @DisplayName("자신을 팔로우하면 400 응답을 한다")
  @WithMockUser(username = "1")
  @Sql("follow.sql")
  void should_return_400_status_when_self_follow() throws Exception {
    mockMvc.perform(
               post("/api/v1/follows")
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsBytes(SELF_FOLLOW_REQUEST))
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(SelfFollowException.class)
           );
  }

  @Test
  @DisplayName("이미 팔로우가 되어있으면 400 응답을 한다")
  @WithMockUser(username = "2")
  @Sql("follow.sql")
  void should_return_201_status_when_already_follow() throws Exception {
    mockMvc.perform(
               post("/api/v1/follows")
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsBytes(DUPLICATED_FOLLOW_REQUEST))
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(DuplicatedFollowException.class)
           );
  }

  @Test
  @DisplayName("팔로우 유저가 존재하지 않으면 400 응답을 한다")
  @WithMockUser(username = "1")
  void should_return_400_status_when_follow_do_not_exist() throws Exception {
    mockMvc.perform(
               post("/api/v1/follows")
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsBytes(NOT_EXIST_USER_REQUEST))
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(ProfileNotFoundException.class)
           );
  }
}