package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.SeedStore;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface SeedStoreService {

    SeedStore create(MultipartFile image, HttpServletRequest request) throws IOException;

    Boolean deleteSeedStore(Integer id);

    SeedStore updateSeedStore(Integer id, MultipartFile image, HttpServletRequest request) throws IOException;

    SeedStore getSeedStoreInfo(Integer id);

    PageVO<SeedStore> getAllSeedStores(PageQO pageQO);

    PageVO<SeedStore> getSeedStoresBySubKind(String subKind,PageQO pageQO);


}
