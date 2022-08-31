package com.example.qsl.base;

import com.example.qsl.domain.SiteUser;
import com.example.qsl.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")    // 이 클래스가 정의된 Bean들은 test 모드에서만 활성화된다.
public class TestInitData {

  // CommandLineRunner : 앱 실행 후 초기 데이터 세팅에 사용
  @Bean
  CommandLineRunner init(UserRepository userRepository) {
    return args -> {
      SiteUser u1 = SiteUser.builder()
          .username("user1")
          .password("1234")
          .email("user1@test.com")
          .build();
      SiteUser u2 = SiteUser.builder()
          .username("user2")
          .password("1234")
          .email("user2@test.com")
          .build();

      List<SiteUser> siteUsers = userRepository.saveAll(Arrays.asList(u1, u2));
    };
  }
}
