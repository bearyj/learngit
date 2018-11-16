package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.ImageUtils;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.FeedStore;
import com.jit.robert.domain.SeedStore;
import com.jit.robert.mapper.SeedStoreMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.SeedStoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class SeedStoreServiceImpl implements SeedStoreService {


    @Value("${image.seedStore.path}")
    private String seedStore_path;

    @Value("${image.seedStore.url}")
    private String image_url;

    @Value("${image.url}")
    private String url;

    @Autowired
    private SeedStoreMapper seedStoreMapper;

    @Override
    public SeedStore create(MultipartFile image, HttpServletRequest request) throws IOException {
        SeedStore seedStore = new SeedStore();
        seedStore.setTitle(request.getParameter("title"));
        seedStore.setKind(2);
        seedStore.setSubKind(request.getParameter("subKind"));
        seedStore.setPrice(request.getParameter("price"));
        seedStore.setDescription(request.getParameter("description"));
        seedStore.setCompany(request.getParameter("company"));
        seedStore.setProductPlace(request.getParameter("productPlace"));
        seedStore.setContact(request.getParameter("contact"));
        seedStore.setTelPhone(request.getParameter("telPhone"));
        String fileName = ImageUtils.ImgReceive(image,seedStore_path);
        seedStore.setImage(image_url+fileName);
        seedStore.setVisitCount(0);
        seedStore.setPublishTime(new Date());
        int flag = seedStoreMapper.insert(seedStore);
        if (flag>0){
            SeedStore seedStore1 = seedStoreMapper.getSeedStoreById(seedStore.getId());
            changeImage(seedStore1);
            return seedStore1;
        }else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    @Override
    public Boolean deleteSeedStore(Integer id) {
        int flag = seedStoreMapper.delete(id);
        if (flag>0){
            return true;
        }else {
            throw new BusinessException(ResultCode.DATABASE_DELETE_ERROR);
        }
    }

    @Override
    public SeedStore updateSeedStore(Integer id, MultipartFile image, HttpServletRequest request) throws IOException {
        SeedStore seedStore = seedStoreMapper.getSeedStoreById(id);
        if (seedStore == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        seedStore.setTitle(request.getParameter("title"));
        seedStore.setKind(2);
        seedStore.setSubKind(request.getParameter("subKind"));
        seedStore.setPrice(request.getParameter("price"));
        seedStore.setDescription(request.getParameter("description"));
        seedStore.setCompany(request.getParameter("company"));
        seedStore.setProductPlace(request.getParameter("productPlace"));
        seedStore.setContact(request.getParameter("contact"));
        seedStore.setTelPhone(request.getParameter("telPhone"));
        String fileName = ImageUtils.ImgReceive(image,seedStore_path);
        seedStore.setImage(image_url+fileName);
        seedStore.setVisitCount(0);
        seedStore.setPublishTime(new Date());
        int flag = seedStoreMapper.update(seedStore);
        if (flag>0){
            SeedStore seedStore1 =  seedStoreMapper.getSeedStoreById(id);
            changeImage(seedStore1);
            return seedStore1;
        }else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }
    }

    @Override
    public SeedStore getSeedStoreInfo(Integer id) {
        SeedStore seedStore = seedStoreMapper.getSeedStoreById(id);
        if (seedStore == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        seedStoreMapper.updateVisitCount(seedStore.getVisitCount() + 1,id);
        changeImage(seedStore);
        return seedStore;
    }

    @Override
    public PageVO<SeedStore> getAllSeedStores(PageQO pageQO) {
        Page<SeedStore> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<SeedStore> seedStores = seedStoreMapper.getAllSeedStores();
        changeImages(seedStores);
        return PageVO.build(page);
    }

    @Override
    public PageVO<SeedStore> getSeedStoresBySubKind(String subKind, PageQO pageQO) {
        if (subKind.equals("所有")){
            Page<SeedStore> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            List<SeedStore> seedStores = seedStoreMapper.getAllSeedStores();
            changeImages(seedStores);
            return PageVO.build(page);
        }else {
            Page<SeedStore> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            List<SeedStore> seedStores = seedStoreMapper.getSeedStoresBySubKind(subKind);
            changeImages(seedStores);
            return PageVO.build(page);
        }
    }

    private void changeImages(List<SeedStore> list){
        for (int i=0;i<list.size();i++){
            changeImage(list.get(i));
        }
    }

    private void changeImage(SeedStore obj){
        if (obj.getImage()!=null){
            obj.setImage(url+obj.getImage());
        }
    }
}
