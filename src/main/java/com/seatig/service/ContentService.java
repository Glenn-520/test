package com.seatig.service;

import com.seatig.domain.Content;

import java.util.List;
import java.util.Map;

public interface ContentService {
    List<Content> getContent(int num, int page);

    void setContent(Content content);

    Map<String,Integer> test();
}
