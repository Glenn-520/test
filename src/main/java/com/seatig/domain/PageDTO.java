package com.seatig.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seatig.utils.ObjectUtil;
import lombok.Data;

/**
 * 分页排序
 * @description: 
 * @author: xianzw
 * @time: 2019年3月20日下午3:11:41
 */
@Data
public class PageDTO<T> {
	
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	
	/**
	 *  当前页
	 */
	private int limit = 1;
	
	/**
	 * 每页显示条数
	 */
	private int offset = 1000;
	
	/**
	 * 排序字段
	 */
	private String sort;
	
	/**
	 * 排序类型
	 */
	private String sortType = ASC;
	
	/**
	 * 构造mybatis-plus分页page对象
	 * @Description:   
	 * @param: @return      
	 * @return: Page<T>      
	 * @throws
	 */
	public Page<T> page(){
		Page<T> page = new Page<T>(limit,offset);
		if(ObjectUtil.isNotEmpty(sort)){
			if(ASC.equals(sortType)){
				page.setAsc(ObjectUtil.formatString(sort));
			}else{
				page.setDesc(ObjectUtil.formatString(sort));
			}
		}
		return page;
	}

}
