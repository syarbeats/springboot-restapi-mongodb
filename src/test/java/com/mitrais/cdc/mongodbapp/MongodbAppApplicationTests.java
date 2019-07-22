package com.mitrais.cdc.mongodbapp;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.awt.*;
import java.nio.charset.Charset;

import com.mitrais.cdc.mongodbapp.model.User;
import com.mitrais.cdc.mongodbapp.security.MongoUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
//import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@Slf4j
public class MongodbAppApplicationTests {

    private final String USERNAME_FOR_ID_5d22a9d168ed663ca092a574 = "user1";
    private final String ROLE_FOR_ID_5d22a9d168ed663ca092a574 = "ROLE_USER";
    private final String ID = "5d22a9d168ed663ca092a574";
    private final String USERNAME = "user1";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private MockMvc mockMvc;
    private UserDetails userDetails;
    private Authentication authToken;

    @Autowired
    MongoUserDetailService mongoUserDetailService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;


    @Before
    public void contextLoads() {
        userDetails = mongoUserDetailService.loadUserByUsername("admin");
        authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void userRegistration() throws Exception{
        User user = new User("arkhyterima", "pass123", true, "ROLE_USER", "srf.hidayat@gmail.com");
        String userJson = mapper.writeValueAsString(user);

        mockMvc.perform(post("/api/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userJson)
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['message']", containsString("Check your email to activate your account")))
                .andExpect(jsonPath("$['contents']['username']", containsString("arkhyterima")))
                .andExpect(jsonPath("$['contents']['role']", containsString("ROLE_USER")));
    }

    @Test
    public void UpdateUserData() throws Exception{

        User user = new User("user1", "user999", true, "ROLE_USER", "srf.hidayat@gmail.com");
        String userJson = mapper.writeValueAsString(user);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.patch("/api/update/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['contents']['message']", containsString("Update user data has been updated successfully")))
                .andExpect(jsonPath("$['contents']['data']['username']", containsString("user1")))
                .andExpect(jsonPath("$['contents']['data']['role']", containsString("ROLE_USER")));
    }

    @Test
    public void deleteUserByUsername() throws Exception{
        String username = "arkhyterima";
        mockMvc.perform(delete("/api/delete/user/"+username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['contents']['message']", containsString("Delete user data has been executed successfully")))
                .andExpect(jsonPath("$['contents']['data']", containsString("arkhyterima")));

    }


    @Test
    public void findUserByID() throws Exception{

        mockMvc.perform(get("/api/find/user/"+ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$['contents']['data']['username']", containsString(USERNAME_FOR_ID_5d22a9d168ed663ca092a574)));

    }

    @Test
    public void findUserByUsername() throws Exception{

        mockMvc.perform(get("/api/find-user-by-username/"+USERNAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$['contents']['data']['role']", containsString(ROLE_FOR_ID_5d22a9d168ed663ca092a574)));

    }

    @Test
    public void getAllUsers() throws Exception{

        mockMvc.perform(get("/api/all-users/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$['contents']['message']", containsString("Users data was founds")))
                .andExpect(jsonPath("$['contents']['data'][0]['username']", containsString("admin")))
                .andExpect(jsonPath("$['contents']['data'][0]['role']", containsString("ROLE_ADMIN")));
    }



}
