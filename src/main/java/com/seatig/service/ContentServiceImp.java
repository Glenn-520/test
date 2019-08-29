package com.seatig.service;

import com.seatig.dao.ContentDao;
import com.seatig.domain.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContentServiceImp implements ContentService {

    @Autowired
    ContentDao contentDao;

    @Override
    public List<Content> getContent(int num, int page) {
        page=(page-1)*num;
        Map<String,Object> map=new HashMap<>();
        map.put("num",num);
        map.put("page",page);
        return contentDao.getContent(map);
    }

    @Override
    public void setContent(Content content) {
         contentDao.setContent(content);
    }

    @Override
    public Map<String, Integer> test() {
        return contentDao.test();
    }
}
