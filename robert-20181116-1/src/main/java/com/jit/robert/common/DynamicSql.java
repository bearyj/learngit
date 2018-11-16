package com.jit.robert.common;

import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.Map;

@Slf4j
public class DynamicSql {

    /**
     * 构建根据省市地区筛选、注册时间筛选的动态sql
     * @param para
     * @return
     */
    public String QuerySql(Map<String, Object> para){
        StringBuilder sql = new StringBuilder();
        Integer type = (Integer) para.get("type");
        QueryStrategy strategy = (QueryStrategy) para.get("strategy");
        String province = strategy.getProvince();
        String city = strategy.getCity();
        String county = strategy.getCounty();
//        String address = strategy.getAddress();
        String beginTime = strategy.getBeginTime();
        String endTime = strategy.getEndTime();
//        String registerTime = strategy.getRegisterTime();
        if (type == 1){
            sql.append("select C.*, D.repairNum from (select A.*,B.registertime from (select * from technology) AS A LEFT JOIN " +
                    "(select registertime,username from user) AS B ON B.username = A.username where 1=1 ");
            if (!province.equals(""))
                sql.append(" and A.province = '" + province + "'");
            if (!city.equals(""))
                sql.append(" and A.city = '" + city + "'");
            if (!county.equals(""))
                sql.append(" and A.county = '" + county + "'");
            if (! beginTime.equals("")&&!endTime.equals(""))
                sql.append(" and date_format(B.registertime,'%Y-%m-%d') >= '" + beginTime + "' and date_format(B.registertime,'%Y-%m-%d') <= '" + endTime + "'");
            sql.append(") AS C LEFT JOIN (select technology_id, COUNT(technology_id) AS repairNum from robert.`repair` GROUP BY technology_id) " +
                    "AS D ON C.id = D.technology_id ORDER BY enter_time DESC");
        }else {
            sql.append("select A.*, B.registertime,B.image from (select * from ");
            if (type == 2){
                sql.append(" expert)" );
            } else if (type == 0){
                sql.append(" customer)");
            }
            sql.append(" as A left join (select registertime, image, username from user) as B on B.username = A.username where 1=1 ");
            if (!province.equals(""))
                sql.append(" and A.province = '" + province + "'");
            if (!city.equals(""))
                sql.append(" and A.city = '" + city + "'");
            if (!county.equals(""))
                sql.append(" and A.county = '" + county + "'");
            if (! beginTime.equals("")&&!endTime.equals(""))
                sql.append(" and date_format(B.registertime,'%Y-%m-%d') >= '" + beginTime + "' and date_format(B.registertime,'%Y-%m-%d') <= '" + endTime + "'");
        }
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    /**
     * 获取要删除用户的id
     * @param para
     * @return
     */
    public String getUserIds(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        Integer type = (Integer) para.get("type");
        String ids = (String) para.get("ids");
        sql.append("select id as userId from user where username in (select username from ");
        if (type == 0){
           sql.append(" customer ");
        } else if (type == 1){
            sql.append(" technology ");
        } else if (type == 2){
            sql.append(" expert ");
        }
        sql.append("where id in (" + ids + "))");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    /**
     * 批量删除用户
     * @param para
     * @return
     */
    public String deleteUserBatch(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");
        sql.append("delete from user where id in (" + ids + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    public String getRepairListSql(Map<String, Object> para){
        StringBuilder sql = new StringBuilder();
        Integer status = (Integer) para.get("status");
        Integer robert_id = (Integer) para.get("robert_id");
        sql.append("select * from repair where robert_id = '" + robert_id + "'");
        if (!(status == null)){
            sql.append(" and status = '"+ status + "'");
        }
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    public String getRepairDTOListSql(Map<String, Object> para){
        StringBuilder sql = new StringBuilder();
        Integer status = (Integer) para.get("status");
        Integer robert_id = (Integer) para.get("robert_id");
        sql.append("SELECT C.*, D.technology_realname, D.technology_username, D.technology_tel FROM " +
                "(SELECT A.*, B.number ,B.user_id, B.type FROM " +
                "(SELECT * FROM robert.repair WHERE robert_id = '" + robert_id + "'");
        if (!(status == null)){
            sql.append(" and status = '"+ status + "'");
        }
        sql.append(" ) AS A LEFT JOIN " +
                "(SELECT r.number, r.id, r.user_id, r.type FROM robert r) AS B ON B.id = A.robert_id) AS C LEFT JOIN " +
                "(SELECT t.id, t.`realname` AS technology_realname ,t.`username` AS technology_username ,t.`tel` AS technology_tel FROM technology t )AS D ON C.technology_id = D.id");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    public String deleteDownlogBatchSql(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");
        sql.append("delete from downlog where id in (" + ids + ") ");
        return sql.toString();
    }

}
