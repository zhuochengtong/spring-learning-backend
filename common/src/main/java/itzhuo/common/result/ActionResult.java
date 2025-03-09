package itzhuo.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import itzhuo.common.constants.MsgCode;
import lombok.Data;

import java.util.List;

/**
 * @author JNPF开发平台组
 * @version V3.1.0
 * @copyright 引迈信息技术有限公司
 * @date 2021/3/16 10:45
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActionResult<T> {

    @Schema(description = "状态码")
    private Integer code;

    @Schema(description = "返回信息")
    private String msg;

    @Schema(description = "返回数据")
    private T data;

    /* ============== success ============ */

    public static <T> ActionResult<T> success() {
        ActionResult<T> jsonData = new ActionResult<>();
        jsonData.setCode(200);
        jsonData.setMsg(MsgCode.SU000.get());
        return jsonData;
    }

    public static <T> ActionResult<T> success(String msg) {
        ActionResult<T> jsonData = new ActionResult<>();
        jsonData.setCode(200);
        jsonData.setMsg(msg);
        return jsonData;
    }

    public static <T> ActionResult<T> success(T object) {
        ActionResult<T> jsonData = new ActionResult<>();
        jsonData.setData(object);
        jsonData.setCode(200);
        jsonData.setMsg(MsgCode.SU000.get());
        return jsonData;
    }

    public static <T> ActionResult<T> success(String msg, T object) {
        ActionResult<T> jsonData = new ActionResult<>();
        jsonData.setData(object);
        jsonData.setCode(200);
        jsonData.setMsg(msg);
        return jsonData;
    }

    /* ============== fail ============ */

    public static <T> ActionResult<T> fail(Integer code, String message) {
        ActionResult<T> jsonData = new ActionResult<>();
        jsonData.setCode(code);
        jsonData.setMsg(message);
        return jsonData;
    }

    public static ActionResult<String> fail(String msg, String data) {
        ActionResult<String> jsonData = new ActionResult<>();
        jsonData.setMsg(msg);
        jsonData.setData(data);
        return jsonData;
    }

    public static <T> ActionResult<T> fail(String msg) {
        ActionResult<T> jsonData = new ActionResult<>();
        jsonData.setMsg(msg);
        jsonData.setCode(400);
        return jsonData;
    }

    /* ============== page ============ */

    public static <T> ActionResult<PageListVO<T>> page(List<T> list, PaginationVO pagination) {
        ActionResult<PageListVO<T>> jsonData = new ActionResult<>();
        PageListVO<T> vo = new PageListVO<>();
        vo.setList(list);
        vo.setPagination(pagination);
        jsonData.setData(vo);
        jsonData.setCode(200);
        jsonData.setMsg(MsgCode.SU000.get());
        return jsonData;
    }

//    public static <T> ActionResult<DataInterfacePageListVO<T>> page(List<T> list, PaginationVO pagination,
//        String dataProcessing) {
//        ActionResult<DataInterfacePageListVO<T>> jsonData = new ActionResult<>();
//        DataInterfacePageListVO<T> vo = new DataInterfacePageListVO<>();
//        vo.setList(list);
//        vo.setPagination(pagination);
//        vo.setDataProcessing(dataProcessing);
//        jsonData.setCode(200);
//        jsonData.setData(vo);
//        jsonData.setMsg(MsgCode.SU000.get());
//        return jsonData;
//    }

}
