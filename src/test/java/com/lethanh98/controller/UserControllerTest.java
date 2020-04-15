package com.lethanh98.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lethanh98.dto.request.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class UserControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void saveBody() throws Exception {
        UserRequestDTO user = new UserRequestDTO();
        user.setFirstName("le");
        user.setLastName("thanh");
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/users/new")
                .content(this.objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(document("api/users/new-user",
                        requestFields(
                                fieldWithPath("firstName")
                                        .description("firstName"),
                                fieldWithPath("lastName")
                                        .description("lastName")),
                        responseFields(
                                fieldWithPath("status")
                                        .description("Trạng thái của request"),
                                fieldWithPath("data")
                                        .description("data nếu có"),
                                fieldWithPath("data.id")
                                        .description("id"),
                                fieldWithPath("data.firstName")
                                        .description("firstName"),
                                fieldWithPath("data.lastName")
                                        .description("lastName")
                        )
                ));
    }

    @Test
    public void saveParam() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/users")
                .param("firstName", "le")
                .param("lastName", "thanh")
                .content("{}").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(document("api/users/new-user-param",
                        requestParameters(
                                parameterWithName("firstName")
                                .description("firstName"),
                                parameterWithName("lastName")
                                .description("tên cuối")),
                        responseFields(
                                fieldWithPath("status")
                                        .description("Trạng thái của request"),
                                fieldWithPath("data")
                                        .description("data nếu có"),
                                fieldWithPath("data.id")
                                        .description("id"),
                                fieldWithPath("data.firstName")
                                        .description("firstName"),
                                fieldWithPath("data.lastName")
                                        .description("lastName")
                        )
                ));
    }

    @Test
    public void getByIdOK() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(document("api/users/get-by-id",
                        pathParameters(parameterWithName("id")
                                .description("Identifier of the person to be obtained.")),
                        responseFields(
                                fieldWithPath("status")
                                        .description("Trạng thái của request"),
                                fieldWithPath("data")
                                        .description("data nếu có"),
                                fieldWithPath("data.id")
                                        .description("id"),
                                fieldWithPath("data.firstName")
                                        .description("firstName"),
                                fieldWithPath("data.lastName")
                                        .description("lastName")
                        )
                ));
    }

    @Test
    public void getByNotFound() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/users/{id}", 10000))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(document("api/users/get-by-id-not-found",
                        pathParameters(parameterWithName("id")
                                .description("Identifier of the person to be obtained.")),
                        responseFields(
                                fieldWithPath("status")
                                        .description("Trạng thái của request"),
                                fieldWithPath("data")
                                        .description("data nếu có")
                        )
                ));
    }

    @Test
    public void getALL() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(document("api/users/get-all",
                        pathParameters(),
                        responseFields(
                                fieldWithPath("status")
                                        .description("Trạng thái của request"),
                                fieldWithPath("data")
                                        .description("data nếu có"),
                                fieldWithPath("data[].id")
                                        .description("id"),
                                fieldWithPath("data[].firstName")
                                        .description("firstName"),
                                fieldWithPath("data[].lastName")
                                        .description("lastName")
                        )
                ));
    }
}
