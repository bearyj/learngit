package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.ImageUtils;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.FeedStore;
import com.jit.robert.mapper.FeedStoreMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.FeedStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FeedStoreServiceImpl implements FeedStoreService {

    @Value("${image.feedStore.path}")
    private String feedStore_path ;

    @Value("${image.feedStore.url}")
    private String image_url;


    @Value("${image.url}")
    private String url;

    @Autowired
    private FeedStoreMapper feedStoreMapper;

    @Override
    public FeedStore create(MultipartFile image, HttpServletRequest request) throws IOException {
        FeedStore feedStore = new FeedStore();
        feedStore.setName(request.getParameter("name"));
        feedStore.setKind(2);
        feedStore.setSubKind(request.getParameter("subKind"));
        feedStore.setType(request.getParameter("type"));
        feedStore.setPrice(request.getParameter("price"));
        feedStore.setManualInstruct(request.getParameter("manualInstruct"));
        feedStore.setCompany(request.getParameter("company"));
        feedStore.setContact(request.getParameter("contact"));
        feedStore.setTelPhone(request.getParameter("telPhone"));
        feedStore.setVisitCount(0);
        feedStore.setPublishTime(new Date());
        String fileName = ImageUtils.ImgReceive(image,feedStore_path);
        feedStore.setImage(image_url+fileName);
        int flag = feedStoreMapper.insert(feedStore);
        if (flag>0){
            FeedStore feedStore1 = feedStoreMapper.getFeedStoreById(feedStore.getId());
            changeImage(feedStore1);
            return feedStore1;
        }else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    @Override
    public Boolean deleteFeedStore(Integer id) {
        int flag = feedStoreMapper.delete(id);
        if (flag>0){
            return true;
        }else {
            throw new BusinessException(ResultCode.DATABASE_DELETE_ERROR);
        }
    }

    @Override
    public FeedStore updateFeedStore(Integer id, MultipartFile image, HttpServletRequest request) throws IOException {
        FeedStore feedStore = feedStoreMapper.getFeedStoreById(id);
        if (feedStore == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        feedStore.setName(request.getParameter("name"));
        feedStore.setKind(2);
        feedStore.setSubKind(request.getParameter("subKind"));
        feedStore.setType(request.getParameter("type"));
        feedStore.setPrice(request.getParameter("price"));
        feedStore.setManualInstruct(request.getParameter("manualInstruct"));
        feedStore.setCompany(request.getParameter("company"));
        feedStore.setContact(request.getParameter("contact"));
        feedStore.setTelPhone(request.getParameter("telPhone"));
        feedStore.setVisitCount(0);
        feedStore.setPublishTime(new Date());
        String fileName = ImageUtils.ImgReceive(image,feedStore_path);
        feedStore.setImage(image_url+fileName);
        int flag = feedStoreMapper.update(id,feedStore);
        if (flag>0){
            FeedStore feedStore1 = feedStoreMapper.getFeedStoreById(id);
            changeImage(feedStore1);
            return feedStore1;
        }else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }
    }

    @Override
    public FeedStore getFeedStoreInfo(Integer id) {
        FeedStore feedStore = feedStoreMapper.getFeedStoreById(id);
        if (feedStore == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        feedStoreMapper.updateVisitCount(feedStore.getVisitCount() + 1,id);
        changeImage(feedStore);
        return feedStore;
    }

    @Override
    public PageVO<FeedStore> getAllFeedStores(PageQO pageQO) {
        Page<FeedStore> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<FeedStore> feedStores = feedStoreMapper.getAllFeedStores();
        changeImages(feedStores);
        return PageVO.build(page);
    }

    @Override
    public PageVO<FeedStore> getFeedStoresBySubKind(String subKind, PageQO pageQO) {
        if (subKind.equals("所有")){
            Page<FeedStore> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            List<FeedStore> feedStores = feedStoreMapper.getAllFeedStores();
            changeImages(feedStores);
            return PageVO.build(page);
        } else {
            Page<FeedStore> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            List<FeedStore> feedStores = feedStoreMapper.getFeedStoresBySubKind(subKind);
            changeImages(feedStores);
            return PageVO.build(page);
        }
    }

    private void changeImages(List<FeedStore> list){
        for (int i=0;i<list.size();i++){
            changeImage(list.get(i));
        }
    }

    private void changeImage(FeedStore obj){
        if (obj.getImage()!=null){
            obj.setImage(url+obj.getImage());
        }
    }
}
