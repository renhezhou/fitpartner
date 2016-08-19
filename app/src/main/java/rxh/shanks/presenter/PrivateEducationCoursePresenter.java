package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.PrivateEducationCourseBespokeLessonEntity;
import rxh.shanks.entity.PrivateEducationCourseGetHoldingTimeCodeEntity;
import rxh.shanks.entity.PrivateEducationCourseGetUserHoldingTimeCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.PrivateEducationCourseView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class PrivateEducationCoursePresenter {

    private GetInfo getInfo;
    private PrivateEducationCourseView privateEducationCourseView;

    public PrivateEducationCoursePresenter(PrivateEducationCourseView privateEducationCourseView) {
        getInfo = new Is_Networking();
        this.privateEducationCourseView = privateEducationCourseView;
    }

    //获取教练占用的时间  ：日期 例：2016.08.04
    public void getHoldingTime(String coachID, String date) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("coach", "getHoldingTime"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("coachID", coachID);
        params.addBodyParameter("date", date);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                PrivateEducationCourseGetHoldingTimeCodeEntity privateEducationCourseGetHoldingTimeCodeEntity = new PrivateEducationCourseGetHoldingTimeCodeEntity();
                privateEducationCourseGetHoldingTimeCodeEntity = JsonUtils.getHoldingTime(result);
                if (privateEducationCourseGetHoldingTimeCodeEntity.getCode().equals("0")) {
                    privateEducationCourseView.getHoldingTime(privateEducationCourseGetHoldingTimeCodeEntity.getResult());
                } else {
                    privateEducationCourseView.toast(privateEducationCourseGetHoldingTimeCodeEntity.getError());
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


    //获取用户占用的时间  ：日期 例：2016.08.04
    public void getUserHoldingTime(String date) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "getUserHoldingTime"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("date", date);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                PrivateEducationCourseGetUserHoldingTimeCodeEntity privateEducationCourseGetUserHoldingTimeCodeEntity = new PrivateEducationCourseGetUserHoldingTimeCodeEntity();
                privateEducationCourseGetUserHoldingTimeCodeEntity = JsonUtils.getUserHoldingTime(result);
                if (privateEducationCourseGetUserHoldingTimeCodeEntity.getCode().equals("0")) {
                    privateEducationCourseView.getUserHoldingTime(privateEducationCourseGetUserHoldingTimeCodeEntity.getResult());
                } else {
                    privateEducationCourseView.toast(privateEducationCourseGetUserHoldingTimeCodeEntity.getError());
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

    //预约私教课
    public void bespokeLesson(String orderTime) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "bespokeLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("lessonID", MyApplication.lessonID);
        params.addBodyParameter("time", orderTime);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                PrivateEducationCourseBespokeLessonEntity privateEducationCourseBespokeLessonEntity = new PrivateEducationCourseBespokeLessonEntity();
                privateEducationCourseBespokeLessonEntity = JsonUtils.bespokeLesson(result);
                if (privateEducationCourseBespokeLessonEntity.getCode().equals("0")) {
                    privateEducationCourseView.bespokeLesson();
                } else {
                    privateEducationCourseView.toast(privateEducationCourseBespokeLessonEntity.getError());
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