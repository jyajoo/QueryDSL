package com.example.qsl.repository;

import static com.example.qsl.domain.QSiteUser.siteUser;

import com.example.qsl.domain.QSiteUser;
import com.example.qsl.domain.SiteUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public SiteUser getQslUser(Long id) {
    /*
    SELECT *
    FROM site_user
    WHERE id = 1
     */

    return jpaQueryFactory
        .select(siteUser)
        .from(siteUser)
        .where(siteUser.id.eq(id))
        .fetchOne();
  }
}
