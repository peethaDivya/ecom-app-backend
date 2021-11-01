package com.ouhamza.ecommerce;

import com.ouhamza.ecommerce.entity.Grocery;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.ouhamza.ecommerce.controller.GroceryController;

public class GroceryControllerTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getGrocerysList() throws Exception {
        String uri = "/Grocerys/get";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Grocery[] grocerylist = super.mapFromJson(content, Grocery[].class);
        assertTrue(grocerylist.length > 0);
    }

    @Test
    public void getGrocerysListByid() throws Exception {
        String uri = "/Grocerys/get/13";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Grocery grocerylist = super.mapFromJson(content, Grocery.class);
        assertTrue(grocerylist.getId() == 13);
    }

    @Test
    public void createGrocery() throws Exception {
        String uri = "/Grocerys/add";
        Grocery grocery = new Grocery();
        grocery.setName("starbucks coffee");
        grocery.setBrand("coffee");
        grocery.setPrice(150.0F);

        String inputJson = super.mapToJson(grocery);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void deleteGrocery() throws Exception {
        String uri = "/Grocerys/9";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}
