package com.jit.robert.serviceinterface;

import javax.servlet.http.HttpServletResponse;

public interface ExcelService {
    void exportRobertExcel(HttpServletResponse response) throws Exception;
    void exportAdminExcel(HttpServletResponse response) throws Exception;
    void exportTechnologyExcel(HttpServletResponse response) throws Exception;
    void exportTechnologyTaskExcel(HttpServletResponse response) throws Exception;
    void exportRobertParamExcel(HttpServletResponse response) throws Exception;
    void exportExpertExcel(HttpServletResponse response) throws Exception;
    void exportCustomerExcel(HttpServletResponse response) throws Exception;


}
