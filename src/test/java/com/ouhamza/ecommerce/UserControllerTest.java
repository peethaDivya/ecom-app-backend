package com.ouhamza.ecommerce;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ouhamza.ecommerce.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.ouhamza.ecommerce.controller.UserController;

public class UserControllerTest extends AbstractTest {

   @Override
   @Before
   public void setUp() {
      super.setUp();
   }

   @Test
   public void getUsersList() throws Exception {
      String uri = "/users/get";
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      User[] userlist = super.mapFromJson(content, User[].class);
      assertTrue(userlist.length > 0);
   }
   @Test
   public void createUser() throws Exception {
      String uri = "/users/add";
      User user = new User();
      user.setName("Divya");
      user.setPassword("12345");
      user.setType("divya");

      String inputJson = super.mapToJson(user);
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(inputJson)).andReturn();

      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      }
   
   @Test
   public void deleteUser() throws Exception {
      String uri = "/users/5";
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
   }
}