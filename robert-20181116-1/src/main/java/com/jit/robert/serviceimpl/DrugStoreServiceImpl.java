package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.ImageUtils;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Disease;
import com.jit.robert.domain.DrugStore;
import com.jit.robert.mapper.DrugStoreMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.DrugStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class DrugStoreServiceImpl implements DrugStoreService {

    @Value("${image.user.path}")
    private String drugStore_path;

    @Value("${image.user.url}")
    private String image_url;

    @Value("${image.url}")
    private String url;

    @Autowired
    private DrugStoreMapper drugStoreMapper;
    @Override
    public DrugStore insertDrugStore(MultipartFile image, HttpServletRequest request) throws IOException {
        DrugStore drugStore = new DrugStore();
        drugStore.setCompany(request.getParameter("company"));
        drugStore.setContact(request.getParameter("contact"));
        drugStore.setKind(Integer.parseInt(request.getParameter("kind")==null? "0":request.getParameter("kind")));
        drugStore.setManualInstruct(request.getParameter("manualInstruct"));
        drugStore.setName(request.getParameter("name"));
        drugStore.setPrice(request.getParameter("price"));
        drugStore.setSubKind(request.getParameter("subKind"));
        drugStore.setTelPhone(request.getParameter("telPhone"));
        drugStore.setType(request.getParameter("type"));
        drugStore.setVisitCount(0);
        drugStore.setPublishTime(new Date());
        if (image!=null){
            String fileName = ImageUtils.ImgReceive(image,drugStore_path);
            drugStore.setImage(image_url+fileName);
        }
        int flag = drugStoreMapper.insert(drugStore);
        if (flag>0){
            changeImage(drugStore);
            return drugStore;
        }else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    @Override
    public Boolean deleteDrugStore(Integer id) {
        int flag = drugStoreMapper.delete(id);
        if (flag>0){
            return true;
        }else {
            throw new BusinessException(ResultCode.DATABASE_DELETE_ERROR);
        }
    }

    @Override
    public DrugStore updateDrugStore(MultipartFile image, HttpServletRequest request, Integer id) throws IOException {

        DrugStore drugStore = drugStoreMapper.getOneDrug(id);
        if (drugStore == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        drugStore.setCompany(request.getParameter("company"));
        drugStore.setContact(request.getParameter("contact"));
        drugStore.setKind(Integer.parseInt(request.getParameter("kind")==null? "0":request.getParameter("kind")));
        drugStore.setManualInstruct(request.getParameter("manualInstruct"));
        drugStore.setName(request.getParameter("name"));
        drugStore.setPrice(request.getParameter("price"));
        drugStore.setSubKind(request.getParameter("subKind"));
        drugStore.setTelPhone(request.getParameter("telPhone"));
        drugStore.setType(request.getParameter("type"));
        if (image!=null){
            String fileName = ImageUtils.ImgReceive(image,drugStore_path);
            drugStore.setImage(image_url+fileName);
        }
        drugStore.setId(id);
        int flag = drugStoreMapper.update(drugStore);
        if (flag>0){
            DrugStore drugStore1 = drugStoreMapper.getOneDrug(id);
            changeImage(drugStore1);
           return drugStore1;
        }else {
           throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }
    }

    @Override
    public PageVO<DrugStore> getAllDrugStores(PageQO pageQO) {
        Page<DrugStore> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<DrugStore> drugStoreList = drugStoreMapper.getAllDrugs();
        changeImages(drugStoreList);
        return PageVO.build(page);
    }

    @Override
    public PageVO<DrugStore> getDrugStoreBySubkind(String subKind,PageQO pageQO) {
        if (subKind.equals("所有")){
            Page<DrugStore> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            List<DrugStore> drugStoreList = drugStoreMapper.getAllDrugs();
            changeImages(drugStoreList);
            return PageVO.build(page);
        }else {
            Page<DrugStore> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            List<DrugStore> drugStoreList = drugStoreMapper.getAllDrugsBysubkind(subKind);
            changeImages(drugStoreList);
            return PageVO.build(page);
        }

    }

    @Override
    public DrugStore getDrugStoreInfo(Integer id) {
        DrugStore drugStore = drugStoreMapper.getOneDrug(id);
        if (drugStore == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        if (drugStore.getVisitCount() == null){
            drugStoreMapper.updateVisitCount( 1,id);
        }else {
            drugStoreMapper.updateVisitCount(drugStore.getVisitCount() + 1,id);
        }
        changeImage(drugStore);
        return drugStore;
    }


    private void changeImages(List<DrugStore> list){
        for (int i=0;i<list.size();i++){
            changeImage(list.get(i));
        }
    }

    private void changeImage(DrugStore obj){
        if (obj.getImage()!=null){
            obj.setImage(url+obj.getImage());
        }
    }

}
