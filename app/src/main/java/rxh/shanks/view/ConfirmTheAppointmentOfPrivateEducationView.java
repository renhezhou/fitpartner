package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.ConfirmTheAppointmentOfPrivateEducationEntity;

/**
 * Created by Administrator on 2016/11/15.
 */
public interface ConfirmTheAppointmentOfPrivateEducationView {

    void show();

    void hide();

    void toast(String msg);

    void error();

    void getlessoncoach(List<ConfirmTheAppointmentOfPrivateEducationEntity> entities);


}
