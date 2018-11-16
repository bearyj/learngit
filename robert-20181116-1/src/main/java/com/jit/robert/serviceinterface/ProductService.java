package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Product;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product insertProduct(MultipartFile image, HttpServletRequest request) throws IOException;
    Boolean deleteProduct(Integer id);
    Product updateProduct(Integer id,MultipartFile image,HttpServletRequest request) throws IOException;
    PageVO<Product> getAllProducts(PageQO pageQO);
    PageVO<Product> getProductsBySubkind(String subKind,PageQO pageQO);
    Product getProductInfo(Integer id);


}
