package com.flip.flipapp.domain.category.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class GetAllCategoriesControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("카테고리 목록이 담긴 200 응답을 한다")
  @Sql("getAllCategories.sql")
  void should_status_is_200_when_request_come() throws Exception {
    mockMvc.perform(get("/api/v1/categories")
               .contentType(APPLICATION_JSON))
           .andExpectAll(
               status().isOk(),
               jsonPath("$.size()").value(4),
               jsonPath("$[0].categoryId").value(1)
           )
           .andDo(
               document("getAllCategories",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   responseFields(
                       fieldWithPath("[].categoryId").type(JsonFieldType.NUMBER)
                                                     .description("카테고리 ID"),
                       fieldWithPath("[].categoryName").type(JsonFieldType.STRING)
                                                       .description("카테고리명")
                   )
               )
           );
  }
}