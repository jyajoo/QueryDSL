package com.example.qsl.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(InterestKeywordId.class)
public class InterestKeyword {

  @Id
  @ManyToOne
  @EqualsAndHashCode.Include
  private SiteUser user;

  @Id
  @EqualsAndHashCode.Include
  private String content;
}
