package com.seatig.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seatig.dao.CompanyMapper;
import com.seatig.dao.UserDao;
import com.seatig.dao.UserMapper;
import com.seatig.domain.Company;
import com.seatig.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImp extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public List<User> getBlackList(Page<User> page, String search) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("locked", "true");
        if (search != null) {
            queryWrapper.like("phone", search);
            queryWrapper.or();
            queryWrapper.like("email", search);
        }

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public User findUserbyUserName(String username) {

        User user = userDao.loadUserByUsername(username);
        return user;
    }
}
