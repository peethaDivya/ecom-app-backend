package com.ouhamza.ecommerce;
import com.ouhamza.ecommerce.AbstractTest;
import com.ouhamza.ecommerce.beans.Users;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EcommerceApplicationTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getDataTest() throws Exception {
        String uri = "/viewData";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
//
//        String content = mvcResult.getResponse().getContentAsString();
//        Users[] userlist = super.mapFromJson(content, Users[].class);
//        assertTrue(userlist.length > 0);
    }
    @Test
    public void addUserTest() throws Exception {
        String uri = "/addUser";
        Users user = new Users();
        user.setFirstname("divya");
        user.setLastname("jalla");
        user.setUsername("divya");
        user.setEmail("div@gmail.com");
        user.setregisterId("123");
        user.setPassword("12345");
        user.setPhoneno("1234567890");

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }


    @Test
    public void authUserTest() throws Exception {
        String uri = "/authUser";
        Users user = new Users();
        user.setFirstname("div");
        user.setLastname("j");
        user.setUsername("div");
        user.setEmail("div@g.co");
        user.setregisterId("123");
        user.setPassword("123");
        user.setPhoneno("1233456");

        String inputJson = super.mapToJson(user);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}