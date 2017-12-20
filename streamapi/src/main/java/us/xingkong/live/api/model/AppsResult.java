package us.xingkong.live.api.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 饶翰新 on 2017/12/20.
 * <p>
 * Apps结果模型
 */

public class AppsResult extends Result {

    public String src;

    public List<App> apps;

    public AppsResult() {
        super();
        src = "";
        apps = new ArrayList<App>();
    }

    public AppsResult(String Result) throws JSONException {
        this();
        JSONObject json = new JSONObject(Result);
        setStatus(json.getInt("status"));

        //setMsg(json.getString("msg"));
        setSrc(json.getString("src"));
        JSONArray arr = json.getJSONArray("apps");
        for (int i = 0; i < arr.length(); i++) {
            apps.add(new App(arr.getJSONObject(i)));
        }
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrc() {
        return src;
    }

    public void setAppst(List<App> result) {
        this.apps = result;
    }

    public List<App> getApps() {
        return apps;
    }
}
