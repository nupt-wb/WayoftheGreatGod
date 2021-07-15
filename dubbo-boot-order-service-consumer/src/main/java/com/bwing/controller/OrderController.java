package com.bwing.controller;

import com.bwing.bean.UserAddress;
import com.bwing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/initOrder")
    @ResponseBody
    public List<UserAddress> initOrder(@RequestParam("id") int userId){
        return orderService.initOrder(userId);
    }
}
