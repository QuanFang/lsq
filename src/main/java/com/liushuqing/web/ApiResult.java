/**
 * 
 */
package com.liushuqing.web;

/**
 * @author nancheng
 *
 */
public class ApiResult {
    /**
     * 
     */
    public ApiResult() {
        this.code = 200;
        this.msg = "OK";
        this.result = null;
    }

    private int code;
    private String msg;
    private Object result;

    public int getCode() {
        return code;
    }

    public ApiResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ApiResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public ApiResult setResult(Object result) {
        this.result = result;
        return this;
    }

}
