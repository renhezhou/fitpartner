package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.ConfirmAnAppointmentEntity;

/**
 * Created by Administrator on 2016/8/11.
 */
public interface ConfirmAnAppointmentView {

    void show(int flag);

    void hide(int flag);

    void getTeamLessonFreeTime(List<ConfirmAnAppointmentEntity> data);

    void success();

    void toast(String msg);

}
