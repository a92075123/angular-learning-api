package com.example.api.config.security;

import com.example.api.generate.po.UserEntity;
import com.example.api.mappers.UserMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserMapper userMapper;

  public CustomUserDetailsService(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
    UserEntity entity = userMapper.findByAccount(account);
    if (entity == null) {
      throw new UsernameNotFoundException("帳號不存在: " + account);
    }
    //這裡的User是SpringSecurity內建的User類別 裡面包含 帳號、加密後的密碼、權限清單
    return new User(entity.getAccount(), entity.getPassword(),
        AuthorityUtils.createAuthorityList("ROLE_USER"));
  }
}
