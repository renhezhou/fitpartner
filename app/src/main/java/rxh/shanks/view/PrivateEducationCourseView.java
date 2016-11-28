package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.DataSouceCoachEntity;
import rxh.shanks.entity.PrivateEducationCourseGetHoldingTimeEntity;
import rxh.shanks.entity.PrivateEducationCourseGetUserHoldingTimeEntity;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface PrivateEducationCourseView {

    void show(int flag);

    void hide(int flag);

    void getCoachTime(List<DataSouceCoachEntity> entities);

    void bespokeLesson();

    void toast(String msg);

}
