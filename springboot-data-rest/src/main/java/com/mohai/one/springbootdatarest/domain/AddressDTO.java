package com.mohai.one.springbootdatarest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/22 01:07
 */
@Entity
public class AddressDTO {

    @GeneratedValue
    @Id
    private Long id;
    private final String street, zipCode, city, state;

    public AddressDTO(String street, String zipCode, String city, String state) {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
    }

    public String toString() {
        return String.format("%s, %s, %s, %s", street, zipCode, city, state);
    }

}
