package com.bwing.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserAddress implements Serializable {

    private int addressId;

    private String addressName;
}
