package org.smart4j.chapter1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Customer
 * @Description 客户类
 * @Author jeremy.lai
 * @Date 2021/11/29 3:55 下午
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    //ID 自增长
    private long id;

    //客户名称
    private String name;

    //联系人
    private String contact;

    //电话号码
    private String telephone;

    //邮箱地址
    private String email;

    //备注
    private String remark;

}
