package com.jit.robert.common;

public class JsonResultHelper {
    public static JsonResult getSuccessRes(int code, String msg, Object res, int loginStatus){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(code);
        jsonResult.setRes(res);
        jsonResult.setLoginStatus(loginStatus);
        msg = msg == null ? "success!" : msg;
        jsonResult.setMessage(msg);
        return jsonResult;
    }

    public static JsonResult getErrorRes(int code, String msg, Object res, int loginStatus){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(code);
        jsonResult.setRes(res);
        jsonResult.setLoginStatus(loginStatus);
        msg = msg == null ? "unknown error!" : msg;
        jsonResult.setMessage(msg);
        return jsonResult;
    }

    public static JsonResult getErrorRes(int resCode,String msg,Object res){

        return getErrorRes(resCode,msg,res,1);
    }

    public static JsonResult getSuccessRes(int code,String msg,Object res){
        return getSuccessRes(code,msg,res,1);
    }
    public static JsonResult getSuccessRes(){
        return getSuccessRes(1,null,null,1);
    }
    public static JsonResult getErrorRes(){
        return getErrorRes(0,null,null,1);
    }
}
