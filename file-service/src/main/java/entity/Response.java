package entity;


import java.io.Serializable;

/**
 * @author 凌雪
 */

public class Response implements Serializable {

    private static final long serialVersionUID = 2L;

    public static class ResponseState {
        public static final Integer OK = 200;
        public static final Integer UN_AUTHENTICATION = 401;
        public static final Integer BAD_REQUEST  = 400;
        public static final Integer NO_ACCESS  = 403;
        public static final Integer SERVER_ERROR  = 500;
    }

    private Integer code;
    private Object data;
    private String msg;


    public static Response ok(){
        return new Response(ResponseState.OK, null, null);
    }
    public static Response ok(Object data, String msg){
        return new Response(ResponseState.OK, data, msg);
    }
    public static Response ok(String msg){
        return new Response(ResponseState.OK, null, msg);
    }

    public static Response ok(Object data){
        return new Response(ResponseState.OK, data, null);
    }

    public static Response fail(Integer code, String msg){
        return new Response(code, null, msg);
    }

    public static Response fail(String msg){
        return new Response(ResponseState.SERVER_ERROR, null, msg);
    }

    public Response() {
    }

    public Response(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
