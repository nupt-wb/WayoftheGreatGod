package com.bwing.service.impl;

import com.bwing.bean.UserAddress;
import com.bwing.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public List<UserAddress> getUserAddressList(int userId) {

        List<UserAddress> addresses = new ArrayList<>();
        addresses.add(new UserAddress(1,"上海市"));
        addresses.add(new UserAddress(2,"北京市"));
        return addresses;
    }
}
