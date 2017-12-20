package us.xingkong.live.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 饶翰新 on 2017/12/20.
 * <p>
 * 通用结果模型
 */

public class Result {

    public int status;

    public String msg;

    public Result() {
        msg = "";
    }

    public Result(String Result) throws JSONException {
        JSONObject json = new JSONObject(Result);
        setStatus(json.getInt("status"));
        setMsg(json.getString("msg"));
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }
}
