package com.seatig.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seatig.domain.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 企业信息表 Mapper 接口
 * </p>
 *
 * @author xianzw
 * @since 2019-03-04
 */
public interface CompanyMapper extends BaseMapper<Company> {



	/**
	 * @Description: 根据查询条件返回企业 or 机构信息
	 * @Param: [search]
	 * @return: java.util.List<com.seatig.quanaxy.entity.user.Company>
	 * @Author: glenn
	 * @Date: 2019/7/8
	 */
	List<Company> getCompanyByStr(Page<Company> page, @Param("search") String search);



}
