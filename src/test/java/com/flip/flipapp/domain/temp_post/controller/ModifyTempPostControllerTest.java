package com.flip.flipapp.domain.temp_post.controller;

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
import com.flip.flipapp.domain.post.model.BgColorType;
import com.flip.flipapp.domain.post.model.FontStyleType;
import com.flip.flipapp.domain.temp_post.controller.dto.request.ModifyTempPostRequest;
import com.flip.flipapp.domain.temp_post.exception.NotTempPostWriterException;
import com.flip.flipapp.domain.temp_post.exception.TempPostNotFoundException;
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
class ModifyTempPostControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  ModifyTempPostRequest VALID_REQUEST = new ModifyTempPostRequest(
      "new title",
      "new content",
      "YELLOW",
      "NORMAL",
      List.of("tag1", "tag2"),
      3L
  );

  @Test
  @WithMockUser(username = "1")
  @Sql("modifyTempPost.sql")
  @DisplayName("요청이 유효하면 성공적으로 임시포스트를 수정하고 204 응답을 한다")
  void should_return_status_204_when_request_is_valid() throws Exception {
    mockMvc.perform(
               put("/api/v1/temp-posts/{tempPostId}", 1L)
                   .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsString(VALID_REQUEST))
           )
           .andExpect(status().isNoContent())
           .andDo(
               document("modifyTempPost",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   pathParameters(
                       parameterWithName("tempPostId")
                           .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                           .description("수정할 임시 포스트 ID")
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
                   )
               )
           );
  }

  @Test
  @WithMockUser(username = "1")
  @DisplayName("ID에 해당하는 임시 포스트가 없으면 400 응답을 한다")
  void should_return_status_400_when_temp_post_dont_exist() throws Exception {
    mockMvc.perform(
               put("/api/v1/temp-posts/{tempPostId}", 1L)
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsString(VALID_REQUEST))
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(TempPostNotFoundException.class)
           );
  }

  @Test
  @WithMockUser(username = "2")
  @Sql("modifyTempPost.sql")
  @DisplayName("글쓴이가 아니라면 400 응답을 한다")
  void should_return_status_204_when_it_is_not_writer() throws Exception {
    mockMvc.perform(
               put("/api/v1/temp-posts/{tempPostId}", 1L)
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsString(VALID_REQUEST))
           )
           .andExpectAll(
               status().isBadRequest(),
               result -> assertThat(result.getResolvedException())
                   .isInstanceOf(NotTempPostWriterException.class)
           );

  }
}