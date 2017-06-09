package com.example.panzq.xutilsapp;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by panzq on 2017/6/9.
 */
@HttpResponse( parser = ResultParser.class)
public class ResponseEntity {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
