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
import org.springframework.test.annotation.Rollback;
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
    int totalPages = (int) Math.ceil(totalCount / (double) pageSize);
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
    int totalPages = (int) Math.ceil(totalCount / (double) pageSize);
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

  @Test
  @DisplayName("회원에게 관심사를 등록할 수 있다.")
  void t10() {
    SiteUser u2 = userRepository.getQslUser(2L);

    u2.addInterestKeywordContent("축구");
    u2.addInterestKeywordContent("야구");
    u2.addInterestKeywordContent("농구");
    u2.addInterestKeywordContent("농구");

    userRepository.save(u2);
  }

  @Test
  @DisplayName("축구에 관심이 있는 회원들 검색")
  void t11() {
    List<SiteUser> users = userRepository.getQslUsersByInterestKeyword("축구");

    assertThat(users.size()).isEqualTo(1);

    SiteUser u = users.get(0);

    assertThat(u.getId()).isEqualTo(1L);
    assertThat(u.getUsername()).isEqualTo("user1");
    assertThat(u.getEmail()).isEqualTo("user1@test.com");
    assertThat(u.getPassword()).isEqualTo("1234");
  }

  @Test
  @DisplayName("no qsl, 축구에 관심이 있는 회원들 검색")
  void t12() {
    List<SiteUser> users = userRepository.findByInterestKeywords_content("축구");

    assertThat(users.size()).isEqualTo(1);

    SiteUser u = users.get(0);

    assertThat(u.getId()).isEqualTo(1L);
    assertThat(u.getUsername()).isEqualTo("user1");
    assertThat(u.getEmail()).isEqualTo("user1@test.com");
    assertThat(u.getPassword()).isEqualTo("1234");
  }

  @Test
  @DisplayName("u2 = 아이돌, u1 = 팬 / u1은 u2의 팔로워")
  void t13() {
    SiteUser u1 = userRepository.getQslUser(1L);
    SiteUser u2 = userRepository.getQslUser(2L);
    u2.follow(u1);
    userRepository.save(u2);
  }

  @Test
  @DisplayName("본인이 본인 follow 불가능")
  @Rollback(false)
  void t14() {
    SiteUser u1 = userRepository.getQslUser(1L);
    u1.follow(u1);
    assertThat(u1.getFollowers().size()).isEqualTo(0);
  }

  @Test
  @DisplayName("특정회원의 follower들과 following들을 모두 알 수 있어야 한다.")
  @Rollback(false)
  void t15() {
    SiteUser u1 = userRepository.getQslUser(1L);
    SiteUser u2 = userRepository.getQslUser(2L);

    u1.follow(u2);

    // follower
    // u1의 구독자 : 0
    assertThat(u1.getFollowers().size()).isEqualTo(0);

    // follower
    // u2의 구독자 : 1
    assertThat(u2.getFollowers().size()).isEqualTo(1);

    // following
    // u1이 구독중인 회원 : 1
    assertThat(u1.getFollowing().size()).isEqualTo(1);

    // following
    // u2가 구독중인 회원 : 0
    assertThat(u2.getFollowing().size()).isEqualTo(0);
  }
}