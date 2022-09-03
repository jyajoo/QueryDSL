package com.example.qsl.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestKeywordId implements Serializable {

  private SiteUser user;
  private String content;
}
