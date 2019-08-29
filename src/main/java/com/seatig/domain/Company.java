package com.seatig.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 企业信息表
 * </p>
 *
 * @author xianzw
 * @since 2019-03-04
 */
@Data
@TableName("t_company")
public class Company extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "name";
    /**
     * 名称
     */
    private String name;

    /**
     * logoid
     */
    private String logoId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 公司官网
     */
    private String website;
    /**
     * 服务行业
     */
    private String serviceIndustry;
    /**
     * 公司地址
     */
    private String corporateAddress;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 传真
     */
    private String fax;

    /**
     * 税号
     */
    private String taxid;

    /**
     * 法人识别码
     */
    private String lei;


}
