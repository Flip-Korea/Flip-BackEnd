package com.flip.flipapp.domain.temp_post.controller;

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
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.domain.category.exception.CategoryNotFoundException;
import com.flip.flipapp.domain.post.model.BgColorType;
import com.flip.flipapp.domain.post.model.FontStyleType;
import com.flip.flipapp.domain.temp_post.controller.dto.request.AddTempPostRequest;
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
class AddTempPostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  AddTempPostRequest VALID_REQUEST = new AddTempPostRequest("title", "content", "BLUE",
      "NORMAL", List.of("tag1", "tag2"), 1L);

  AddTempPostRequest INVALID_CATEGORY_ID = new AddTempPostRequest("title", "content", "BLUE",
      "NORMAL", List.of("tag1", "tag2"), 8L);

  @Test
  @WithMockUser(username = "1")
  @DisplayName("요청이 유효하면 임시 포스트를 저장하고 201 응답을 한다")
  @Sql("addTempPost.sql")
  void should_return_201_status_when_request_is_valid() throws Exception {
    mockMvc.perform(
               post("/api/v1/temp-posts")
                   .header("Authorization", "Bearer access-token")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsBytes(VALID_REQUEST))
           )
           .andExpectAll(
               status().isCreated(),
               header().string("Location", containsString("/api/v1/temp-posts/1"))
           )
           .andDo(
               document("addTempPost",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   requestFields(
                       fieldWithPath("title").attributes(constraintsField("널, 공백 X"))
                                             .type(JsonFieldType.STRING)
                                             .description("임시 포스트 제목"),
                       fieldWithPath("content").attributes(constraintsField("널 X"))
                                               .type(JsonFieldType.STRING)
                                               .description("임시 포스트 내용"),
                       fieldWithPath("bgColorType").attributes(constraintsField("널, 공백 X"))
                                                   .type(JsonFieldType.STRING)
                                                   .description(
                                                       Arrays.toString(BgColorType.values())),
                       fieldWithPath("fontStyleType").attributes(constraintsField("널, 공백 X"))
                                                     .type(JsonFieldType.STRING)
                                                     .description(
                                                         Arrays.toString(FontStyleType.values())),
                       fieldWithPath("tags").attributes(constraintsField("널 X, 최대 10개"))
                                            .type(JsonFieldType.ARRAY)
                                            .description("태그 목록"),
                       fieldWithPath("categoryId").attributes(constraintsField("널 X, MIN 1"))
                                                  .type(JsonFieldType.NUMBER)
                                                  .description("카테고리 ID")
                   ),
                   responseHeaders(
                       headerWithName("Location").description("생성된 임시 게시글 URI")
                   )
               ));
  }

  @Test
  @WithMockUser(username = "1")
  @DisplayName("존재하지 않는 카테고리 ID 라면 400 응답을 한다")
  @Sql("addTempPost.sql")
  void should_return_400_status_when_category_id_is_not_exist() throws Exception {
    mockMvc.perform(
               post("/api/v1/posts")
                   .header("Authorization", "Bearer access-token")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsBytes(INVALID_CATEGORY_ID))
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(CategoryNotFoundException.class)
           );
  }
}