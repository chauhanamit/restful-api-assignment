package com.restfulapi.assignment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfulapi.assignment.RestfulApiAssignmentApplication;
import com.restfulapi.assignment.model.JwtRequest;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RestfulApiAssignmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper mapper;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void testCreateAuthenticationToken() throws Exception {

		JwtRequest jwtRequest = new JwtRequest("user1", "12345");
		String jsonRequest = mapper.writeValueAsString(jwtRequest);
		MvcResult result = mockMvc
				.perform(post("/authenticate").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		JSONObject obj = new JSONObject(response);
		String token = obj.getString("jwttoken");
		MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.get("/employees")
				.header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals(200, result2.getResponse().getStatus());

	}
}
