package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.ImageUtils;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.Expert;
import com.jit.robert.domain.User;
import com.jit.robert.enums.UserTypeEnum;
import com.jit.robert.mapper.ExpertMapper;
import com.jit.robert.mapper.UserMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.ExpertService;
import com.jit.robert.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExpertServiceImpl implements ExpertService {

    @Value("${image.user.path}")
    private String user_path;

    @Value("${image.user.url}")
    private String image_url;

    @Value("${image.url}")
    private String url;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ExpertMapper expertMapper;

    @Override
    @Transactional
    public Expert create(Expert expert) {
        if (userMapper.findByUsername(expert.getUsername()) != null){
            throw  new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }
        //1、注册专家
        User user = UserUtil.RegisterUser(expert.getUsername());
        userMapper.insert(user);
        //2、设置角色
        User currentUser = userMapper.findByUsername(expert.getUsername());
        userMapper.insertUserRole(currentUser.getId(),5);
        //3、插入专家列表
        expert.setSign_time(new Date());
        expertMapper.insert(expert);
        Expert returnExpert = expertMapper.getByUsername(expert.getUsername());
        changeImage(returnExpert);
        return returnExpert;
    }

    @Override
    public PageVO<Expert> getAllExperts(PageQO pageQO) {
        Page<Expert> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Expert> expertList = expertMapper.getAllExperts();
        changeImages(expertList);
        return PageVO.build(page);
    }

    @Override
    public PageVO<Expert> getExpertsByStrategy(QueryStrategy queryStrategy, PageQO pageQO) {
        Page<Expert> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Expert> expertList = expertMapper.getExpertsByStrategy(UserTypeEnum.EXPERT.getCode(),queryStrategy);
        changeImages(expertList);
        return PageVO.build(page);
    }

    @Override
    public Expert updateExpert(Integer id, Expert expert) {
        Expert currentExpert = expertMapper.getExpertById(id);
        if (currentExpert == null)
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        currentExpert.setProduct(expert.getProduct());
        currentExpert.setMajor(expert.getMajor());
        currentExpert.setDegree(expert.getDegree());
        currentExpert.setCompany(expert.getCompany());
        currentExpert.setRemark(expert.getRemark());
        currentExpert.setTel(expert.getTel());
        currentExpert.setEmail(expert.getEmail());
        currentExpert.setAge(expert.getAge());
        currentExpert.setProvince(expert.getProvince());
        currentExpert.setCity(expert.getCity());
        currentExpert.setCounty(expert.getCounty());
        currentExpert.setAddress(expert.getAddress());
        currentExpert.setRealname(expert.getRealname());
        expertMapper.updateExpert(currentExpert);
        Expert returnExpert = expertMapper.getExpertById(id);
        changeImage(returnExpert);
        return returnExpert;
    }

    @Override
    public Boolean deleteExpert(String ids) {
        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());
//            log.info("del_ids = {}",del_ids);
            String delIds = del_ids.toString();
            List<Integer> userIds = expertMapper.getUserIds(UserTypeEnum.EXPERT.getCode(),delIds.substring(1,delIds.length()-1));
            String del_userIds = userIds.toString();
            userMapper.deleteUserBatch(del_userIds.substring(1,del_userIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            Integer user_id = expertMapper.getUserId(id);
            userMapper.deleteUser(user_id);
        }
        return true;
    }

    @Override
    public Expert updateExpertImage(Integer id, MultipartFile image) throws IOException {
        Expert currentExpert = expertMapper.getExpertById(id);
        if (currentExpert == null)
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);

        if (image!=null){
            String fileName = ImageUtils.ImgReceive(image,user_path);
            userMapper.updateUserImage(currentExpert.getUsername(),image_url+fileName);
        }
        Expert expert = expertMapper.getExpertById(id);
        changeImage(expert);
        return expert;


    }

    private void changeImages(List<Expert> list){
        for (int i=0;i<list.size();i++){
            changeImage(list.get(i));
        }
    }

    private void changeImage(Expert obj){
        if (obj.getImage()!=null){
            obj.setImage(url+obj.getImage());
        }
    }

    @Override
    public Expert getExpertInfo(Integer id) {
        Expert expert = expertMapper.getExpertById(id);
        changeImage(expert);
        return expert;
    }
}
