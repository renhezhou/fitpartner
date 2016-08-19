package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/8/12.
 */
public class DataSouceCoachEntity {

    private String time;
    private int flag;//0表示用户和教练都有空，1表示教练没空，2表示用户没空，3表示教练和用户都没空

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
