package com.flip.flipapp.domain.block.controller;

import static com.flip.flipapp.docs.RestDocsAttributeFactory.constraintsField;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.flipapp.common.HeaderConstants;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.domain.block.controller.dto.request.BlockRequest;
import com.flip.flipapp.domain.block.exception.DuplicatedBlockException;
import com.flip.flipapp.domain.block.exception.SelfBlockException;
import com.flip.flipapp.domain.profile.exception.ProfileNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class BlockControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  BlockRequest VALID_REQUEST = new BlockRequest(2L);
  BlockRequest SELF_BLOCK_REQUEST = new BlockRequest(1L);
  BlockRequest DUPLICATED_BLOCK_REQUEST = new BlockRequest(3L);
  BlockRequest NOT_EXIST_USER_REQUEST = new BlockRequest(999L);

  @Test
  @DisplayName("유효한 요청으로 차단을 하면 201 응답을 반환한다")
  @WithMockUser(username = "1")
  @Sql("BlockControllerTest.sql")
  void should_return_201_when_request_is_valid() throws Exception {
    mockMvc.perform(
            post("/api/v1/block")
                .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(VALID_REQUEST))
        )
        .andExpect(status().isCreated())
        .andDo(
            document("block",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HeaderConstants.ACCESS_TOKEN.getKey())
                        .description(HeaderConstants.ACCESS_TOKEN.getValue())
                ),
                requestFields(
                    fieldWithPath("blockedProfileId").attributes(constraintsField("널 X, MIN 1"))
                        .type(JsonFieldType.NUMBER)
                        .description("차단할 계정 ProfileId")
                ),
                responseHeaders(
                    headerWithName("Location").description("추가된 차단 URI")
                )
            )
        );
  }

  @Test
  @DisplayName("자신을 차단하려고 하면 400 응답을 반환한다")
  @WithMockUser(username = "1")
  @Sql("BlockControllerTest.sql")
  void should_return_400_when_self_block() throws Exception {
    mockMvc.perform(
            post("/api/v1/block")
                .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(SELF_BLOCK_REQUEST))
        )
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertThat(result.getResolvedException())
            .isInstanceOf(SelfBlockException.class));
  }

  @Test
  @DisplayName("이미 차단된 사용자를 다시 차단하려 하면 400 응답을 반환한다")
  @WithMockUser(username = "1")
  @Sql("BlockControllerTest.sql")
  void should_return_400_when_already_blocked() throws Exception {
    mockMvc.perform(
            post("/api/v1/block")
                .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(DUPLICATED_BLOCK_REQUEST))
        )
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertThat(result.getResolvedException())
            .isInstanceOf(DuplicatedBlockException.class));
  }

  @Test
  @DisplayName("차단할 유저가 존재하지 않으면 400 응답을 반환한다")
  @WithMockUser(username = "1")
  void should_return_400_when_user_does_not_exist() throws Exception {
    mockMvc.perform(
            post("/api/v1/block")
                .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(NOT_EXIST_USER_REQUEST))
        )
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertThat(result.getResolvedException())
            .isInstanceOf(ProfileNotFoundException.class));
  }
}
