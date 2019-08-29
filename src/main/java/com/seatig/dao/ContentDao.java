package com.seatig.dao;

import com.seatig.domain.Content;

import java.util.List;
import java.util.Map;

public interface ContentDao {

    List<Content> getContent(Map<String, Object> map);

    void setContent(Content content);

    Map<String, Integer> test();
}
