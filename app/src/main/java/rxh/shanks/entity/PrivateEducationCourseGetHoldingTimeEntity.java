package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 * 预约私教课程，教练占用的时间
 */
public class PrivateEducationCourseGetHoldingTimeEntity {

    private String date;
    private List<String> holdTime;
    private String isRest;
    private String restTime;
    private String workTime;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(List<String> holdTime) {
        this.holdTime = holdTime;
    }

    public String getIsRest() {
        return isRest;
    }

    public void setIsRest(String isRest) {
        this.isRest = isRest;
    }

    public String getRestTime() {
        return restTime;
    }

    public void setRestTime(String restTime) {
        this.restTime = restTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }
}
