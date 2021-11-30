package org.smart4j.chapter1.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter1.model.Customer;
import org.smart4j.chapter1.service.CustomerService;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName CustomerServiceTest
 * @Description TODO
 * @Author jeremy.lai
 * @Date 2021/11/29 4:26 下午
 * @Version 1.0
 **/
public class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest() {
        this.customerService = new CustomerService();
    }

    @Before
    public void init() {
        // TODO: 2021/11/29 初始化数据库
    }

    @Test
    public void getCustomerListTest() {
        List<Customer> customerList = customerService.getCustomerList("");
        Assert.assertEquals(2, customerList.size());
    }

    @Test
    public void getCustomerTest() {
        long id = 1;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerTest() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", "customer100");
        hashMap.put("contact", "John");
        hashMap.put("telephone", "13566489898");
        boolean result = customerService.createCustomer(hashMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomerTest() {
        long id = 1;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("remark", "我改备注了哈");
        boolean result = customerService.updateCustomer(id, hashMap);
        Assert.assertTrue(result);
    }

    @Test
    public void deleteCustomerTest() {
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }

}
