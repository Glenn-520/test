package com.seatig.common;

import lombok.Data;

/**
 * 
 * @description 自定义异常信息
 * @author xianzw
 * @time 2019年2月27日下午4:20:06
 */
@Data
public class BusinessException extends Exception{

	private static final long serialVersionUID = 1L;

	private String exceptionCode;//异常码
	
	private String exceptionMessage;//异常信息
	
	
	public BusinessException(String exceptionCode) {
		super();
		this.exceptionCode = exceptionCode;
	}
	
	public BusinessException(String exceptionCode, String exceptionMessage){
		super();
		this.exceptionCode = exceptionCode;
		this.exceptionMessage = exceptionMessage;
	}
}
