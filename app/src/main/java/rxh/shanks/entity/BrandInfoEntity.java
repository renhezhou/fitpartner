package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class BrandInfoEntity {

    private String clubIntroduce;
    private String clubName;
    private List<String> imageArrayTrans;
    private String nowTime;
    private String onlineNum;

    public String getClubIntroduce() {
        return clubIntroduce;
    }

    public void setClubIntroduce(String clubIntroduce) {
        this.clubIntroduce = clubIntroduce;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public List<String> getImageArrayTrans() {
        return imageArrayTrans;
    }

    public void setImageArrayTrans(List<String> imageArrayTrans) {
        this.imageArrayTrans = imageArrayTrans;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(String onlineNum) {
        this.onlineNum = onlineNum;
    }
}
