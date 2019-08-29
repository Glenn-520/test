package com.seatig.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果
 * @Description 
 * @author xianw
 * @time 2019年2月25日下午1:33:21
 * @version v1.0
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String resultCode;
    private String message;
    private T data;

}
