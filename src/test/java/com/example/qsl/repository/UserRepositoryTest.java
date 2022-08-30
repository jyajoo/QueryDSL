package com.example.qsl.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.qsl.domain.SiteUser;
import java.util.Arrays;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Transactional          // 각 테스트케이스에 모두 붙게 된다. 자동 롤백
@ActiveProfiles("test")
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("회원 생성")
  void t1() {
    SiteUser u3 = SiteUser.builder()
        .usename("user3")
        .password("1234")
        .email("user3@test.com")
        .build();

    SiteUser u4 = SiteUser.builder()
        .usename("user4")
        .password("1234")
        .email("user4@test.com")
        .build();

    userRepository.saveAll(Arrays.asList(u3, u4));
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