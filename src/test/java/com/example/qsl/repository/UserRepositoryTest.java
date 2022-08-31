package com.example.qsl.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.qsl.domain.SiteUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        .username("user3")
        .password("1234")
        .email("user3@test.com")
        .build();

    SiteUser u4 = SiteUser.builder()
        .username("user4")
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
    assertThat(u1.getUsername()).isEqualTo("user1");
    assertThat(u1.getEmail()).isEqualTo("user1@test.com");
    assertThat(u1.getPassword()).isEqualTo("1234");
  }

  @Test
  @DisplayName("2번 회원을 Qsl로 가져오기")
  void t3() {
    SiteUser u1 = userRepository.getQslUser(2L);

    assertThat(u1.getId()).isEqualTo(2L);
    assertThat(u1.getUsername()).isEqualTo("user2");
    assertThat(u1.getEmail()).isEqualTo("user2@test.com");
    assertThat(u1.getPassword()).isEqualTo("1234");
  }

  @Test
  @DisplayName("모든 회원 수")
  void t4() {
    long count = userRepository.getQslCount();

    assertThat(count).isGreaterThan(0);
  }

  @Test
  @DisplayName("가장 오래된 회원 1명")
  void t5() {
    SiteUser u1 = userRepository.getQslOrderByIdAscOne();

    assertThat(u1.getId()).isEqualTo(1L);
    assertThat(u1.getUsername()).isEqualTo("user1");
    assertThat(u1.getPassword()).isEqualTo("1234");
    assertThat(u1.getEmail()).isEqualTo("user1@test.com");
  }

  @Test
  @DisplayName("전체 회원, 오래된 순")
  void t6() {
    List<SiteUser> siteUsers = userRepository.getQslUsersOrderByIdAsc();
    SiteUser u1 = siteUsers.get(0);
    assertThat(u1.getId()).isEqualTo(1L);
    assertThat(u1.getUsername()).isEqualTo("user1");
    assertThat(u1.getPassword()).isEqualTo("1234");
    assertThat(u1.getEmail()).isEqualTo("user1@test.com");

    SiteUser u2 = siteUsers.get(1);
    assertThat(u2.getId()).isEqualTo(2L);
    assertThat(u2.getUsername()).isEqualTo("user2");
    assertThat(u2.getPassword()).isEqualTo("1234");
    assertThat(u2.getEmail()).isEqualTo("user2@test.com");
  }

  @Test
  @DisplayName("검색, List 리턴")
  void t7() {
    // 검색대상 : username, email
    List<SiteUser> users = userRepository.searchQsl("user1");

    assertThat(users.size()).isEqualTo(1);

    SiteUser u1 = users.get(0);
    assertThat(u1.getId()).isEqualTo(1L);
    assertThat(u1.getUsername()).isEqualTo("user1");
    assertThat(u1.getPassword()).isEqualTo("1234");
    assertThat(u1.getEmail()).isEqualTo("user1@test.com");

    users = userRepository.searchQsl("user2");
    SiteUser u2 = users.get(0);
    assertThat(u2.getId()).isEqualTo(2L);
    assertThat(u2.getUsername()).isEqualTo("user2");
    assertThat(u2.getPassword()).isEqualTo("1234");
    assertThat(u2.getEmail()).isEqualTo("user2@test.com");
  }

  @Test
  @DisplayName("검색, Page 리턴, id ASC, pageSize=1, page=0")
  void t8() {
    long totalCount = userRepository.count();
    int pageSize = 1; // 한 페이지에 보여줄 아이템 개수
    int totalPages = (int)Math.ceil(totalCount / (double)pageSize);
    int page = 1;
    String kw = "user";

    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.asc("id"));
    Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts)); // 한 페이지에 10까지 가능
    Page<SiteUser> usersPage = userRepository.searchQsl(kw, pageable);

    assertThat(usersPage.getTotalPages()).isEqualTo(totalPages);
    assertThat(usersPage.getNumber()).isEqualTo(page);
    assertThat(usersPage.getSize()).isEqualTo(pageSize);

    List<SiteUser> users = usersPage.get().toList();

    assertThat(users.size()).isEqualTo(pageSize);

    SiteUser u = users.get(0);

    assertThat(u.getId()).isEqualTo(2L);
    assertThat(u.getUsername()).isEqualTo("user2");
    assertThat(u.getEmail()).isEqualTo("user2@test.com");
    assertThat(u.getPassword()).isEqualTo("1234");
  }

  @Test
  @DisplayName("검색, Page 리턴, id DESC, pageSize=1, page=0")
  void t9() {
    long totalCount = userRepository.count();
    int pageSize = 1; // 한 페이지에 보여줄 아이템 개수
    int totalPages = (int)Math.ceil(totalCount / (double)pageSize);
    int page = 1;
    String kw = "user";

    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("id"));
    Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts)); // 한 페이지에 10까지 가능
    Page<SiteUser> usersPage = userRepository.searchQsl(kw, pageable);

    assertThat(usersPage.getTotalPages()).isEqualTo(totalPages);
    assertThat(usersPage.getNumber()).isEqualTo(page);
    assertThat(usersPage.getSize()).isEqualTo(pageSize);

    List<SiteUser> users = usersPage.get().toList();

    assertThat(users.size()).isEqualTo(pageSize);

    SiteUser u = users.get(0);

    assertThat(u.getId()).isEqualTo(1L);
    assertThat(u.getUsername()).isEqualTo("user1");
    assertThat(u.getEmail()).isEqualTo("user1@test.com");
    assertThat(u.getPassword()).isEqualTo("1234");
  }
}