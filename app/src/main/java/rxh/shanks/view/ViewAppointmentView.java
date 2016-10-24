package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.ViewAppointmentEntity;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface ViewAppointmentView {

    void show(int flag);

    void hide(int flag);

    void getOrderLesson(List<ViewAppointmentEntity> viewAppointmentEntityList);

    void toast(String msg);

    void success();

}
