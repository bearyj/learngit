package com.jit.robert.common;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 适用于word 2007
 * poi 版本 3.7
 */
public class WordUtils {
    private static Log log = LogFactory.getLog(WordUtils.class);

    /**
     * 根据指定的参数值、模板，生成 word 文档
     * @param param    需要替换的变量
     * @param template 模板
     */
    public static XWPFDocument generateWord(Map<String, String> param, String template) {
        XWPFDocument doc = null;
        try {
            OPCPackage pack = POIXMLDocument.openPackage(template);
            doc = new XWPFDocument(pack);
            if (param != null && param.size() > 0) {

                //处理段落  
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc);

                //处理表格  
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            processParagraphs(paragraphListTable, param, doc);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 处理段落
     *
     * @param paragraphList
     */
    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, String> param, XWPFDocument doc) {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Entry<String, String> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if (text.indexOf(key) != -1) {
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {//文本替换  
                                    text = text.replace(key, value.toString());
                                } else if (value instanceof Map) {/*//图片替换  
                                    text = text.replace(key, "");  
                                    Map pic = (Map)value;  
                                    int width = Integer.parseInt(pic.get("width").toString());  
                                    int height = Integer.parseInt(pic.get("height").toString());  
                                    int picType = getPictureType(pic.get("type").toString());  
                                    byte[] byteArray = (byte[]) pic.get("content");  
                                    ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);  
                                    try {  
                                        int ind = doc.addPicture(byteInputStream,picType);  
                                        doc.createPicture(ind, width , height,paragraph);  
                                    } catch (Exception e) {  
                                        e.printStackTrace();  
                                    }  
                                */
                                }
                            }
                        }
                        if (isSetText) {
                            if (text.contains("\n")) {
                                String[] ts = text.split("\n");
                                run.setText("", 0);
                                CTText ctText = null;
                                for (String t : ts) {
                                    ctText = run.getCTR().addNewInstrText();
                                    ctText.setSpace(SpaceAttribute.Space.PRESERVE);
                                    ctText.setStringValue(t);
                                    run.getCTR().addNewBr();
                                }
                            } else {
                                run.setText(text, 0);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据图片类型，取得对应的图片类型代码
     *
     * @param picType
     * @return int
     */
    @SuppressWarnings("unused")
    private static int getPictureType(String picType) {
        int res = XWPFDocument.PICTURE_TYPE_PICT;
        if (picType != null) {
            if (picType.equalsIgnoreCase("png")) {
                res = XWPFDocument.PICTURE_TYPE_PNG;
            } else if (picType.equalsIgnoreCase("dib")) {
                res = XWPFDocument.PICTURE_TYPE_DIB;
            } else if (picType.equalsIgnoreCase("emf")) {
                res = XWPFDocument.PICTURE_TYPE_EMF;
            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
                res = XWPFDocument.PICTURE_TYPE_JPEG;
            } else if (picType.equalsIgnoreCase("wmf")) {
                res = XWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

    /**
     * 将输入流中的数据写入字节数组
     *
     * @param in
     * @return
     */
    public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isClose) {
                try {
                    in.close();
                } catch (Exception e2) {
                    log.error("关闭流失败");
                }
            }
        }
        return byteArray;
    }
}  