package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/8/11.
 */
public class ConfirmAnAppointmentEventBusEntity {

    private String lessonID;
    private String coachID;

    public ConfirmAnAppointmentEventBusEntity(String lessonID, String coachID) {
    }

    public String getLessonID() {
        return lessonID;
    }

    public String getCoachID() {
        return coachID;
    }
}
