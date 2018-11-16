package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.Customer;
import com.jit.robert.domain.Repair;
import com.jit.robert.dto.CustomerDTO;
import com.jit.robert.dto.RepairDTO;
import com.jit.robert.dto.TaskDTO;

import java.util.List;

public interface CustomerService {
    Customer create(Customer customer);

    PageVO<Customer> getAllCustomers(PageQO pageQO);

    PageVO<Customer> getCustomersByStrategy(QueryStrategy strategy, PageQO pageQO);

    Customer updateCustomer(Integer id, Customer customer);

    Boolean deleteCustomer(String ids);

    Repair createRepair(Integer robert_id, String description);

    List<RepairDTO> getMyRepairList(Integer status);

    List<CustomerDTO> getUserIdList();
}
