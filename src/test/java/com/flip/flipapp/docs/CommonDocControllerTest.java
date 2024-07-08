package com.flip.flipapp.docs;

import static com.flip.flipapp.docs.ErrorFieldDescription.CODE;
import static com.flip.flipapp.docs.ErrorFieldDescription.ERROR_LIST_FIELD;
import static com.flip.flipapp.docs.ErrorFieldDescription.ERROR_LIST_REASON;
import static com.flip.flipapp.docs.ErrorFieldDescription.ERROR_LIST_VALUE;
import static com.flip.flipapp.docs.ErrorFieldDescription.MESSAGE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.flipapp.docs.CommonDocController.SampleRequest;
import com.flip.flipapp.global.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
class CommonDocControllerTest {

  ObjectMapper objectMapper = new ObjectMapper();
  MockMvc mockMvc;
  CommonDocController commonDocController = new CommonDocController();

  @BeforeEach
  void setup(RestDocumentationContextProvider restDocumentation) {
    mockMvc = MockMvcBuilders.standaloneSetup(commonDocController)
                             .apply(documentationConfiguration(restDocumentation))
                             .setControllerAdvice(GlobalExceptionHandler.class)
                             .addFilter(new CharacterEncodingFilter("UTF-8", true))
                             .build();
  }

  @Test
  void test() throws Exception {
    SampleRequest sampleRequest = new SampleRequest("", "email");
    mockMvc.perform(
               post("/sample/error")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsBytes(sampleRequest)))
           .andExpect(status().isBadRequest())
           .andDo(
               document("sample-error-response",
                   preprocessRequest(prettyPrint()),
                   preprocessResponse(prettyPrint()),
                   responseFields(
                       fieldWithPath(CODE.getField()).description(CODE.getDescription()),
                       fieldWithPath(MESSAGE.getField()).description(MESSAGE.getDescription()),
                       fieldWithPath(ERROR_LIST_FIELD.getField())
                           .description(ERROR_LIST_FIELD.getDescription()).optional(),
                       fieldWithPath(ERROR_LIST_VALUE.getField())
                           .description(ERROR_LIST_VALUE.getDescription()).optional(),
                       fieldWithPath(ERROR_LIST_REASON.getField())
                           .description(ERROR_LIST_REASON.getDescription()).optional()
                   )
               )
           );
  }
}