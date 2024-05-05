package base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Tag(name = "通用接口模板")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
@JsonInclude(JsonInclude.Include.NON_NULL)
    public class ActionResult<T> {

    @Schema(description = "响应码")
    private Integer code;

    @Schema(description = "响应信息")
    private String msg;

    @Schema(description = "响应数据")
    private T data;

    @Schema(description = "拓展数据",hidden = true)
    @JsonIgnore
    private T exData;


    public void fine(String msg,T data){
        setCode(200);
        setMsg(msg);
        setData(data);
    }


    public static ActionResult success(){
        ActionResult jsonData = new ActionResult();
        jsonData.setCode(200);
        jsonData.setMsg("Success");
        return jsonData;
    }

    public static ActionResult success(String msg){
        ActionResult jsonData = new ActionResult();
        jsonData.setCode(200);
        jsonData.setMsg(msg);
        return jsonData;
    }

    public static ActionResult success(Object object){
        ActionResult jsonData = new ActionResult();
        jsonData.setCode(200);
        jsonData.setMsg("Success");
        jsonData.setData(object);
        return jsonData;
    }

    public static ActionResult success(String msg,Object object){
        ActionResult jsonData = new ActionResult();
        jsonData.setCode(200);
        jsonData.setMsg(msg);
        jsonData.setData(object);
        return jsonData;
    }


    public static ActionResult fail(Integer code,String message){
        ActionResult jsonData = new ActionResult();
        jsonData.setCode(code);
        jsonData.setMsg(message);
        return jsonData;
    }

    public static ActionResult fail(String msg){
        ActionResult jsonData = new ActionResult();
        jsonData.setCode(400);
        jsonData.setMsg(msg);
        return jsonData;
    }

    public static<T> ActionResult<T> fail(String msg,T data){
        ActionResult<T> jsonData = new ActionResult();
        jsonData.setCode(400);
        jsonData.setMsg(msg);
        jsonData.setData(data);
        return jsonData;
    }

    public ActionResult<T> successData(T object) {
        ActionResult<T> jsonData = new ActionResult<>();
        jsonData.setData(object);
        jsonData.setCode(200);
        jsonData.setMsg("Success");
        return jsonData;
    }

    public ActionResult<T> successData(String msg, T object) {
        ActionResult<T> jsonData = new ActionResult<>();
        jsonData.setData(object);
        jsonData.setCode(200);
        jsonData.setMsg(msg);
        return jsonData;
    }


    }

