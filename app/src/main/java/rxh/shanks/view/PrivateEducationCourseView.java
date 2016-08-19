package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.PrivateEducationCourseGetHoldingTimeEntity;
import rxh.shanks.entity.PrivateEducationCourseGetUserHoldingTimeEntity;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface PrivateEducationCourseView {

    void getHoldingTime(List<PrivateEducationCourseGetHoldingTimeEntity> privateEducationCourseGetHoldingTimeEntityList);

    void getUserHoldingTime(List<PrivateEducationCourseGetUserHoldingTimeEntity> privateEducationCourseGetUserHoldingTimeEntityList);

    void bespokeLesson();

    void toast(String msg);

}
