package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.NotMakeAnAppointmentEntity;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface NotMakeAnAppointmentView {

    void show();

    void hide();

    void toast(String msg);

    void getMyUnorderPrivateLesson(List<NotMakeAnAppointmentEntity> notMakeAnAppointmentEntityList);

}
