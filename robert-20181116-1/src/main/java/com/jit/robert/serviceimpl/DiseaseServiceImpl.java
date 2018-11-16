package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.ImageUtils;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Disease;
import com.jit.robert.mapper.DiseaseMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class DiseaseServiceImpl implements DiseaseService{
    @Value("${image.diseaseStore.path}")
    private String diseaseStore_path;

    @Value("${image.diseaseStore.url}")
    private String image_url;

    @Value("${image.url}")
    private String url;

    @Autowired
    private DiseaseMapper diseaseMapper;
    @Override
    public Disease insertDisease(MultipartFile image,HttpServletRequest request) throws IOException {
        Disease disease = new Disease();
        disease.setCause(request.getParameter("cause"));
        disease.setDiseaseName(request.getParameter("diseaseName"));
        disease.setSubKind(request.getParameter("subKind"));
        disease.setSymptom(request.getParameter("symptom"));
        disease.setTreatment(request.getParameter("treatment"));
        disease.setPublishTime(new Date());
        if (image!=null){
            String fileName = ImageUtils.ImgReceive(image,diseaseStore_path);
            disease.setImage(image_url+fileName);
        }

        int flag = diseaseMapper.insert(disease);
        if (flag>0){
            changeImage(disease);
            return disease;
        }else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }



    @Override
    public Boolean deleteDisease(Integer id) {
        int flag = diseaseMapper.delete(id);
        if (flag>0){
            return true;
        }else {
            throw new BusinessException(ResultCode.DATABASE_DELETE_ERROR);
        }
    }

    @Override
    public Disease updateDisease(MultipartFile image,HttpServletRequest request, Integer id) throws IOException {
        Disease disease = diseaseMapper.getOneDisease(id);
        if (disease == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        disease.setId(id);
        disease.setCause(request.getParameter("cause"));
        disease.setDiseaseName(request.getParameter("diseaseName"));
        disease.setSubKind(request.getParameter("subKind"));
        disease.setSymptom(request.getParameter("symptom"));
        disease.setTreatment(request.getParameter("treatment"));
        disease.setPublishTime(new Date());
        if (image!=null){
            String fileName = ImageUtils.ImgReceive(image,diseaseStore_path);
            disease.setImage(image_url+fileName);
        }

        int flag = diseaseMapper.update(disease);
        if (flag>0){
            Disease disease1 = diseaseMapper.getOneDisease(id);
            changeImage(disease1);
            return disease1;

        }else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }

    }

    @Override
    public PageVO<Disease> getAllDiseases(PageQO pageQO) {
        Page<Disease> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Disease> diseaseList = diseaseMapper.getAllDiseases();
        changeImages(diseaseList);
        return PageVO.build(page);
    }

    @Override
    public PageVO<Disease> getDiseasesBySubkind(String subKind,PageQO pageQO) {
        if (subKind.equals("所有")){
            Page<Disease> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            List<Disease> diseaseList = diseaseMapper.getAllDiseases();
            changeImages(diseaseList);
            return PageVO.build(page);
        }else {
            Page<Disease> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            List<Disease> diseaseList = diseaseMapper.getDiseasesBySubkind(subKind);
            changeImages(diseaseList);
            return PageVO.build(page);
        }
    }

    @Override
    public Disease getDiseaseInfo(Integer id) {
        Disease disease = diseaseMapper.getOneDisease(id);
        if (disease == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        if (disease.getVisitCount() == null){
            diseaseMapper.updateVisitCount(1,id);
        }else {
            diseaseMapper.updateVisitCount(disease.getVisitCount() + 1,id);
        }
        changeImage(disease);
        return disease;
    }

    private void changeImages(List<Disease> list){
        for (int i=0;i<list.size();i++){
            changeImage(list.get(i));
        }
    }

    private void changeImage(Disease obj){
        if (obj.getImage()!=null){
            obj.setImage(url+obj.getImage());
        }
    }
}
