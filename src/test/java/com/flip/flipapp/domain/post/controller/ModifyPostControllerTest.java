package com.flip.flipapp.domain.post.controller;

import static com.flip.flipapp.docs.RestDocsAttributeFactory.constraintsField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.flipapp.common.HeaderConstants;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.docs.RestDocsAttributeFactory;
import com.flip.flipapp.domain.category.exception.CategoryNotFoundException;
import com.flip.flipapp.domain.post.controller.dto.request.ModifyPostRequest;
import com.flip.flipapp.domain.post.exception.NotPostWriterException;
import com.flip.flipapp.domain.post.exception.PostNotFoundException;
import com.flip.flipapp.domain.post.model.BgColorType;
import com.flip.flipapp.domain.post.model.FontStyleType;
import com.flip.flipapp.domain.post_tag.exception.PostTagOverMaxCountException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class ModifyPostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  ModifyPostRequest VALID_REQUEST = new ModifyPostRequest("new title", "new content", "RED",
      "NORMAL", List.of("tag3", "tag4"), List.of(2L), 2L);

  ModifyPostRequest INVALID_CATEGORY_REQUEST = new ModifyPostRequest("new title", "new content",
      "RED",
      "NORMAL", List.of("tag3", "tag4"), List.of(2L), 20L);

  ModifyPostRequest OVER_MAX_TAGS_REQUEST = new ModifyPostRequest("new title", "new content", "RED",
      "NORMAL", List.of("tag3", "tag4", "tag5", "tag6", "tag7", "tag8", "tag9", "tag10", "tag11"),
      List.of(), 2L);

  @Test
  @WithMockUser(username = "1")
  @Sql("modifyPost.sql")
  @DisplayName("요청이 유효하면 성공적으로 임시포스트를 수정하고 204 응답을 한다")
  void should_return_status_204_when_request_is_valid() throws Exception {
    mockMvc.perform(
               put("/api/v1/posts/{postId}", 1L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .content(objectMapper.writeValueAsBytes(VALID_REQUEST))
                   .contentType(MediaType.APPLICATION_JSON)
           )
           .andExpect(status().isNoContent())
           .andDo(
               document("modifyPost",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   pathParameters(
                       parameterWithName("postId")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .optional().description("수정할 포스트 ID")
                   ),
                   requestFields(
                       fieldWithPath("title").attributes(constraintsField("널, 공백 X"))
                                             .type(JsonFieldType.STRING)
                                             .description("수정할 포스트 제목"),
                       fieldWithPath("content").attributes(constraintsField("널, 공백 X"))
                                               .type(JsonFieldType.STRING)
                                               .description("수정할 포스트 내용"),
                       fieldWithPath("bgColorType").attributes(constraintsField("널, 공백 X"))
                                                   .type(JsonFieldType.STRING)
                                                   .description(
                                                       Arrays.toString(BgColorType.values())),
                       fieldWithPath("fontStyleType").attributes(constraintsField("널, 공백 X"))
                                                     .type(JsonFieldType.STRING)
                                                     .description(
                                                         Arrays.toString(FontStyleType.values())),
                       fieldWithPath("newTags").attributes(constraintsField("널 X, 최대 10개"))
                                               .type(JsonFieldType.ARRAY)
                                               .description("새로 추가할 태그 이름"),
                       fieldWithPath("deletePostTagIds").attributes(constraintsField("널 X, 최대 10개"))
                                                    .type(JsonFieldType.ARRAY)
                                                    .description("삭제할 태그 ID"),
                       fieldWithPath("categoryId").attributes(constraintsField("널 X"))
                                                  .type(JsonFieldType.NUMBER)
                                                  .description("수정할 카테고리 ID")
                   )
               )
           );
  }

  @Test
  @WithMockUser(username = "1")
  @Sql("modifyPost.sql")
  @DisplayName("포스트의 태그가 10개를 초과하면 400 응답을 한다")
  void should_return_status_400_when_tags_are_over_ten() throws Exception {
    mockMvc.perform(
               put("/api/v1/posts/{postId}", 1L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .content(objectMapper.writeValueAsBytes(OVER_MAX_TAGS_REQUEST))
                   .contentType(MediaType.APPLICATION_JSON)
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(PostTagOverMaxCountException.class)
           );
  }

  @Test
  @WithMockUser(username = "2")
  @Sql("modifyPost.sql")
  @DisplayName("글쓴이가 아니면 400 응답을 한다")
  void should_return_status_400_when_it_is_not_writer() throws Exception {
    mockMvc.perform(
               put("/api/v1/posts/{postId}", 1L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .content(objectMapper.writeValueAsBytes(VALID_REQUEST))
                   .contentType(MediaType.APPLICATION_JSON)
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(NotPostWriterException.class)
           );
  }

  @Test
  @WithMockUser(username = "1")
  @Sql("modifyPost.sql")
  @DisplayName("포스트가 존재하지 않으면 400 응답을 한다")
  void should_return_status_400_when_post_dont_exist() throws Exception {
    mockMvc.perform(
               put("/api/v1/posts/{postId}", 2L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .content(objectMapper.writeValueAsBytes(VALID_REQUEST))
                   .contentType(MediaType.APPLICATION_JSON)
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(PostNotFoundException.class)
           );
  }

  @Test
  @WithMockUser(username = "1")
  @Sql("modifyPost.sql")
  @DisplayName("카테고리가 존재하지 않으면 400 응답을 한다")
  void should_return_status_400_when_category_dont_exist() throws Exception {
    mockMvc.perform(
               put("/api/v1/posts/{postId}", 1L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .content(objectMapper.writeValueAsBytes(INVALID_CATEGORY_REQUEST))
                   .contentType(MediaType.APPLICATION_JSON)
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(CategoryNotFoundException.class)
           );
  }
}