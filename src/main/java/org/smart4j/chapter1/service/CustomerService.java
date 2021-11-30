package org.smart4j.chapter1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter1.helper.DatabaseHelper;
import org.smart4j.chapter1.model.Customer;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CustomerService
 * @Description 提供客户数据服务
 * @Author jeremy.lai
 * @Date 2021/11/29 4:14 下午
 * @Version 1.0
 **/
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);


    /**
     * @return java.util.List<org.smart4j.chapter1.model.Customer>
     * @MethodName getCustomerList
     * @Description 获取客户列表
     * @Param [keyword]
     * @Author jeremy.lai
     * @Date 4:17 下午 2021/11/29
     **/
    public List<Customer> getCustomerList(String keyword) {
        Connection conn = null;
        List<Customer> customerList;
        try {
            String sql = "select * from customer";
            conn = DatabaseHelper.getConnection();
            customerList = DatabaseHelper.queryEntityList(Customer.class, conn, sql);
        } finally {
            DatabaseHelper.closeConnection(conn);
        }
        return customerList;
    }

    /**
     * @return org.smart4j.chapter1.model.Customer
     * @MethodName getCustomer
     * @Description 获取客户
     * @Param [id]
     * @Author jeremy.lai
     * @Date 4:18 下午 2021/11/29
     **/
    public Customer getCustomer(long id) {
        // TODO: 2021/11/29
        return null;
    }

    /**
     * @return boolean
     * @MethodName createCustomer
     * @Description 创建客户
     * @Param [fieldMap]
     * @Author jeremy.lai
     * @Date 4:20 下午 2021/11/29
     **/
    public boolean createCustomer(Map<String, Object> fieldMap) {
        // TODO: 2021/11/29
        return false;
    }

    /**
     * @return boolean
     * @MethodName updateCustomer
     * @Description 更新客户信息
     * @Param [id, fieldMap]
     * @Author jeremy.lai
     * @Date 4:22 下午 2021/11/29
     **/
    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        // TODO: 2021/11/29
        return false;
    }

    /**
     * @return boolean
     * @MethodName deleteCustomer
     * @Description 删除客户
     * @Param [id]
     * @Author jeremy.lai
     * @Date 4:23 下午 2021/11/29
     **/
    public boolean deleteCustomer(long id) {
        // TODO: 2021/11/29
        return false;
    }


}
