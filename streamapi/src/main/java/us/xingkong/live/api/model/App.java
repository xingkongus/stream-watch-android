package us.xingkong.live.api.model;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by 饶翰新 on 2017/12/20.
 * <p>
 * App模型
 */

public class App {

    public int id;

    public String appname;

    public String title;

    public String maintext;

    public String token;

    public App() {
        appname = title = maintext = token = "";
    }

    /**
     * 通过json对象初始化本对象
     *
     * @param json
     */
    public App(JSONObject json) {
        Field[] fileds = this.getClass().getFields();
        for (Field filed : fileds) {
            filed.setAccessible(true);
            try {
                filed.set(this, json.get(filed.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public int getId() {
        return id;
    }

    public String getAppname() {
        return appname;
    }

    public String getMaintext() {
        return maintext;
    }

    public String getTitle() {
        return title;
    }

    public String getToken() {
        return token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
