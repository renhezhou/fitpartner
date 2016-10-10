package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.CoachEntity;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface CoachView {

    void show();

    void hide();

    void getCoach(List<CoachEntity> coachEntityList);

}
