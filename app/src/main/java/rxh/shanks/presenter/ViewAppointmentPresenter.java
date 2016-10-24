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
    private ViewAppointmentView view;

    public ViewAppointmentPresenter(ViewAppointmentView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void getOrderLesson(String lessonID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "getOrderLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("lessonID", lessonID);
        params.addBodyParameter("userID", MyApplication.userID);
        view.show(0);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide(0);
                ViewAppointmentCodeEntity viewAppointmentCodeEntity = new ViewAppointmentCodeEntity();
                viewAppointmentCodeEntity = JsonUtils.getOrderLesson(result);
                if (viewAppointmentCodeEntity.getCode().equals("0")) {
                    view.getOrderLesson(viewAppointmentCodeEntity.getResult());
                } else {
                    view.toast(viewAppointmentCodeEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                view.hide(0);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                view.hide(0);
            }

            @Override
            public void onFinished() {
                view.hide(0);
            }
        });
    }


    //确认代约
    public void confirmOrderLesson(String appointmentID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "confirmOrderLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("appointmentID", appointmentID);
        view.show(11);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide(11);
                ViewAppointmentState viewAppointmentState = new ViewAppointmentState();
                viewAppointmentState = JsonUtils.viewAppointmentState(result);
                if (viewAppointmentState.getCode().equals("0")) {
                    //操作成功
                    view.toast("操作已成功");
                    view.success();
                } else {
                    view.toast(viewAppointmentState.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                view.hide(11);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                view.hide(11);
            }

            @Override
            public void onFinished() {
                view.hide(11);
            }
        });
    }


    //取消约课
    public void cancelBespokeLesson(String appointmentID) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "cancelBespokeLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("appointmentID", appointmentID);
        view.show(10);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide(10);
                ViewAppointmentState viewAppointmentState = new ViewAppointmentState();
                viewAppointmentState = JsonUtils.viewAppointmentState(result);
                if (viewAppointmentState.getCode().equals("0")) {
                    //操作成功
                    view.toast("操作已成功");
                    view.success();
                } else {
                    view.toast(viewAppointmentState.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                view.hide(10);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                view.hide(10);
            }

            @Override
            public void onFinished() {
                view.hide(10);
            }
        });
    }
}
