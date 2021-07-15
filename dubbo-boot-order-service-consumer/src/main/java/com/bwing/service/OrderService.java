package com.bwing.service;

import com.bwing.bean.UserAddress;

import java.util.List;

public interface OrderService {

    List<UserAddress> initOrder(int userId);
}
