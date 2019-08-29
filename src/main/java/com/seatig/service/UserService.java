package com.seatig.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seatig.domain.Company;
import com.seatig.domain.User;

import java.util.List;

public interface UserService extends IService<User> {



    List<User> getBlackList(Page<User> page, String search);

    User findUserbyUserName(String username);
}
