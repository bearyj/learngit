package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.common.QueryStrategy;
import com.jit.robert.domain.Expert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ExpertService {
    Expert create(Expert expert);

    PageVO<Expert> getAllExperts(PageQO pageQO);

    PageVO<Expert> getExpertsByStrategy(QueryStrategy queryStrategy, PageQO pageQO);

    Expert updateExpert(Integer id, Expert expert);

    Boolean deleteExpert(String ids);

    Expert updateExpertImage(Integer id, MultipartFile image) throws IOException;

    Expert getExpertInfo(Integer id);

}
