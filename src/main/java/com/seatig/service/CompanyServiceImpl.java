package com.seatig.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seatig.dao.CompanyMapper;
import com.seatig.domain.Company;

import com.seatig.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 企业信息表 服务实现类
 * </p>
 *
 * @author xianzw
 * @since 2019-03-04
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {



	/**
	 * @Description: 根据查询条件返回企业 or 机构信息
	 * @Param: [search]
	 * @return: java.util.List<com.seatig.quanaxy.entity.user.Company>
	 * @Author: glenn
	 * @Date: 2019/7/8
	 */
	@Override
	public List<Company> getCompanyByStr(Page<Company> page, String search) {

		return baseMapper.getCompanyByStr(page, search);
	}

	@Override
	public List<User> getBlackList(Page<Company> page, String search) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("locked", "true");
		if (search != null) {
			queryWrapper.like("phone", search);
			queryWrapper.or();
			queryWrapper.like("email", search);
		}
		return baseMapper.selectList(queryWrapper);
	}


}
