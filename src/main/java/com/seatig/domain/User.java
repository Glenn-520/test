package com.seatig.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author xianzw
 * @since 2019-02-26
 */
@Data
@TableName("t_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String SUPERADMIN = "superAdmin";

    /**
     * 名称
     */
    @Size(max=30,message="{user.name.Size}")
    private String name;

    /**
     * 用户名
     */
    @Size(max=30,message="{user.username.Size}")
    private String username;

    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 区号
     */
    private String area;

    /**
     * 手机号码
     */
    private String phone;
    
    /**
     * 区号+手机号码
     */
    private String areaPhone;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 密码
     */
    private String password;

    /**
     * Q链密码
     */
    private String qxPassword;

    /**
     * 是否锁定
     */
    private Boolean locked = false;

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * logoId
     */
    private String logoId;


    /**
     * 激活状态
     */
    private Integer activation = 0;
    
    /**
     * 验证码
     */
    @TableField(exist = false)
    private String code;
}
