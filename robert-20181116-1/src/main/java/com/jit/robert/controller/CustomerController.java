package com.jit.robert.controller;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.Customer;
import com.jit.robert.domain.Repair;
import com.jit.robert.dto.CustomerDTO;
import com.jit.robert.dto.RepairDTO;
import com.jit.robert.dto.TaskDTO;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "customer", description = "客户管理")
@ResponseResult
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 管理员注册普通用户
     * @param customer
     * @return
     */
    @ApiOperation(value = "普通用户注册",notes = "管理员注册普通用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "customer", value = "普通用户对象", required = true, dataType = "Customer")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Customer create(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    /**
     * 查看所有用户信息，分页展示
     * @return
     */
    @ApiOperation(value = "查看用户信息",notes = "查看所有用户信息，分页展示")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<Customer> getAllCustomers(PageQO pageQO){
        return customerService.getAllCustomers(pageQO);
    }

    /**
     * 根据省市地区或者注册时间筛选普通用户
     * @param queryStrategy
     * @param pageQO
     * @return
     */
    @ApiOperation(value = "筛选用户",notes = "根据省市地区或者注册时间筛选普通用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "queryStrategy", value = "筛选条件对象", required = true, dataType = "QueryStrategy")
    })
    @RequestMapping(value = "/strategy",method = RequestMethod.POST)
    public PageVO<Customer> getCustomersByStrategy(@RequestBody QueryStrategy queryStrategy, PageQO pageQO){
        return customerService.getCustomersByStrategy(queryStrategy, pageQO);
    }

    /**
     * 更新普通用户信息，可以是管理员也可以是用户自己
     * @return
     */
    @ApiOperation(value = "更新用户",notes = "更新普通用户信息，可以是管理员也可以是用户自己")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "普通用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "customer", value = "需更新的用户对象（可更新字段：realname, sex, type, tel, email, age, province, city, county, address）", required = true, dataType = "Customer")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Customer updateCustomer(@PathVariable Integer id, @RequestBody Customer customer){
        return customerService.updateCustomer(id, customer);
    }

    /**
     * 删除普通用户，仅管理员可删，可单个可批量
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除用户",notes = "删除普通用户，仅管理员可删，可单个可批量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "需要删除的普通用户的id（多个用“-”连接，如：1-3-4）", required = true, dataType = "String")
    })
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public Boolean deleteCustomer(@PathVariable("ids") String ids){
        return customerService.deleteCustomer(ids);
    }

    /**
     * 用户提交需要维修的机器人的信息
     * @param robert_id
     * @param description
     * @return
     */
    @ApiOperation(value = "用户填写维修信息",notes = "用户提交需要维修的机器人的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "robert_id", value = "需要维修的机器人id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "description", value = "问题描述", required = true, dataType = "String")
    })
    @RequestMapping(value = "/repair/{robert_id}", method = RequestMethod.POST)
    public Repair createRepair(@PathVariable Integer robert_id, @RequestParam String description){
        return customerService.createRepair(robert_id,description);
    }

    /**
     * 用户自己获取正在维修的机器人列表
     * @param status
     * @return
     */
    @ApiOperation(value = "用户获取维修列表",notes = "用户自己获取正在维修的机器人列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "机器人维修的状态（如不填则获取所有，若填写则根据状态获取）", dataType = "int")
    })
    @RequestMapping(value = "/repair/list",method = RequestMethod.GET)
    public List<RepairDTO> getMyRepairList(@RequestParam(value = "status",required = false)Integer status){
        return customerService.getMyRepairList(status);
    }

    @ApiOperation(value = "获取用户Id列表",notes = "获取用户Id列表")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/ids",method = RequestMethod.GET)
    public List<CustomerDTO> getUserIdList(){
        return customerService.getUserIdList();
    }
}
