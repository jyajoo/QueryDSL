package com.example.qsl.repository;

import static org.assertj.core.api.Assertions.assertThat;
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
    SiteUser u1 = SiteUser.builder()
        .usename("user1")
        .password("1234")
        .email("user1@test.com")
        .build();

    SiteUser u2 = SiteUser.builder()
        .usename("user2")
        .password("1234")
        .email("user2@test.com")
        .build();

    userRepository.saveAll(Arrays.asList(u1, u2));
  }

  @Test
  @DisplayName("1번 회원을 Qsl로 가져오기")
  void t2() {
    SiteUser u1 = userRepository.getQslUser(1L);

    assertThat(u1.getId()).isEqualTo(1L);
    assertThat(u1.getUsename()).isEqualTo("user1");
    assertThat(u1.getEmail()).isEqualTo("user1@test.com");
    assertThat(u1.getPassword()).isEqualTo("1234");
  }

  @Test
  @DisplayName("2번 회원을 Qsl로 가져오기")
  void t3() {
    SiteUser u1 = userRepository.getQslUser(2L);

    assertThat(u1.getId()).isEqualTo(2L);
    assertThat(u1.getUsename()).isEqualTo("user2");
    assertThat(u1.getEmail()).isEqualTo("user2@test.com");
    assertThat(u1.getPassword()).isEqualTo("1234");
  }
}