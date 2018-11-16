package com.jit.robert.controller;

import com.jit.robert.common.ImageUtils;
import com.jit.robert.domain.User;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.mapper.UserMapper;
import com.jit.robert.serviceinterface.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Api(value = "user", description = "用户管理")
@ResponseResult
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    @Value("${image.user.path}")
    private String pic_path;
    @Value("${image.user.url}")
    private String image_url;

    @Value("${image.url}")
    private String url;

    /**
     * 上传头像
     * @param file
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "上传头像",notes = "上传头像")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/image",method = RequestMethod.POST)
    public ResponseEntity<Map<String,String>> updateUserImage( MultipartFile file) throws Exception{

//        String pic_path = "D:\\tmp\\";
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String fileName = ImageUtils.ImgReceive(file,pic_path);
        if (fileName != null){
            String image = userService.getUserImage(username);
            if (image != null){
                String imageArray[] = image.split("/");
                String oldFileName = imageArray[imageArray.length-1];
                System.out.println("oldFileName " + oldFileName);
                File oldFile = new File(pic_path + oldFileName);
                System.out.println("oldFile " + oldFile);
                if (oldFile.exists()){
                    oldFile.delete();
                }
            }
            userService.updateUserImage(username,image_url+fileName);
            Map<String,String> imageUrl = new HashMap<>();
            imageUrl.put("image",url+image_url+fileName);
            return new ResponseEntity<>(imageUrl, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @ApiOperation(value = "修改密码",notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/password",method = RequestMethod.PUT)
    public Boolean updatePassword(@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword){
        return userService.updatePassword(oldPassword, newPassword);
    }

    @ApiOperation(value = "获取超级管理员信息",notes = "获取超级管理员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User getCurrentUserInfo(@PathVariable Integer id){
        return userService.getUserById(id);
    }

}
