package com.example.qsl.repository;

import com.example.qsl.domain.SiteUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long>, UserRepositoryCustom {

  List<SiteUser> findByInterestKeywords_content(String content);
}
