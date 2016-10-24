package rxh.shanks.presenter;

import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.NotMakeAnAppointmentCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.NotMakeAnAppointmentView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class NotMakeAnAppointmentPresenter {

    private GetInfo getInfo;
    private NotMakeAnAppointmentView view;

    public NotMakeAnAppointmentPresenter(NotMakeAnAppointmentView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void getMyUnorderPrivateLesson(String coachID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "getMyUnorderPrivateLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("coachID", coachID);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                NotMakeAnAppointmentCodeEntity notMakeAnAppointmentCodeEntity = new NotMakeAnAppointmentCodeEntity();
                notMakeAnAppointmentCodeEntity = JsonUtils.getMyUnorderPrivateLesson(result);
                if (notMakeAnAppointmentCodeEntity.getCode().equals("0")) {
                    view.getMyUnorderPrivateLesson(notMakeAnAppointmentCodeEntity.getResult());
                } else {
                    view.toast(notMakeAnAppointmentCodeEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                view.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    public void getMyUnorderTeamLesson(String coachID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "getMyUnorderTeamLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("coachID", coachID);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                NotMakeAnAppointmentCodeEntity notMakeAnAppointmentCodeEntity = new NotMakeAnAppointmentCodeEntity();
                notMakeAnAppointmentCodeEntity = JsonUtils.getMyUnorderPrivateLesson(result);
                if (notMakeAnAppointmentCodeEntity.getCode().equals("0")) {
                    view.getMyUnorderPrivateLesson(notMakeAnAppointmentCodeEntity.getResult());
                } else {
                    view.toast(notMakeAnAppointmentCodeEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                view.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
