package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.ConfirmAnAppointmentActivityCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CheckToView;
import rxh.shanks.view.ConfirmAnAppointmentActivityView;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ConfirmAnAppointmentActivityPresenter {

    private GetInfo getInfo;
    private ConfirmAnAppointmentActivityView confirmAnAppointmentActivityView;

    public ConfirmAnAppointmentActivityPresenter(ConfirmAnAppointmentActivityView confirmAnAppointmentActivityView) {
        getInfo = new Is_Networking();
        this.confirmAnAppointmentActivityView = confirmAnAppointmentActivityView;
    }

    public void orderTeamLesson(String lessonID, String coachID, List<String> planID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "orderTeamLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("lessonID", lessonID);
        params.addBodyParameter("coachID", coachID);
        params.addBodyParameter("planID", planID.toString());
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                ConfirmAnAppointmentActivityCodeEntity confirmAnAppointmentActivityCodeEntity = new ConfirmAnAppointmentActivityCodeEntity();
                confirmAnAppointmentActivityCodeEntity = JsonUtils.orderTeamLesson(result);
                if (confirmAnAppointmentActivityCodeEntity.getCode().equals("0")) {
                    confirmAnAppointmentActivityView.success();
                } else {
                    confirmAnAppointmentActivityView.toast(confirmAnAppointmentActivityCodeEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {

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
