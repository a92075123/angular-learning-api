package com.example.api.dto;

import java.util.Date;
import lombok.Data;

@Data
public class UserDto {

  private String email;

  private String password;

  private Date createdAt;

  private Date updatedAt;

  private String account;
}
