package com.jit.robert.controller;

import com.jit.robert.common.WordUtils;
import com.jit.robert.responseResult.result.ResponseResult;
import com.jit.robert.serviceinterface.WordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Api(value = "excel", description = "word下载，暂时不用")
@RestController
@ResponseResult
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public void wordDownload() throws Exception {
        wordService.wordDownload();
    }
}
