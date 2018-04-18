package com.dyenigma.sharing.util;

import com.alibaba.fastjson.JSONObject;
import com.dyenigma.sharing.constant.RespCodeEnum;
import com.dyenigma.sharing.constant.SystemConstant;
import com.dyenigma.sharing.exception.ApiException;
import com.dyenigma.sharing.exception.GlobalException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * backend/com.dyenigma.sharing.util
 *
 * @Description :
 * @Author : dingdongliang
 * @Date : 2018/3/11 19:18
 */
public class JsonUtil {

    /**
     * @param
     * @return com.alibaba.fastjson.JSONObject
     * @Description : 返回一个returnData为空对象的成功消息的json
     * @author dingdongliang
     * @date 2018/3/11 20:04
     */
    public static JSONObject successJson() {
        return successJson(new JSONObject());
    }

    /**
     * @param returnData
     * @return com.alibaba.fastjson.JSONObject
     * @Description : 返回一个返回码为1的json
     * @author dingdongliang
     * @date 2018/3/11 20:04
     */
    public static JSONObject successJson(Object returnData) {
        JSONObject resultJson = new JSONObject();
        resultJson.put(SystemConstant.CODE, SystemConstant.SUCCESS_RESULT);
        resultJson.put(SystemConstant.MESSAGE, SystemConstant.SUCCESS_MSG);
        resultJson.put(SystemConstant.DATA, returnData);
        return resultJson;
    }

    /**
     * @param request
     * @param requiredColumns
     * @return JSONObject
     * @Description : 将request转JSON并且验证非空字段
     * @author dingdongliang
     * @date 2018/3/11 19:18
     */
    public static JSONObject convert2JsonAndCheckRequiredColumns(HttpServletRequest request, String requiredColumns)
            throws GlobalException {
        JSONObject jsonObject = request2Json(request);
        hasAllRequired(jsonObject, requiredColumns);
        return jsonObject;
    }

    /**
     * @param request
     * @return com.alibaba.fastjson.JSONObject
     * @Description : 将request参数值转为json
     * @author dingdongliang
     * @date 2018/3/11 19:19
     */
    public static JSONObject request2Json(HttpServletRequest request) {
        JSONObject requestJson = new JSONObject();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] pv = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pv.length; i++) {
                if (pv[i].length() > 0) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(pv[i]);
                }
            }
            requestJson.put(paramName, sb.toString());
        }
        return requestJson;
    }

    /**
     * @param jsonObject
     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,userName,userBirth"
     * @return void
     * @Description : 验证是否含有全部必填字段
     * @author dingdongliang
     * @date 2018/3/11 19:20
     */
    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) throws GlobalException {
        if (!StringUtil.isNullOrEmpty(requiredColumns)) {
            //验证字段非空
            String[] columns = requiredColumns.split(",");
            String missCol = "";
            for (String column : columns) {
                Object val = jsonObject.get(column.trim());
                if (StringUtil.isNullOrEmpty(val)) {
                    missCol += column + "  ";
                }
            }
            if (!StringUtil.isNullOrEmpty(missCol)) {
                throw new ApiException(RespCodeEnum.NOT_ENOUGH_PARAMS);
            }
        }
    }

    /**
     * @param respCodeEnum 错误码的errorEnum
     * @return com.alibaba.fastjson.JSONObject
     * @Description : 返回错误信息JSON
     * @author dingdongliang
     * @date 2018/3/11 19:30
     */
    public static JSONObject errorJson(RespCodeEnum respCodeEnum) {
        JSONObject resultJson = new JSONObject();
        resultJson.put(SystemConstant.CODE, respCodeEnum.getCode());
        resultJson.put(SystemConstant.MESSAGE, respCodeEnum.getMessage());
        resultJson.put(SystemConstant.DATA, new JSONObject());
        return resultJson;
    }

}
