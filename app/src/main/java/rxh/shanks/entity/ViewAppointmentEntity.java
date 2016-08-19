package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/8/2.
 */
public class ViewAppointmentEntity {

    private String appointmentID;
    private String lessonData;
    private String lessonID;
    private String lessonName;
    private String lessonTime;
    private String state;

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getLessonData() {
        return lessonData;
    }

    public void setLessonData(String lessonData) {
        this.lessonData = lessonData;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(String lessonTime) {
        this.lessonTime = lessonTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
