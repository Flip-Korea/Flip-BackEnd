package com.flip.flipapp.domain.profile.controller;

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

import com.flip.flipapp.common.HeaderConstants;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class GetMyProfileControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("인증된 사용자면 해당 프로필 정보를 200 응답으로 반환한다")
  @Sql("GetMyProfileControllerTest.sql")
  @WithMockUser(username = "1")
  void should_status_is_200_with_profile_when_user_is_authenticated() throws Exception {
    mockMvc.perform(get("/api/v1/my/profile")
            .header(HeaderConstants.ACCESS_TOKEN.getKey(), HeaderConstants.ACCESS_TOKEN.getValue()))
        .andExpectAll(
            status().isOk(),
            jsonPath("$.userId").value("user1"),
            jsonPath("$.nickname").value("nickname1"),
            jsonPath("$.introduce").value("자기소개1"),
            jsonPath("$.followerCnt").value(0),
            jsonPath("$.followingCnt").value(0),
            jsonPath("$.postCnt").value(0)
        )
        .andDo(
            document("getMyProfile",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer access-token")
                ),
                responseFields(
                    fieldWithPath("userId").description("사용자 ID"),
                    fieldWithPath("nickname").description("닉네임"),
                    fieldWithPath("introduce").description("사용자 소개"),
                    fieldWithPath("followerCnt").description("팔로워 수"),
                    fieldWithPath("followingCnt").description("팔로잉 수"),
                    fieldWithPath("postCnt").description("게시물 수")
                )
            ));
  }
}
