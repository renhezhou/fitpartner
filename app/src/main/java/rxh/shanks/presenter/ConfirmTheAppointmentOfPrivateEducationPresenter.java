package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.ConfirmTheAppointmentOfPrivateEducationCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CheckToView;
import rxh.shanks.view.ConfirmTheAppointmentOfPrivateEducationView;

/**
 * Created by Administrator on 2016/11/15.
 */
public class ConfirmTheAppointmentOfPrivateEducationPresenter {

    private GetInfo getInfo;
    private ConfirmTheAppointmentOfPrivateEducationView view;

    public ConfirmTheAppointmentOfPrivateEducationPresenter(ConfirmTheAppointmentOfPrivateEducationView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void getlessoncoach(String lessonID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "getLessonCoach"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("lessonID", lessonID);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                ConfirmTheAppointmentOfPrivateEducationCodeEntity entity = new ConfirmTheAppointmentOfPrivateEducationCodeEntity();
                entity = JsonUtils.getlessoncoach(result);
                if (entity.getCode().equals("0")) {
                    view.getlessoncoach(entity.getResult());
                } else {
                    view.toast(entity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                view.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                view.hide();
            }

            @Override
            public void onFinished() {
                view.hide();
            }
        });
    }

}
