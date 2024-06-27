package com.flip.flipapp.domain.interest_category.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.docs.RestDocsAttributeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class ChangeMyCategoriesControllerTest {

  @Autowired
  MockMvc mockMvc;

  final String VALID_CONTENT = """
      {
      "categoryIds":[1,5,6]
      }
      """;

  final String LESS_THAN_MIN_SIZE_CONTENT = """
      {
      "categoryIds":[1,2]
      }
      """;

  final String MORE_THEN_MAX_SIZE_CONTENT = """
      {
      "categoryIds":[1,2,3,4,5,6,7,8,9,10,11,12,13]
      }
      """;

  final String NOT_EXIST_CATEGORY_CONTENT = """
      {
      "categoryIds":[1,2,12]
      }
      """;

  @Test
  @Sql("changeMyCategories.sql")
  @DisplayName("요청이 유효하면 카테고리를 변경하고 204 응답을 한다")
  @WithMockUser(username = "1")
  void should_status_is_204_when_request_is_valid() throws Exception {
    mockMvc.perform(put("/api/v1/my/categories")
               .header("Authorization", "Bearer access-token")
               .contentType(MediaType.APPLICATION_JSON)
               .content(VALID_CONTENT))
           .andExpect(
               status().isNoContent()
           )
           .andDo(
               document("changeMyCategories",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   requestFields(
                       fieldWithPath("categoryIds")
                           .description("새로 변경할 카테고리 ID 목록")
                           .attributes(
                               RestDocsAttributeFactory.constraintsField("중복된 ID X, 3개 이상 12개 이하"))
                   )
               ));
  }

  @Test
  @DisplayName("카테고리 ID가 3개 미만이면 400 응답을 한다")
  @Sql("changeMyCategories.sql")
  @WithMockUser(username = "1")
  void should_status_is_400_when_categoryIds_less_than_3() throws Exception {
    mockMvc.perform(put("/api/v1/my/categories")
               .header("Authorization", "Bearer access-token")
               .contentType(MediaType.APPLICATION_JSON)
               .content(LESS_THAN_MIN_SIZE_CONTENT))
           .andExpect(
               status().isBadRequest()
           );
  }

  @Test
  @DisplayName("카테고리 ID가 12개 초과면 400 응답을 한다")
  @Sql("changeMyCategories.sql")
  @WithMockUser(username = "1")
  void should_status_is_400_when_categoryIds_more_than_12() throws Exception {
    mockMvc.perform(put("/api/v1/my/categories")
               .header("Authorization", "Bearer access-token")
               .contentType(MediaType.APPLICATION_JSON)
               .content(MORE_THEN_MAX_SIZE_CONTENT))
           .andExpect(
               status().isBadRequest()
           );
  }

  @Test
  @DisplayName("ID에 해당하는 카테고리가 없으면 400 응답을 한다")
  @Sql("changeMyCategories.sql")
  @WithMockUser(username = "1")
  void should_status_is_400_when_categoryId_is_not_exist() throws Exception {
    mockMvc.perform(put("/api/v1/my/categories")
               .header("Authorization", "Bearer access-token")
               .contentType(MediaType.APPLICATION_JSON)
               .content(NOT_EXIST_CATEGORY_CONTENT))
           .andExpect(
               status().isBadRequest()
           );
  }
}