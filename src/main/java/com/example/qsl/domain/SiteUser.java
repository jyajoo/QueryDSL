package com.example.qsl.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;

  @Column(unique = true)
  private String email;

  @Builder.Default
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Set<InterestKeyword> interestKeywords = new HashSet<>();

  @Builder.Default
  @ManyToMany(cascade = CascadeType.ALL)
  private Set<SiteUser> followers = new HashSet<>();

  @Builder.Default
  @ManyToMany(cascade = CascadeType.ALL)
  private Set<SiteUser> following = new HashSet<>();

  public void addInterestKeywordContent(String keywordContent) {
    interestKeywords.add(new InterestKeyword(this, keywordContent));
  }

  public void follow(SiteUser following) {
    if(this == following) return;
    if(following == null) return;;
    if(this.getId().equals(following.getId())) return;
    following.getFollowers().add(this);
    getFollowing().add(following);
  }
}
