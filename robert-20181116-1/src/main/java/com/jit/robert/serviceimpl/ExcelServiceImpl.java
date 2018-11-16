package com.jit.robert.serviceimpl;

import com.jit.robert.common.ExcelData;
import com.jit.robert.common.ExcelUtils;
import com.jit.robert.domain.*;
import com.jit.robert.dto.AdminDTO;
import com.jit.robert.dto.RobertDTO;
import com.jit.robert.dto.TaskDTO;
import com.jit.robert.dto.TechnologyDTO;
import com.jit.robert.mapper.*;
import com.jit.robert.serviceinterface.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    private RobertMapper robertMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private TechnologyMapper technologyMapper;

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private AlarmMapper alarmMapper;

    @Autowired
    private ExpertMapper expertMapper;

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 导出机器人列表到excel
     * @param response
     * @throws Exception
     */
    @Override
    public void exportRobertExcel(HttpServletResponse response) throws Exception {
        List<RobertDTO> roberts = robertMapper.getAllRoberts();
        ExcelData excelData = new ExcelData();
        excelData = setRobertList(roberts);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
        ////下载测试
//        File f = new File("D:/"+"robert"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"robert"+fileName+".excel",excelData);
    }

    private ExcelData setRobertList(List<RobertDTO> robertList){
        ExcelData excelData = new ExcelData();
        excelData.setName("robert");
        List<String> titles = new ArrayList<>();
        titles.add("序号");

        titles.add("分类");
        titles.add("注册时间");
        titles.add("联系人");
        titles.add("性别");
        titles.add("年龄");
        titles.add("联系电话");
        titles.add("省");
        titles.add("市");
        titles.add("区(县)");
        titles.add("地址");
        titles.add("机器人编号");
        titles.add("养殖种类");
        titles.add("饵料");
        titles.add("药品");
        titles.add("状态");
        excelData.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        Integer number = 1;
        for(RobertDTO robert:robertList){
            List<Object> row = new ArrayList();
            row.add(number);

            row.add(robert.getType());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(robert.getRegistertime() !=null){
                row.add(sdf.format(robert.getRegistertime()));
            }else {
                row.add("");
            }

            row.add(robert.getUsername());
            if (robert.getSex()!=null){
                if (robert.getSex()==0){
                    row.add("男");
                }else {
                    row.add("女");
                }
            }else{
                row.add("");
            }

            row.add(robert.getAge());
            row.add(robert.getTel());
            row.add(robert.getProvince());
            row.add(robert.getCity());
            row.add(robert.getCounty());
            row.add(robert.getAddress());
            row.add(robert.getNumber());

            row.add(robert.getCategory());
            row.add(robert.getFeed_name());
            row.add(robert.getMedicine_name());
            row.add("使用中");
            rows.add(row);
            number++;
        }

        excelData.setRows(rows);
        return excelData;
    }

    /**
     * 导出管理员列表到excel
     * @param response
     * @throws Exception
     */
    @Override
    public void exportAdminExcel(HttpServletResponse response) throws Exception {
        List<AdminDTO> adminDTOS = adminMapper.getAllAdmins();
        ExcelData excelData = new ExcelData();
        excelData = setAdminList(adminDTOS);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
        ////下载测试
//        File f = new File("D:/"+"admin"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"admin"+fileName+".excel",excelData);
    }
    private ExcelData setAdminList(List<AdminDTO> adminDTOList){
        ExcelData excelData = new ExcelData();
        excelData.setName("admin");
        List<String> titles = new ArrayList<>();
        titles.add("序号");
        titles.add("姓名");
        titles.add("工号");
        titles.add("部门");
        titles.add("所属用户组");
        titles.add("注册时间");

        excelData.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        Integer number = 1;
        for(AdminDTO adminDTO:adminDTOList){
            List<Object> row = new ArrayList();
            row.add(number);
            row.add(adminDTO.getUsername());
            row.add(adminDTO.getNumber());
            row.add(adminDTO.getDepartment());
            row.add(adminDTO.getUser_group());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (adminDTO.getLogin_time()!=null){
                row.add(sdf.format(adminDTO.getLogin_time()));
            }else{
                row.add("");
            }
            rows.add(row);
            number++;
        }

        excelData.setRows(rows);
        return excelData;
    }
    /**
     * 导出技术人员列表到excel
     * @param response
     * @throws Exception
     */
    @Override
    public void exportTechnologyExcel(HttpServletResponse response) throws Exception {
        List<TechnologyDTO> technologyDTOS = technologyMapper.getAllTechnologies();
        ExcelData excelData = new ExcelData();
        excelData = setTechnologyList(technologyDTOS);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
        ////下载测试
//        File f = new File("D:/"+"technology"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"technology"+fileName+".excel",excelData);
    }
    private ExcelData setTechnologyList(List<TechnologyDTO> technologyDTOList){
        ExcelData excelData = new ExcelData();
        excelData.setName("technology");
        List<String> titles = new ArrayList<>();
        titles.add("序号");
        titles.add("入职时间");
        titles.add("联系人");
        titles.add("性别");
        titles.add("年龄");
        titles.add("联系电话");
        titles.add("省");
        titles.add("市");
        titles.add("区(县)");
        titles.add("地址");
        titles.add("工号");
        titles.add("职位");
        titles.add("正在维修数量");


        excelData.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        Integer number = 1;
        for(TechnologyDTO technologyDTO:technologyDTOList){
            List<Object> row = new ArrayList();
            row.add(number);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (technologyDTO.getEnter_time() !=null){
                row.add(sdf.format(technologyDTO.getEnter_time()));
            }else {
                row.add("");
            }

            row.add(technologyDTO.getUsername());
            if (technologyDTO.getSex() !=null){
                if (technologyDTO.getSex() == 0){
                    row.add("男");
                }else {
                    row.add("女");
                }
            }else {
                row.add("");
            }

            row.add(technologyDTO.getAge());
            row.add(technologyDTO.getTel());
            row.add(technologyDTO.getProvince());
            row.add(technologyDTO.getCity());
            row.add(technologyDTO.getCounty());
            row.add(technologyDTO.getAddress());
            row.add(technologyDTO.getNumber());
            row.add(technologyDTO.getPosition());
            row.add(technologyDTO.getRepairNum());
            rows.add(row);
            number++;
        }

        excelData.setRows(rows);
        return excelData;
    }
    /**
     * 导出技术人员任务列表到excel
     * @param response
     * @throws Exception
     */
    @Override
    public void exportTechnologyTaskExcel(HttpServletResponse response) throws Exception {
        List<TaskDTO> taskDTOS = repairMapper.getAllRepairTasks();
        ExcelData excelData = new ExcelData();
        excelData = setTaskList(taskDTOS);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
        ////下载测试
//        File f = new File("D:/"+"task"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"task"+fileName+".excel",excelData);
    }

    private ExcelData setTaskList(List<TaskDTO> taskDTOList){
        ExcelData excelData = new ExcelData();
        excelData.setName("task");
        List<String> titles = new ArrayList<>();
        titles.add("序号");
        titles.add("产品类别");
        titles.add("送修时间");
        titles.add("客户");
        titles.add("性别");
        titles.add("年龄");
        titles.add("联系电话");
        titles.add("机器人编号");
        titles.add("负责技术人员");
        titles.add("故障描述");


        excelData.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        Integer number = 1;
        for(TaskDTO taskDTO:taskDTOList){
            List<Object> row = new ArrayList();
            row.add(number);
            row.add(taskDTO.getType());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (taskDTO.getTime() != null){
                row.add(sdf.format(taskDTO.getTime()));
            }else {
                row.add("");
            }

            row.add(taskDTO.getCustomer_id());
            if (taskDTO.getSex() !=null){
                if (taskDTO.getSex() == 0){
                    row.add("男");
                }else {
                    row.add("女");
                }
            }else {
                row.add("");
            }

            row.add(taskDTO.getAge());
            row.add(taskDTO.getTel());
            row.add(taskDTO.getNumber());
            row.add(taskDTO.getTechnology_name());
            row.add(taskDTO.getDescription());
            rows.add(row);
            number++;
        }

        excelData.setRows(rows);
        return excelData;
    }
    /**
     * 导出机器人配置表到excel
     * @param response
     * @throws Exception
     */
    @Override
    public void exportRobertParamExcel(HttpServletResponse response) throws Exception {
        List<Alarm> alarms = alarmMapper.getAllAlarm();
        ExcelData excelData = new ExcelData();
        excelData = setRobertParamList(alarms);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
        ////下载测试
//        File f = new File("D:/"+"alarm"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"alarm"+fileName+".excel",excelData);
    }

    private ExcelData setRobertParamList(List<Alarm> alarmList){
        ExcelData excelData = new ExcelData();
        excelData.setName("alarm");
        List<String> titles = new ArrayList<>();
        titles.add("序号");
        titles.add("水产机器人");
        titles.add("水温上限(℃)");
        titles.add("水温下限(℃)");
        titles.add("PH上限");
        titles.add("PH下限");
        titles.add("溶解氧上限(mg/l)");
        titles.add("溶解氧下限(mg/l)");
        titles.add("电量");
        titles.add("饵料箱");
        titles.add("增氧");
        titles.add("灯控");
        titles.add("传感器自动清洗(天)");


        excelData.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        Integer number = 1;
        for(Alarm alarm:alarmList){
            List<Object> row = new ArrayList();
            row.add(number);
            row.add(alarm.getRobert_id());
            row.add(alarm.getTemperature_max());
            row.add(alarm.getTemperature_min());
            row.add(alarm.getPh_max());
            row.add(alarm.getPh_min());
            row.add(alarm.getOxygen_max());
            row.add(alarm.getOxygen_min());
            row.add(alarm.getPower());
            row.add(alarm.getFeed());
            row.add("增氧");
            row.add("灯控");
            row.add(alarm.getWash());
            rows.add(row);
            number++;
        }

        excelData.setRows(rows);
        return excelData;
    }
    /**
     * 导出专家列表到excel
     * @param response
     * @throws Exception
     */
    @Override
    public void exportExpertExcel(HttpServletResponse response) throws Exception {
        List<Expert> experts = expertMapper.getAllExperts();
        ExcelData excelData = new ExcelData();
        excelData = setExpertList(experts);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
        ////下载测试
//        File f = new File("D:/"+"expert"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"expert"+fileName+".excel",excelData);
    }
    private ExcelData setExpertList(List<Expert> expertList){
        ExcelData excelData = new ExcelData();
        excelData.setName("expert");
        List<String> titles = new ArrayList<>();
        titles.add("序号");
        titles.add("产品类别");
        titles.add("负责类别");
        titles.add("签约时间");
        titles.add("联系人");
        titles.add("性别");
        titles.add("年龄");
        titles.add("联系电话");
        titles.add("省");
        titles.add("市");
        titles.add("区(县)");
        titles.add("地址");
        titles.add("工号");
        titles.add("学历");
        titles.add("所在单位");

        excelData.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        Integer number = 1;
        for(Expert expert:expertList){
            List<Object> row = new ArrayList();
            row.add(number);
            row.add(expert.getProduct());
            row.add(expert.getMajor());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (expert.getSign_time() != null){
                row.add(sdf.format(expert.getSign_time()));
            }else{
                row.add("");
            }

            row.add(expert.getUsername());
            if (expert.getSex() != null){
                if (expert.getSex() == 0){
                    row.add("男");
                }else {
                    row.add("女");
                }
            }else {
                row.add("");
            }

            row.add(expert.getAge());
            row.add(expert.getTel());
            row.add(expert.getProvince());
            row.add(expert.getCity());
            row.add(expert.getCounty());
            row.add(expert.getAddress());
            row.add(expert.getNumber());
            row.add(expert.getDegree());
            row.add(expert.getCompany());
            rows.add(row);
            number++;
        }

        excelData.setRows(rows);
        return excelData;
    }

    @Override
    public void exportCustomerExcel(HttpServletResponse response) throws Exception {
        List<Customer> customerList = customerMapper.getAllCustomers();
        ExcelData excelData = new ExcelData();
        excelData = setCustomerList(customerList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
        ////下载测试
//        File f = new File("D:/"+"customer"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"customer"+fileName+".excel",excelData);
    }
    private ExcelData setCustomerList(List<Customer> customerList){
        ExcelData excelData = new ExcelData();
        excelData.setName("customer");
        List<String> titles = new ArrayList<>();
        titles.add("序号");
        titles.add("客户名称");
        titles.add("账号名称");
        titles.add("产品类别");
        titles.add("性别");
        titles.add("年龄");
        titles.add("联系电话");
        titles.add("电子邮箱");
        titles.add("省");
        titles.add("市");
        titles.add("区(县)");
        titles.add("地址");
        excelData.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        Integer number = 1;
        for(Customer customer:customerList){
            List<Object> row = new ArrayList();
            row.add(number);
            row.add(customer.getRealname());
            row.add(customer.getUsername());
            row.add(customer.getType());
            if (customer.getSex() != null){
                if (customer.getSex() == 0){
                    row.add("男");
                }else {
                    row.add("女");
                }
            }else {
                row.add("");
            }

            row.add(customer.getAge());
            row.add(customer.getTel());
            row.add(customer.getEmail());
            row.add(customer.getProvince());
            row.add(customer.getCity());
            row.add(customer.getCounty());
            row.add(customer.getAddress());
            rows.add(row);
            number++;
        }

        excelData.setRows(rows);
        return excelData;
    }
}
