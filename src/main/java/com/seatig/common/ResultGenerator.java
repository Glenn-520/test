package com.seatig.common;

import com.seatig.utils.ObjectUtil;

/**
 * 响应结果生成工具
 * @Description 
 * @author xianw
 * @time 2019年2月25日下午1:32:22
 * @version v1.0
 */
public class ResultGenerator {
	
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_ERROR_MESSAGE = "ERROR";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
    
    private static final String NULL = "{}";
    
    /**
     * 返回成功消息
     * @Description:   
     * @param: @param code
     * @param: @param data
     * @param: @return      
     * @return: Result      
     * @throws
     */
    public static Result getSuccessResult(String code,Object data){
    	Result result = new Result();
    	result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setResultCode(code);
        result.setData(data);
        return result;
    }
    
    /**
     * 返回失败消息
     * @Description:   
     * @param: @param code
     * @param: @param data
     * @param: @return      
     * @return: Result      
     * @throws
     */
    public static Result getErrorResult(String code,Object data){
    	Result result = new Result();
    	result.setMessage(DEFAULT_ERROR_MESSAGE);
    	result.setResultCode(code);
    	result.setData(data);
    	return result;
    }

    /**
     * 反复成功消息
     * @Description:   
     * @param: @return      
     * @return: Result      
     * @throws
     */
    public static Result getSuccessResult() {
        Result result = new Result();
        result.setResultCode(Constants.RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(NULL);
        return result;
    }

    /**
     * 返回成功消息并添加数据
     * @Description:   
     * @param: @param data
     * @param: @return      
     * @return: Result      
     * @throws
     */
    public static Result getSuccessResult(Object data) {
        Result result = new Result();
        result.setResultCode(Constants.RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        if(ObjectUtil.isEmpty(data)){
        	result.setData(NULL);
        }else{
        	result.setData(data);
        }
        return result;
    }


    
    /**
     * 返回验证error消息
     * @Description:   
     * @param: @param code
     * @param: @param defaultMessage
     * @param: @return      
     * @return: Result      
     * @throws
     */
	public static Result getValidResult(String code, String defaultMessage) {
		Result result = new Result();
		result.setResultCode(code);
		result.setMessage(defaultMessage);
		result.setData(NULL);
		return result;
	}

}
