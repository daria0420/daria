package com.daria.util;

import lombok.Data;

/**
 * @Author Daria
 * @Description
 * @Date 2020/5/4 -- 12:39
 */
@Data
public class BaseResponse<T> {
    private int code;
    private String msg;
    private T data;


    public BaseResponse() {
    }

//    public BaseResponse(CodeMsgUtil codeMsgUtil) {
//        this.code = codeMsgUtil.getCode();
//        this.msg = codeMsgUtil.getMsg();
//    }
//    public BaseResponse(CodeMsgUtil codeMsgUtil, T data) {
//        this.code = codeMsgUtil.getCode();
//        this.msg = codeMsgUtil.getMsg();
//        this.data = data;
//    }

    public void setCodeMsg(CodeMsgUtil codeMsg) {
        this.setMsg(codeMsg.getMsg());
        this.setCode(codeMsg.getCode());
    }

    public void setCodeMsg(CodeMsgUtil codeMsg, T data) {
        this.setMsg(codeMsg.getMsg());
        this.setCode(codeMsg.getCode());
        this.setData(data);
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
