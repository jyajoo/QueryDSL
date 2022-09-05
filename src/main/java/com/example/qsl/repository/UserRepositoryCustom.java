package com.example.qsl.repository;

import com.example.qsl.domain.SiteUser;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

  SiteUser getQslUser(Long id);

  Long getQslCount();

  SiteUser getQslOrderByIdAscOne();

  List<SiteUser> getQslUsersOrderByIdAsc();

  List<SiteUser> searchQsl(String kw);

  Page<SiteUser> searchQsl(String kw, Pageable pageable);

  List<SiteUser> getQslUsersByInterestKeyword(String keywordContent);

  List<String> getKeywordContentsByFollowingsOf(SiteUser user);
}