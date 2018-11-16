package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.FeedStore;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface FeedStoreService {

    FeedStore create(MultipartFile image, HttpServletRequest request) throws IOException;

    Boolean deleteFeedStore(Integer id);

    FeedStore updateFeedStore(Integer id, MultipartFile image, HttpServletRequest request) throws IOException;

    FeedStore getFeedStoreInfo(Integer id);

    PageVO<FeedStore> getAllFeedStores(PageQO pageQO);

    PageVO<FeedStore> getFeedStoresBySubKind(String subKind, PageQO pageQO);


}
