package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class PrivateEducationCourseGetUserHoldingTimeEntity {

    private String date;
    private List<String> holdTime;

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
}
