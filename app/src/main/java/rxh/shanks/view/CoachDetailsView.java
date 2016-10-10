package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.CoachDetailsEntity;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface CoachDetailsView {

    void show();

    void hide();

    void getPrivateLessongetTeamLesson(List<CoachDetailsEntity> coachDetailsEntityList);

}
