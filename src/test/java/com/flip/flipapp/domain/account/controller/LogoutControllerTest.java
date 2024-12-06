package com.flip.flipapp.domain.account.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import com.flip.flipapp.common.HeaderConstants;
import com.flip.flipapp.common.SpringBootTestWithRestDocs;
import com.flip.flipapp.domain.token.repository.TokenRepository;
import com.flip.flipapp.global.error.exception.CustomMalformedJwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTestWithRestDocs
class LogoutControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  TokenRepository tokenRepository;

  private final String validRefreshToken = "Bearer refresh_token1";

  private final String invalidRefreshToken = "Bearer invalid-refresh-token";

  @Test
  @WithMockUser(username = "1")
  @DisplayName("유효한 리프레쉬 토큰으로 로그아웃 요청 시 204 응답을 반환하고, 토큰이 삭제된다")
  @Sql("LogoutControllerTest.sql")
  void should_return_204_and_delete_token_when_logout_is_successful() throws Exception {
    mockMvc.perform(delete("/api/v1/logout")
            .header(HttpHeaders.AUTHORIZATION, validRefreshToken))
        .andExpect(status().isNoContent())
        .andDo(
            document("logout",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HeaderConstants.REFRESH_TOKEN.getKey())
                        .description(HeaderConstants.REFRESH_TOKEN.getValue())
                )
            )
        );

    // 로그아웃 후 해당 리프레쉬 토큰이 삭제되었는지 확인
    String pureToken = validRefreshToken.replace("Bearer ", "");
    assertThat(tokenRepository.findByRefreshToken(pureToken)).isEmpty();
  }

  @Test
  @WithMockUser(username = "1")
  @DisplayName("존재하지 않는 리프레쉬 토큰으로 로그아웃 요청 시 400 응답을 반환한다")
  void should_return_400_when_logout_token_is_not_found() throws Exception {
    mockMvc.perform(delete("/api/v1/logout")
            .header(HttpHeaders.AUTHORIZATION, invalidRefreshToken))
        .andExpectAll(
            status().isUnauthorized(),
            result -> assertThat(result.getResolvedException())
                .isInstanceOf(CustomMalformedJwtException.class)
        );
  }
}
