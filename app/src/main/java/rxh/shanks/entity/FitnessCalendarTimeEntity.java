package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class FitnessCalendarTimeEntity {

    private String noteTime;
    private List<FitnessCalendarEntity> noteDate;
    private List<FitnessCalendarLessonEntity> lesson;

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    public List<FitnessCalendarEntity> getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(List<FitnessCalendarEntity> noteDate) {
        this.noteDate = noteDate;
    }

    public List<FitnessCalendarLessonEntity> getLesson() {
        return lesson;
    }

    public void setLesson(List<FitnessCalendarLessonEntity> lesson) {
        this.lesson = lesson;
    }
}
