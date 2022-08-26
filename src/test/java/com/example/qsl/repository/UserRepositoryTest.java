package com.example.qsl.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.qsl.domain.SiteUser;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("회원 생성")
  void t1() {
    SiteUser u1 = new SiteUser(null, "user1", "1234", "user1@test.com");
    SiteUser u2 = new SiteUser(null, "user2", "1234", "user2@test.com");

    userRepository.saveAll(Arrays.asList(u1, u2));
  }
}