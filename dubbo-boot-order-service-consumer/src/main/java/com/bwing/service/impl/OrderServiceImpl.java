package com.bwing.service.impl;

import com.bwing.bean.UserAddress;
import com.bwing.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bwing.service.OrderService;

import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Reference
    private UserService userService;


    @Override
    public List<UserAddress> initOrder(int userId) {
        List<UserAddress> userAddressList = userService.getUserAddressList(userId);
        return userAddressList;
    }
}
