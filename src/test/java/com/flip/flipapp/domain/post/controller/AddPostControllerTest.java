package com.flip.flipapp.domain.post.controller;

import static com.flip.flipapp.docs.RestDocsAttributeFactory.constraintsField;
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
import com.flip.flipapp.domain.post.controller.dto.request.AddPostRequest;
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
class AddPostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  AddPostRequest VALID_REQUEST = new AddPostRequest("title", "content", "BLUE",
      "NORMAL", List.of("tag1", "tag2"), 1L);

  AddPostRequest INVALID_ENUM_VALUE = new AddPostRequest("title", "content", "INVALID",
      "INVALID", List.of("tag1", "tag2"), 1L);

  @Test
  @WithMockUser(username = "1")
  @DisplayName("요청이 유효하면 포스트를 저장하고 201 응답을 한다")
  @Sql("addPost.sql")
  void should_return_201_status_when_request_is_valid() throws Exception {
    mockMvc.perform(
               post("/api/v1/posts")
                   .header("Authorization", "Bearer access-token")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsBytes(VALID_REQUEST))
           )
           .andExpectAll(
               status().isCreated(),
               header().string("Location", containsString("/api/v1/posts/1"))
           )
           .andDo(
               document("addPost",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   requestFields(
                       fieldWithPath("title").attributes(constraintsField("널, 공백 X"))
                                             .type(JsonFieldType.STRING)
                                             .description("포스트 제목"),
                       fieldWithPath("content").attributes(constraintsField("널, 공백 X"))
                                               .type(JsonFieldType.STRING)
                                               .description("포스트 내용"),
                       fieldWithPath("bgColorType").attributes(constraintsField("널, 공백 X"))
                                                   .type(JsonFieldType.STRING)
                                                   .description("RED | GREEN | BLUE | YELLOW"),
                       fieldWithPath("fontStyleType").attributes(constraintsField("널, 공백 X"))
                                                     .type(JsonFieldType.STRING)
                                                     .description(
                                                         "BOLD | ITALIC | UNDERLINE | NORMAL"),
                       fieldWithPath("tags").attributes(constraintsField("널 X, 최대 10개"))
                                            .type(JsonFieldType.ARRAY)
                                            .description("태그 목록"),
                       fieldWithPath("categoryId").attributes(constraintsField("널 X"))
                                                  .type(JsonFieldType.NUMBER)
                                                  .description("카테고리 ID")
                   ),
                   responseHeaders(
                       headerWithName("Location").description("생성된 게시글 URI")
                   )
               ));
  }

  @Test
  @WithMockUser(username = "1")
  @DisplayName("유효하지 않은 enum 값을 입력하면 400 응답을 한다")
  @Sql("addPost.sql")
  void should_return_400_status_when_enum_value_is_invalid() throws Exception {
    mockMvc.perform(
               post("/api/v1/posts")
                   .header("Authorization", "Bearer access-token")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsBytes(INVALID_ENUM_VALUE))
           )
           .andExpect(
               status().isBadRequest()
           );
  }
}