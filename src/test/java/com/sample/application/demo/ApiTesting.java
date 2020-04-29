package com.sample.application.demo;

import static org.testng.Assert.*;

import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.Test;
import org.springframework.http.HttpStatus;

public class ApiTesting {

    private static final String API_ROOT
            = "http://localhost:8081/api/users";

    @Test
    public void test_getAllUsers() {
        Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void test_getUsersByLastName() {
        Response response = RestAssured.get(
                API_ROOT + "/lastName/" + "Doe");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
                .size() > 0);
    }




}
