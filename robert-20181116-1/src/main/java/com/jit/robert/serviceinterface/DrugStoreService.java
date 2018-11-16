package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.DrugStore;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface DrugStoreService {
    DrugStore insertDrugStore(MultipartFile image, HttpServletRequest request) throws IOException;
    Boolean deleteDrugStore(Integer id);
    DrugStore updateDrugStore(MultipartFile image, HttpServletRequest request, Integer id) throws IOException;
    PageVO<DrugStore> getAllDrugStores(PageQO pageQO);
    PageVO<DrugStore> getDrugStoreBySubkind(String subKind,PageQO pageQO);
    DrugStore getDrugStoreInfo(Integer id);

}
