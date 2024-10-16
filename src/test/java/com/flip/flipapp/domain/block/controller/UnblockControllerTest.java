package com.flip.flipapp.domain.block.controller;

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
import com.flip.flipapp.domain.block.exception.BlockNotFoundException;
import com.flip.flipapp.domain.block.exception.NotBlockerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class UnblockControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @WithMockUser(username = "1")
  @Sql("UnblockControllerTest.sql")
  @DisplayName("요청이 유효한 경우 성공적으로 차단 해제를 하고 204 응답을 한다")
  void should_return_204_status_when_request_is_valid() throws Exception {
    mockMvc.perform(
            delete("/api/v1/block/{blockId}", 1L)
                .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
        )
        .andExpect(status().isNoContent())
        .andDo(
            document("unblock",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HeaderConstants.ACCESS_TOKEN.getKey())
                        .description(HeaderConstants.ACCESS_TOKEN.getValue())
                ),
                pathParameters(
                    parameterWithName("blockId")
                        .attributes(RestDocsAttributeFactory.constraintsField("MIN 1"))
                        .description("차단 해제할 blockId")
                )));
  }

  @Test
  @WithMockUser(username = "1")
  @Sql("UnblockControllerTest.sql")
  @DisplayName("ID에 해당하는 차단이 없는 경우 400 응답을 한다")
  void should_return_400_status_when_block_is_not_exist() throws Exception {
    mockMvc.perform(
            delete("/api/v1/block/{blockId}", 999L)
                .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
        )
        .andExpectAll(
            status().isBadRequest(),
            result -> assertThat(result.getResolvedException())
                .isInstanceOf(BlockNotFoundException.class)
        );
  }

  @Test
  @WithMockUser(username = "2")
  @Sql("UnblockControllerTest.sql")
  @DisplayName("해당 차단의 차단자가 아닌 경우 400 응답을 한다")
  void should_return_400_status_when_not_blocker() throws Exception {
    mockMvc.perform(
            delete("/api/v1/block/{blockId}", 1L)
                .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
        )
        .andExpectAll(
            status().isBadRequest(),
            result -> assertThat(result.getResolvedException())
                .isInstanceOf(NotBlockerException.class)
        );
  }
}
