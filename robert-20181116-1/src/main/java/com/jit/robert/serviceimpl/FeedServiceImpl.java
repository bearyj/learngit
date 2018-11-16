package com.jit.robert.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Feed;
import com.jit.robert.domain.Pound;
import com.jit.robert.mapper.FeedMapper;
import com.jit.robert.mapper.PoundMapper;
import com.jit.robert.responseResult.enums.ResultCode;
import com.jit.robert.responseResult.exceptions.BusinessException;
import com.jit.robert.serviceinterface.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedMapper feedMapper;

    @Autowired
    private PoundMapper poundMapper;

    /**
     * 插入日常投喂
     * @return
     * @throws ParseException
     */
    @Override
    @Transactional
    public Feed insert( HttpServletRequest request) throws ParseException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate =  request.getParameter("date");
        Date date = sdf.parse(strDate);

        if (request.getParameter("pound_id") == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        Integer pound_id = Integer.parseInt(request.getParameter("pound_id"));
        Pound pound = poundMapper.getPoundById(pound_id);
        if (pound == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        Feed newFeed = new Feed();
        newFeed.setDate(date);
        newFeed.setUsername(username);
        newFeed.setCount1(Integer.parseInt(request.getParameter("count1")!=null?request.getParameter("count1"):"0"));
        newFeed.setCount2(Integer.parseInt(request.getParameter("count2")!=null?request.getParameter("count2"):"0"));
        newFeed.setCount3(Integer.parseInt(request.getParameter("count3")!=null?request.getParameter("count3"):"0"));
        newFeed.setCount4(Integer.parseInt(request.getParameter("count4")!=null?request.getParameter("count4"):"0"));
        newFeed.setCount5(Integer.parseInt(request.getParameter("count5")!=null?request.getParameter("count5"):"0"));
        newFeed.setCount6(Integer.parseInt(request.getParameter("count6")!=null?request.getParameter("count6"):"0"));
        newFeed.setPound_id(pound_id);

        Feed isExist = feedMapper.findByDate(pound_id,strDate);
        int flag = -1;
        if (isExist != null){
            flag = feedMapper.update(newFeed);
        }else {
            flag = feedMapper.insert(newFeed);
        }
        if ( flag >0){
            return newFeed;
        }else {
            throw new BusinessException(ResultCode.DATA_IS_WRONG);
        }
    }

    @Override
    public PageVO<Feed> getFeedByPound(Integer pound_id, PageQO pageQO) {
        Page<Feed> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Feed> feeds = feedMapper.getFeeds(pound_id);
        if (feeds != null){
            return PageVO.build(page,feeds);
        }else {
            throw new BusinessException(ResultCode.DATA_IS_WRONG);
        }
    }
}
