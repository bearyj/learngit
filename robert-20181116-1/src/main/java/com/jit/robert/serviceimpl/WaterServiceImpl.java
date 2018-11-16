package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.jit.robert.common.ImageUtils;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Water;
import com.jit.robert.dto.DiaryDTO;
import com.jit.robert.mapper.FeedMapper;
import com.jit.robert.mapper.WaterMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class WaterServiceImpl implements WaterService {

    @Autowired
    private WaterMapper waterMapper;

    @Value("${image.product.path}")
    private String product_path;
    @Value("${image.product.url}")
    private String image_url;

    @Override
    public Water insert(MultipartFile[] image, HttpServletRequest request) throws IOException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Water water = new Water();
        water.setPh(request.getParameter("ph"));
        water.setUsername(username);
        water.setAlkali(request.getParameter("alklai"));
        water.setMedicine(request.getParameter("medicine"));
        water.setNano2(request.getParameter("nano2"));
        water.setNH(request.getParameter("nh"));
        water.setWeather(request.getParameter("weather"));
        water.setTemperature(request.getParameter("temperature"));
        water.setRemark(request.getParameter("remark"));
        water.setPound_id(Integer.parseInt(request.getParameter("pound_id")));
        water.setO2(request.getParameter("o2"));

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = request.getParameter("date");
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        water.setDate(date);


        List<String> images = new ArrayList<>();
        if (image != null){
            for (MultipartFile mFile : image){
                String fileName = ImageUtils.ImgReceive(mFile,product_path);
                images.add(image_url+ fileName);
            }
            String imageUrl = images.toString();
            water.setImage(imageUrl.substring(1,imageUrl.length()-1));
        }


        Water isExist = waterMapper.findByDate(strDate);
        int flag = 0;
        if (isExist == null){
            water.setDate(date);
            water.setUsername(username);
            flag = waterMapper.insert(water);
        }else {
            Integer id = isExist.getId();
            water.setId(id);
            water.setDate(date);
            water.setUsername(username);
            flag = waterMapper.update(water);
        }

        if(flag>0){
            return water;
        }else{
            throw new BusinessException(ResultCode.DATA_IS_WRONG);
        }
    }

    @Override
    public Boolean delete(Integer id) {
       int i = waterMapper.delete(id);
       if(i>0){
           return true;
       }else {
           throw new BusinessException(ResultCode.DATA_IS_WRONG);
       }
    }

    @Override
    public Water update(Water water) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Water currentPound = waterMapper.findByIdAndUsername(water.getId(),username);
        if (currentPound == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        Date date = water.getDate();
        water.setDate(date);
        water.setUsername(username);
      int i = waterMapper.update(water);
      if(i>0){
          return water;
      }else {
          throw new BusinessException(ResultCode.DATA_IS_WRONG);
      }
    }


    @Override
    public PageVO<Water> getWaterByPound(Integer pound_id, PageQO pageQO) {
        Page<Water> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Water> waters = waterMapper.getWaters(pound_id);
        if (waters != null){
            return  PageVO.build(page);
        }else {
            throw new BusinessException(ResultCode.DATA_IS_WRONG);
        }
    }

    @Override
    public List<DiaryDTO> getDiaryByPound(Integer pound_id, String start_date, String end_date) {
        List<DiaryDTO> diaryDTOList = waterMapper.getDiaryByPound(pound_id,start_date,end_date);
        return diaryDTOList;
    }
}
