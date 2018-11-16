package com.jit.robert.serviceimpl;

import com.jit.robert.common.WordUtils;
import com.jit.robert.serviceinterface.WordService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class WordServiceImpl implements WordService {
    @Override
    public void wordDownload() throws Exception {
        String tmpFile = "D:/template.doc";
        String expFile = "D:/result.doc";
        Map<String, String> datas = new HashMap<String, String>();
        datas.put("${title}", "标题部份");
        datas.put("${content}", "这里是内容，测试使用POI导出到Word的内容！");
        datas.put("${author}", "作者");
        datas.put("${url}", "http://www.jit.edu.cn");

        XWPFDocument doc;
        doc = WordUtils.generateWord(datas, "D:\\template.doc");
        File file = new File("D:\\test1.doc");
        // 生成文件
        FileOutputStream fop = null;
        fop = new FileOutputStream(file);
        doc.write(fop);
    }
}
