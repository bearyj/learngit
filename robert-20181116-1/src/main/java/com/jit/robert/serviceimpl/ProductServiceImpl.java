package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.ImageUtils;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Product;
import com.jit.robert.mapper.ProductMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Value("${image.product.path}")
    private String product_path ;

    @Value("${image.product.url}")
    private String image_url;


    @Value("${image.url}")
    private String url;

    @Autowired
    private ProductMapper productMapper;
    /**
     * 插入百科
     * @return
     */
    @Override
    public Product insertProduct(MultipartFile image, HttpServletRequest request) throws IOException {
        Product product = new Product();
        product.setDescription(request.getParameter("description"));
        product.setKind(Integer.parseInt(request.getParameter("kind")==null?"0":request.getParameter("kind")));
        product.setName(request.getParameter("name"));
        product.setSubKind(request.getParameter("subKind"));
        if (image!=null){
            String fileName = ImageUtils.ImgReceive(image,product_path);
            product.setImage(image_url+fileName);
        }

        int flag = productMapper.insert(product);

        if (flag>0){
            changeImage(product);
            return product;
        }else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    /**
     * 删除百科
     * @param id
     * @return
     */
    @Override
    public Boolean deleteProduct(Integer id) {
       int flag = productMapper.delete(id);
       if (flag>0){
           return true;
       }else {
           throw new BusinessException(ResultCode.DATABASE_DELETE_ERROR);
       }
    }

    /**
     * 更新百科
     * @param id
     * @return
     */
    @Override
    public Product updateProduct(Integer id,MultipartFile image,HttpServletRequest request) throws IOException {

        Product product = productMapper.getOneProduct(id);
        if (product == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        product.setDescription(request.getParameter("description"));
        product.setKind(Integer.parseInt(request.getParameter("kind")==null?"0":request.getParameter("kind")));
        product.setName(request.getParameter("name"));
        product.setSubKind(request.getParameter("subKind"));
        if (image!=null){
            String fileName = ImageUtils.ImgReceive(image,product_path);
            product.setImage(image_url+fileName);
        }
        product.setId(id);
        int flag = productMapper.update(product);

        if (flag>0){
            Product product1 = productMapper.getOneProduct(id);
            changeImage(product1);
            return product1;
        }else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }
    }

    /**
     * 获取所有百科
     * @return
     */
    @Override
    public PageVO<Product> getAllProducts(PageQO pageQO) {
        Page<Product> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Product> productList = productMapper.getAllProducts();
        changeImages(productList);
        return PageVO.build(page);
    }

    /**
     * 根据类别获取所有百科
     * @param subKind
     * @return
     */
    @Override
    public PageVO<Product> getProductsBySubkind(String subKind,PageQO pageQO) {
        if (subKind.equals("所有")){
            Page<Product> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            List<Product> productList = productMapper.getAllProducts();
            changeImages(productList);
            return PageVO.build(page);
        }else {
            Page<Product> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
            List<Product> productList = productMapper.getProductsBySubkind(subKind);
            changeImages(productList);
            return PageVO.build(page);
        }

    }

    /**
     * 获取某一条百科
     * @param id
     * @return
     */
    @Override
    public Product getProductInfo(Integer id) {
        Product product = productMapper.getOneProduct(id);
        changeImage(product);
        return product;
    }

    private void changeImages(List<Product> list){
        for (int i=0;i<list.size();i++){
            changeImage(list.get(i));
        }
    }

    private void changeImage(Product obj){
        if (obj.getImage()!=null){
            obj.setImage(url+obj.getImage());
        }
    }
}
