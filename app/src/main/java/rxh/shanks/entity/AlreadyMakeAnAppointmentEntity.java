package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class AlreadyMakeAnAppointmentEntity {

    private String address;
    private List<String> completeTime;
    private String isEvaluated;
    private String lessonID;
    private String lessonIntro;
    private String lessonName;
    private String orderCount;
    private List<String> replaceOrderLesson;
    private List<String> startTime;
    private String time;
    private String totalCount;
    private String unOrderCount;
    private AlreadyMakeAnAppointmentNextLessonEntity nextLesson;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(List<String> completeTime) {
        this.completeTime = completeTime;
    }

    public String getIsEvaluated() {
        return isEvaluated;
    }

    public void setIsEvaluated(String isEvaluated) {
        this.isEvaluated = isEvaluated;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public String getLessonIntro() {
        return lessonIntro;
    }

    public void setLessonIntro(String lessonIntro) {
        this.lessonIntro = lessonIntro;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public List<String> getReplaceOrderLesson() {
        return replaceOrderLesson;
    }

    public void setReplaceOrderLesson(List<String> replaceOrderLesson) {
        this.replaceOrderLesson = replaceOrderLesson;
    }

    public List<String> getStartTime() {
        return startTime;
    }

    public void setStartTime(List<String> startTime) {
        this.startTime = startTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getUnOrderCount() {
        return unOrderCount;
    }

    public void setUnOrderCount(String unOrderCount) {
        this.unOrderCount = unOrderCount;
    }

    public AlreadyMakeAnAppointmentNextLessonEntity getNextLesson() {
        return nextLesson;
    }

    public void setNextLesson(AlreadyMakeAnAppointmentNextLessonEntity nextLesson) {
        this.nextLesson = nextLesson;
    }
}
