package top.nodaoli.dto;

import java.io.Serializable;

/**
 * 通用 API 响应封装类
 * @param <T> 返回数据类型
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    private int code;

    /** 提示信息 */
    private String msg;

    /** 返回数据 */
    private T data;

    // ======================== 构造方法 ========================

    public Result() {}

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this(code, msg);
        this.data = data;
    }

    // ======================== 快捷静态方法 ========================

    public static <T> Result<T> ok() {
        return new Result<>(200, "成功");
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(200, "成功", data);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    public static <T> Result<T> error() {
        return new Result<>(500, "服务器内部错误");
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg);
    }

    // ======================== 链式调用支持 ========================

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    // ======================== Getter / Setter ========================

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
