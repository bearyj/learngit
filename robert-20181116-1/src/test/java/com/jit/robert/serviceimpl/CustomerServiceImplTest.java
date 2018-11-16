package com.jit.robert.serviceimpl;

import com.jit.robert.domain.Customer;
import com.jit.robert.domain.Repair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

    @Autowired
    private CustomerServiceImpl customerService;

    @Test
    public void create() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("mmg");
        customer.setType("虾");
        customer.setTel("12345671098");
        customer.setEmail("mmmg@163.com");
        customer.setRealname("卫翠");
        customer.setSex(1);
        customer.setAge(43);
        customer.setAddress("宣城");
        Customer result = customerService.create(customer);
        Assert.assertNotNull(result);
    }

}