package org.smart4j.chapter1.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName CustomerCreateServlet
 * @Description 创建客户信息
 * @Author jeremy.lai
 * @Date 2021/11/29 4:06 下午
 * @Version 1.0
 **/
@WebServlet("/customer_create")
public class CustomerCreateServlet extends HttpServlet {

    /**
     * @return void
     * @MethodName doGet
     * @Description 进入创建客户界面
     * @Param [req, resp]
     * @Author jeremy.lai
     * @Date 4:11 下午 2021/11/29
     **/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: 2021/11/29  
    }

    /**
     * @return void
     * @MethodName doPost
     * @Description 处理创建客户请求
     * @Param [req, resp]
     * @Author jeremy.lai
     * @Date 4:12 下午 2021/11/29
     **/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: 2021/11/29  
    }
}
