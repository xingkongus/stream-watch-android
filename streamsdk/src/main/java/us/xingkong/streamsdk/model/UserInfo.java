package us.xingkong.streamsdk.model;

/**
 * Created by SeaLynn0 on 2018/1/15.
 *
 * 返回的用户信息的模型
 */

public class UserInfo {

    private String username;

    private String nickname;

    UserInfo(String username, String nickname){
        this.username = username;
        this.nickname = nickname;
    }

    public UserInfo() {
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }
}
