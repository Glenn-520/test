package com.seatig.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seatig.domain.Company;
import com.seatig.domain.User;


import java.util.List;

/**
 * <p>
 * 企业信息表 服务类
 * </p>
 *
 * @author xianzw
 * @since 2019-03-04
 */
public interface ICompanyService extends IService<Company> {


    /**
     * @Description:根据查询条件返回企业 or 机构信息
     * @Param: [search]
     * @return: java.util.List<com.seatig.quanaxy.entity.user.Company>
     * @Author: glenn
     * @Date: 2019/7/8
     */
    List<Company> getCompanyByStr(Page<Company> pageString, String search);

    List<User> getBlackList(Page<Company> page, String search);
}
