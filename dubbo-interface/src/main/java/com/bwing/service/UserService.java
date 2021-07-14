package com.bwing.service;

import com.bwing.bean.UserAddress;

import java.util.List;

public interface UserService {

    public List<UserAddress> getUserAddressList(int userId);
}
