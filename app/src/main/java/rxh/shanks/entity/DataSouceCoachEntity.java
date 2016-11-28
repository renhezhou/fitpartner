package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/8/12.
 */
public class DataSouceCoachEntity {

    private String time;
    private int flag;//0表示可以预约，1表示用户时间被占用，2表示时间被团课，免费课占用

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
