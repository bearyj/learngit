package com.jit.robert.serviceimpl;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.Expert;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ExpertServiceImplTest {
    @Autowired
    private ExpertServiceImpl expertService;

    @Test
    public void create() throws Exception {
        Expert expert = new Expert();
        expert.setUsername("expert");
        expert.setProduct("水产机器人");
        expert.setMajor("蟹");
        expert.setNumber("000002");
        expert.setDegree("硕士");
        expert.setCompany("研究院");
        expert.setTel("12345678988");
        expert.setEmail("123@163.com");
        expert.setAge(23);
        expert.setAddress("南京");
        expert.setRealname("黄三");
        Expert result = expertService.create(expert);
        Assert.assertNotNull(result);
    }

    @Test
    public void getAllExperts() throws Exception {
        PageQO pageQO = new PageQO(1,3);
        PageVO<Expert> expertList = expertService.getAllExperts(pageQO);
        log.info("list ={},size={},total={}",expertList.getList(),expertList.getList().size(),expertList.getTotal());
        Assert.assertNotEquals(0,expertList.getList().size());
    }

    @Test
    public void getExpertsByStrategy() throws Exception{
        QueryStrategy strategy = new QueryStrategy();
//        strategy.setAddress("南京");
//        strategy.setRegisterTime("");
        PageQO pageQO = new PageQO(1,3);
        PageVO<Expert> expertList = expertService.getExpertsByStrategy(strategy,pageQO);
        log.info("List = {}",expertList.getList());
        Assert.assertNotEquals(0,expertList.getList().size());
    }

    @Test
    public void updateExpert() throws Exception{
        Expert expert = new Expert();
        expert.setMajor("泥鳅");
        expert.setDegree("农业博士");
        expert.setCompany("农业研究所");
        expert.setTel("12345672288");
        expert.setEmail("1243@163.com");
        expert.setAge(28);
        expert.setAddress("甘肃");
        expert.setRemark("专家");
        Expert result = expertService.updateExpert(1,expert);
        Assert.assertNotNull(result);
    }

}