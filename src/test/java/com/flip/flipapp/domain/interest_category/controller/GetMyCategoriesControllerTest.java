package com.flip.flipapp.domain.interest_category.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class GetMyCategoriesControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("인증된 사용자면 사용자에 해당하는 관심 카테고리 목록이 담긴 200 응답을 한다")
  @Sql("getMyCategories.sql")
  @WithMockUser(username = "1")
  void should_status_is_200_with_interest_categories_when_user_is_authenticated() throws Exception {
    mockMvc.perform(get("/api/v1/my/categories")
               .header("Authorization", "Bearer access-token"))
           .andExpectAll(
               status().isOk(),
               jsonPath("$.size()").value(2)
           )
           .andDo(
               document("getMyCategories",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   requestHeaders(
                       headerWithName("Authorization").description("Bearer access-token")
                   ),
                   responseFields(
                       fieldWithPath("[].categoryId").description("카테고리 ID"),
                       fieldWithPath("[].categoryName").description("카테고리명")
                   )
               ));
  }
}