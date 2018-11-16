package com.jit.robert.serviceinterface;

import com.jit.robert.common.PageQO;
import com.jit.robert.common.PageVO;
import com.jit.robert.domain.Feed;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

public interface FeedService {
    Feed insert(HttpServletRequest request) throws ParseException;
    PageVO<Feed> getFeedByPound(Integer pound_id, PageQO pageQO);
}
