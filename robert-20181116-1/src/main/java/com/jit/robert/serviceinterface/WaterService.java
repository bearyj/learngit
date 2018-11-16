package com.jit.robert.serviceinterface;


import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Water;
import com.jit.robert.dto.DiaryDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public interface WaterService {
    Water insert(MultipartFile[] image, HttpServletRequest request) throws IOException;
    Boolean delete(Integer id);
    Water update(Water water);
    PageVO<Water> getWaterByPound(Integer pound_id, PageQO pageQO);
    List<DiaryDTO> getDiaryByPound(Integer pound_id,String start_date,String end_date);
   }
