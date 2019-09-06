package cn.fdongl.authority.tool;

/**
 * @ClassName AjaxMessage
 * @Description 后端返回前端的标准信息格式
 * @Author zm
 * @Date 2019/9/6 14:37
 * @Version 1.0
 **/
public class AjaxMessage {

    // 状态码，从枚举MsgType转换而来，全小写
    private String code;
    // 数据
    private Object data;
    // 提示信息
    private String message;

    public AjaxMessage Set(MsgType msgType, Object data, String message){
        this.code = msgType.toString().toLowerCase();
        this.data = data;
        this.message = message;
        return this;
    }

    public AjaxMessage Set(MsgType msgType, Object data){
        this.code = msgType.toString().toLowerCase();
        this.data = data;
        this.message = "";
        return this;
    }

    public AjaxMessage Set(MsgType msgType){
        this.code = msgType.toString().toLowerCase();
        this.data = "";
        this.message = "";
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
