package com.seatig.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体超类
 * @Description 
 * @author xianw
 * @time 2019年2月25日下午2:05:17
 * @version v1.0
 */
@Data
public abstract class BaseEntity implements Serializable {
	
	public static final String ID = "id";
	public static final String CREATEBY = "createby";
	public static final String CREATE_COMPANY_ID = "create_company_id";
	public static final String CREATE_COMPANY_NAME = "create_company_name";
	public static final String CREATE_DATE = "create_date";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4560331540586236532L;

	@TableId(type=IdType.UUID)
	private String id;//id
	
	@JsonFormat(pattern="yyyy.MM.dd",timezone = "GMT+8")
	private Date createDate;//新增时间
	
	private String createby;//创建人
	
	private String createCompanyId;//创建企业Id
	
	private String createCompanyName;//创建企业名称
	
	private String createUserCompanyId;//个人为userId，企业为companyId
	
	private String createUserCompanyName;//个人为userName，企业为companyName

	private String delFlag = "0";//0:有效 1:无效
}
