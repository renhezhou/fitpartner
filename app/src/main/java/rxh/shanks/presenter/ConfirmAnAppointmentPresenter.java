package rxh.shanks.presenter;

import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.List;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.ConfirmAnAppointmentActivityCodeEntity;
import rxh.shanks.entity.ConfirmAnAppointmentListCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatTime;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CheckToView;
import rxh.shanks.view.ConfirmAnAppointmentView;

/**
 * Created by Administrator on 2016/8/11.
 */
public class ConfirmAnAppointmentPresenter {


    private GetInfo getInfo;
    private ConfirmAnAppointmentView confirmAnAppointmentView;
    ConfirmAnAppointmentListCodeEntity confirmAnAppointmentListCodeEntity = new ConfirmAnAppointmentListCodeEntity();

    public ConfirmAnAppointmentPresenter(ConfirmAnAppointmentView confirmAnAppointmentView) {
        getInfo = new Is_Networking();
        this.confirmAnAppointmentView = confirmAnAppointmentView;
    }

    public void getTeamLessonFreeTime(String date) {
//        if (checkdata(date)) {
//            return;
//        }
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "getTeamLessonFreeTime"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("lessonID", MyApplication.lessonID);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("planDate", date);

        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                confirmAnAppointmentListCodeEntity = JsonUtils.getTeamLessonFreeTime(result);
                if (confirmAnAppointmentListCodeEntity.getCode().equals("0")) {
                    //下一步操作
                    if (confirmAnAppointmentListCodeEntity.getResult().get(0).getIsEmpty().equals("0")) {
                        confirmAnAppointmentView.getTeamLessonFreeTime(confirmAnAppointmentListCodeEntity.getResult().get(0).getData());
                    }

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


    public void orderTeamLesson(String lessonID, String coachID, List<String> planID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "orderTeamLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("lessonID", lessonID);
        params.addBodyParameter("coachID", coachID);
        for (String planid : planID) {
            params.addBodyParameter("planID[]", planid);
        }
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                ConfirmAnAppointmentActivityCodeEntity confirmAnAppointmentActivityCodeEntity = new ConfirmAnAppointmentActivityCodeEntity();
                confirmAnAppointmentActivityCodeEntity = JsonUtils.orderTeamLesson(result);
                if (confirmAnAppointmentActivityCodeEntity.getCode().equals("0")) {
                    confirmAnAppointmentView.success();
                } else {
                    confirmAnAppointmentView.toast(confirmAnAppointmentActivityCodeEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                Log.e("...", ex.getMessage());
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    public boolean checkdata(String date) {
        boolean flag = false;
        if (confirmAnAppointmentListCodeEntity.getResult() != null) {
            for (int i = 0; i < confirmAnAppointmentListCodeEntity.getResult().size(); i++) {
                if (confirmAnAppointmentListCodeEntity.getResult().get(i).getDate().equals(CreatTime.creatfragment())) {
                    confirmAnAppointmentView.getTeamLessonFreeTime(confirmAnAppointmentListCodeEntity.getResult().get(i).getData());
                    flag = false;
                } else {
                    flag = true;
                }
            }
        }
        return flag;
    }

}
