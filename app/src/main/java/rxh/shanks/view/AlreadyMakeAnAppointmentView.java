package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.AlreadyMakeAnAppointmentEntity;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface AlreadyMakeAnAppointmentView {

    void show();

    void hide();

    void toast(String msg);

    void getMyOrderPrivateLesson(List<AlreadyMakeAnAppointmentEntity> alreadyMakeAnAppointmentEntityList);

}
