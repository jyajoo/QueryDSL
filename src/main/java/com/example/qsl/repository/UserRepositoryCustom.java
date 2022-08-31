package com.example.qsl.repository;

import com.example.qsl.domain.SiteUser;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

  SiteUser getQslUser(Long id);

  long getQslCount();

  SiteUser getQslOrderByIdAscOne();

  List<SiteUser> getQslUsersOrderByIdAsc();

  List<SiteUser> searchQsl(String kw);

  Page<SiteUser> searchQsl(String kw, Pageable pageable);
}