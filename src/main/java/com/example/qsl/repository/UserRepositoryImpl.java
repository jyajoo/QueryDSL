package com.example.qsl.repository;


import static com.example.qsl.domain.QSiteUser.siteUser;

import com.example.qsl.domain.SiteUser;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

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

  @Override
  public long getQslCount() {
    return jpaQueryFactory
        .select(siteUser.count())
        .from(siteUser)
        .fetchOne();
  }

  @Override
  public SiteUser getQslOrderByIdAscOne() {
    return jpaQueryFactory
        .select(siteUser)
        .from(siteUser)
        .orderBy(siteUser.id.asc())
        .limit(1)
        .fetchOne();
  }

  @Override
  public List<SiteUser> getQslUsersOrderByIdAsc() {
    return jpaQueryFactory
        .select(siteUser)
        .from(siteUser)
        .orderBy(siteUser.id.asc())
        .fetch();
  }

  @Override
  public List<SiteUser> searchQsl(String kw) {
    return jpaQueryFactory
        .select(siteUser)
        .from(siteUser)
        .where(siteUser.email.contains(kw)
            .or(siteUser.username.contains(kw)))
        .orderBy(siteUser.id.desc())
        .fetch();
  }

  // 내용 가져오는 SQL
        /*
        SELECT site_user.*
        FROM site_user
        WHERE site_user.username LIKE '%user%'
        OR site_user.email LIKE '%user%'
        ORDER BY site_user.id ASC
        LIMIT 1, 1
         */

  // 전체 개수 계산하는 SQL
        /*
        SELECT COUNT(*)
        FROM site_user
        WHERE site_user.username LIKE '%user%'
        OR site_user.email LIKE '%user%'
         */
  @Override
  public Page<SiteUser> searchQsl(String kw, Pageable pageable) {
    JPAQuery<SiteUser> usersQuery = jpaQueryFactory
        .select(siteUser)
        .from(siteUser)
        .where(
            siteUser.username.contains(kw)
                .or(siteUser.email.contains(kw))
        )
        .offset(pageable.getOffset()) // 몇개를 건너 띄어야 하는지 LIMIT {1}, ?
        .limit(pageable.getPageSize()); // 한페이지에 몇개가 보여야 하는지 LIMIT ?, {1}

    for (Sort.Order o : pageable.getSort()) {
      PathBuilder pathBuilder = new PathBuilder(siteUser.getType(), siteUser.getMetadata());
      usersQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
    }

    List<SiteUser> users = usersQuery.fetch();

    JPAQuery<Long> usersCountQuery = jpaQueryFactory
        .select(siteUser.count())
        .from(siteUser)
        .where(
            siteUser.username.contains(kw)
                .or(siteUser.email.contains(kw))
        );

    return PageableExecutionUtils.getPage(users, pageable, usersCountQuery::fetchOne);
  }
}
