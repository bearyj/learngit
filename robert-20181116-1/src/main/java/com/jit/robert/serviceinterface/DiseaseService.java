package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Disease;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface DiseaseService {
    Disease insertDisease(MultipartFile image,HttpServletRequest request) throws IOException;
    Boolean deleteDisease(Integer id);
    Disease updateDisease(MultipartFile image,HttpServletRequest request, Integer id)throws IOException;
    PageVO<Disease> getAllDiseases(PageQO pageQO);
    PageVO<Disease> getDiseasesBySubkind(String subKind,PageQO pageQO);
    Disease getDiseaseInfo(Integer id);


}
