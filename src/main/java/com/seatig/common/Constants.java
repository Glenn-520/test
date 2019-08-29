package com.seatig.common;

/**
 * 
 * @Description 
 * @author xianw
 * @time 2019年2月25日下午1:31:51
 * @version v1.0
 */
public class Constants {
	
    public static final String RESULT_CODE_SUCCESS = "200";  // 成功处理请求
    public static final String RESULT_CODE_BAD_REQUEST = "412";  // bad request
    public static final String RESULT_CODE_SERVER_ERROR = "500";  // 没有对应结果
    public static final String RESULT_CODE_LOGIN_SUCCESS = "600";  // 登录成功
    public static final String RESULT_CODE_LOGIN_ERROR = "602";  // 登录失败
    public static final String RESULT_CODE_NEED_LOGIN = "603";  // 需要登录
    public static final String RESULT_CODE_CORDA_LOGIN_CONFIG_ERROOR="701";//corda登陆失败(端口/用户名/密码 错误)
    public static final String RESULT_CODE_CORDA_LOGIN_ADDRESS_ERROR="702";//corda登陆失败(未启动/主机错误)



    public static final String YES = "1";
	public static final String NO = "0";
    
    
}
