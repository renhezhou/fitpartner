package rxh.shanks.presenter;

import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.ViewAppointmentCodeEntity;
import rxh.shanks.entity.ViewAppointmentState;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CheckToView;
import rxh.shanks.view.ViewAppointmentView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class ViewAppointmentPresenter {

    private GetInfo getInfo;
    private ViewAppointmentView viewAppointmentView;

    public ViewAppointmentPresenter(ViewAppointmentView viewAppointmentView) {
        getInfo = new Is_Networking();
        this.viewAppointmentView = viewAppointmentView;
    }

    public void getOrderLesson(String lessonID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "getOrderLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("lessonID", lessonID);
        params.addBodyParameter("userID", MyApplication.userID);
        viewAppointmentView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                viewAppointmentView.hide();
                ViewAppointmentCodeEntity viewAppointmentCodeEntity = new ViewAppointmentCodeEntity();
                viewAppointmentCodeEntity = JsonUtils.getOrderLesson(result);
                if (viewAppointmentCodeEntity.getCode().equals("0")) {
                    viewAppointmentView.getOrderLesson(viewAppointmentCodeEntity.getResult());
                }
            }

            @Override
            public void onError(Throwable ex) {
                viewAppointmentView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                viewAppointmentView.hide();
            }

            @Override
            public void onFinished() {
                viewAppointmentView.hide();
            }
        });
    }


    //确认代约
    public void confirmOrderLesson(String appointmentID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "confirmOrderLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("appointmentID", appointmentID);
        viewAppointmentView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                viewAppointmentView.hide();
                ViewAppointmentState viewAppointmentState = new ViewAppointmentState();
                viewAppointmentState = JsonUtils.viewAppointmentState(result);
                if (viewAppointmentState.getCode().equals("0")) {
                    //操作成功
                    viewAppointmentView.toast("操作已成功");
                    viewAppointmentView.success();
                }
            }

            @Override
            public void onError(Throwable ex) {
                viewAppointmentView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                viewAppointmentView.hide();
            }

            @Override
            public void onFinished() {
                viewAppointmentView.hide();
            }
        });
    }


    //取消约课
    public void cancelBespokeLesson(String appointmentID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "cancelBespokeLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("appointmentID", appointmentID);
        viewAppointmentView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                viewAppointmentView.hide();
                ViewAppointmentState viewAppointmentState = new ViewAppointmentState();
                viewAppointmentState = JsonUtils.viewAppointmentState(result);
                if (viewAppointmentState.getCode().equals("0")) {
                    //操作成功
                    viewAppointmentView.toast("操作已成功");
                    viewAppointmentView.success();
                }else {
                    viewAppointmentView.toast(viewAppointmentState.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                viewAppointmentView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                viewAppointmentView.hide();
            }

            @Override
            public void onFinished() {
                viewAppointmentView.hide();
            }
        });
    }
}
