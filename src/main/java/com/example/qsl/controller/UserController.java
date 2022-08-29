package com.example.qsl.controller;

import com.example.qsl.domain.SiteUser;
import com.example.qsl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;

  @RequestMapping("/user/{id}")
  public SiteUser user(@PathVariable Long id) {
    return userRepository.getQslUser(id);
  }
}
