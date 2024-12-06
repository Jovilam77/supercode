package ${config.basePackage}<#if config.module?? && config.module!=''>.${config.module!}</#if>.model.vo;

/**
 * 结果集
 *
 * @author ${config.author!}<#if config.email?? && config.email!=''>《${config.email!}》</#if>
 * @version ${config.version!}《${date?string('yyyy-MM-dd HH:mm:ss')}》
 */
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        return result;
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    /**
     * 成功
     *
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMsg("操作失败");
        return result;
    }

    /**
     * 失败
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> fail(String msg) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }

    /**
     * 自定义
     *
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> other(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 自定义
     *
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> other(int code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

}