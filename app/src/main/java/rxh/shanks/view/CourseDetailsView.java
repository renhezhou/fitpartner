package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.CourseDetailsEntity;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface CourseDetailsView {

    void getFreeLesson(List<CourseDetailsEntity> courseDetailsEntityList);

}
