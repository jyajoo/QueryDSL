package com.example.qsl.repository;

import com.example.qsl.domain.SiteUser;

public interface UserRepositoryCustom {

  SiteUser getQslUser(Long id);
}