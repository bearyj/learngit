package com.jit.robert.serviceimpl;


import com.jit.robert.domain.Downlog;
import com.jit.robert.mapper.DownlogMapper;
import com.jit.robert.serviceinterface.DownlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DownlogServiceImpl implements DownlogService {
    @Autowired
    private DownlogMapper downlogMapper;
    @Override
    public Downlog addDownlog(String downlogname ) throws ParseException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Downlog downlog  = new Downlog();
        downlog.setDownlogname(downlogname);
        downlog.setDownlogtime(new Date());
        downlog.setUsername(username);
        Downlog isExist = downlogMapper.getDownlogByName(downlogname, username);
        if (isExist != null){
            int id = isExist.getId();
            downlog.setId(id);
            downlogMapper.update(downlog);
        }else {
            downlogMapper.insert(downlog);
        }
        return downlog;
    }

    @Override
    public Boolean deleteDownlogBatch(String ids) {
        int flag = -1;
        //批量删除
        if (ids.contains("-")){
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");

            //组装id的集合
            for (String string : str_ids){
                del_ids.add(Integer.parseInt(string));
            }
            String delIds = del_ids.toString();
            flag = downlogMapper.deleteDownlogBatch(delIds.substring(1,delIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            flag = downlogMapper.deleteDownlog(id);
        }
        if (flag>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Downlog> getAllDownlog() {
        List<Downlog> downlogs = downlogMapper.getAllDownlog();
        return downlogs;
    }
}
