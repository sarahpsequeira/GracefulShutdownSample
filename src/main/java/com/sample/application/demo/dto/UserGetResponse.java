package com.sample.application.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserGetResponse {

    private String firstName;

    private String lastName;

    private String address;
}
