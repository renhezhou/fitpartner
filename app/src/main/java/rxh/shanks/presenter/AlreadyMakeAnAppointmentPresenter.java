package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.AlreadyMakeAnAppointmentCodeEntity;
import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.AlreadyMakeAnAppointmentView;
import rxh.shanks.view.CheckToView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class AlreadyMakeAnAppointmentPresenter {


    private GetInfo getInfo;
    private AlreadyMakeAnAppointmentView view;

    public AlreadyMakeAnAppointmentPresenter(AlreadyMakeAnAppointmentView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void getMyOrderPrivateLesson(String coachID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "getMyOrderPrivateLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("coachID", coachID);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                AlreadyMakeAnAppointmentCodeEntity alreadyMakeAnAppointmentCodeEntity = new AlreadyMakeAnAppointmentCodeEntity();
                alreadyMakeAnAppointmentCodeEntity = JsonUtils.getMyOrderPrivateLesson(result);
                if (alreadyMakeAnAppointmentCodeEntity.getCode().equals("0")) {
                    view.getMyOrderPrivateLesson(alreadyMakeAnAppointmentCodeEntity.getResult());
                } else {
                    view.toast(alreadyMakeAnAppointmentCodeEntity.getError());
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

    public void getMyOrderTeamLesson(String coachID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "getMyOrderTeamLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("coachID", coachID);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                AlreadyMakeAnAppointmentCodeEntity alreadyMakeAnAppointmentCodeEntity = new AlreadyMakeAnAppointmentCodeEntity();
                alreadyMakeAnAppointmentCodeEntity = JsonUtils.getMyOrderPrivateLesson(result);
                if (alreadyMakeAnAppointmentCodeEntity.getCode().equals("0")) {
                    view.getMyOrderPrivateLesson(alreadyMakeAnAppointmentCodeEntity.getResult());
                } else {
                    view.toast(alreadyMakeAnAppointmentCodeEntity.getError());
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
