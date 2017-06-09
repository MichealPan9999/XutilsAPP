package com.example.panzq.xutilsapp;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * Created by panzq on 2017/6/9.
 */
public class ResultParser implements ResponseParser {
    public ResultParser() {
    }

    @Override
    public void checkResponse(UriRequest request) throws Throwable {

    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setResult(result);
        return responseEntity;
    }
}
