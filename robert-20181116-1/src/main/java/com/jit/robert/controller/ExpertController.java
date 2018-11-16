package com.jit.robert.controller;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.Expert;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.ExpertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(value = "expert", description = "专家管理")
@ResponseResult
@RestController
@RequestMapping(value = "/expert")
public class ExpertController {

    @Autowired
    private ExpertService expertService;

    /**
     * 管理员录入专家信息
     * @return
     */
    @ApiOperation(value = "专家注册",notes = "管理员录入专家信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "expert", value = "专家对象", required = true, dataType = "Expert")
    })
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public Expert createExpert(@RequestBody Expert expert){
        return expertService.create(expert);
    }

    /**
     * 查看所有专家信息，分页展示
     * @return
     */
    @ApiOperation(value = "查看专家信息",notes = "查看所有专家信息，分页展示")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public PageVO<Expert> getAllExperts(PageQO pageQO){
        return expertService.getAllExperts(pageQO);
    }

    /**
     * 根据省市地区或者注册时间筛选专家
     * @param queryStrategy
     * @param pageQO
     * @return
     */
    @ApiOperation(value = "筛选专家",notes = "根据省市地区或者注册时间筛选专家")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "queryStrategy", value = "筛选条件对象", required = true, dataType = "QueryStrategy")
    })
    @RequestMapping(value = "/strategy",method = RequestMethod.POST)
    public PageVO<Expert> getExpertsByStrategy(@RequestBody QueryStrategy queryStrategy, PageQO pageQO){
        return expertService.getExpertsByStrategy(queryStrategy, pageQO);
    }

    /**
     * 更新专家信息，可以是管理员也可以是专家自己
     * 可更新字段 "product","major","degree","company","tel","email","age","address","remark"
     * @return
     */
    @ApiOperation(value = "更新专家",notes = "更新专家信息，可以是管理员也可以是专家自己")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "专家id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "expert", value = "需更新的专家对象（可更新字段：realname, product, major, degree, company, tel, email, age, province, city, county, address, remark）", required = true, dataType = "Expert")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Expert updateExpert(@PathVariable Integer id, @RequestBody Expert expert){
        return expertService.updateExpert(id, expert);
    }

    @ApiOperation(value = "上传专家头像",notes = "上传专家头像，可以是管理员也可以是专家自己")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "专家id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/image/{id}", method = RequestMethod.PUT)
    public Expert updateExpert(@PathVariable Integer id, MultipartFile image) throws IOException {
        return expertService.updateExpertImage(id, image);
    }

    /**
     * 删除专家，仅管理员可删，可单个可批量
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除专家",notes = "删除专家，仅管理员可删，可单个可批量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "需要删除的专家的id（多个用“-”连接，如：1-3-4）", required = true, dataType = "String")
    })
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public Boolean deleteExpert(@PathVariable("ids") String ids){
        return expertService.deleteExpert(ids);
    }

    @ApiOperation(value = "获取专家信息",notes = "获取专家信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Expert getCurrentUserInfo(@PathVariable Integer id){
        return expertService.getExpertInfo(id);
    }

}
