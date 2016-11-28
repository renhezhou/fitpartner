package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.DataSouceCoachCodeEntity;
import rxh.shanks.entity.DataSouceCoachEntity;
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
    private PrivateEducationCourseView view;

    public PrivateEducationCoursePresenter(PrivateEducationCourseView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    //日期 例：2016.08.04
    public void getCoachTime(String coachID, String date, String lessonTime) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "getCoachTime"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("coachID", coachID);
        params.addBodyParameter("lessonTime", lessonTime);
        params.addBodyParameter("date", date);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                DataSouceCoachCodeEntity entity = new DataSouceCoachCodeEntity();
                entity = JsonUtils.getCoachTime(result);
                if (entity.getCode().equals("0")) {
                    view.getCoachTime(entity.getResult());
                } else {
                    view.toast(entity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                view.toast(ex.getMessage());
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
    public void bespokeLesson(String lessonID, String orderTime) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "bespokeLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("coachID", MyApplication.CoachID);
        params.addBodyParameter("lessonID", lessonID);
        params.addBodyParameter("time", orderTime);
        view.show(3);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide(3);
                PrivateEducationCourseBespokeLessonEntity privateEducationCourseBespokeLessonEntity = new PrivateEducationCourseBespokeLessonEntity();
                privateEducationCourseBespokeLessonEntity = JsonUtils.bespokeLesson(result);
                if (privateEducationCourseBespokeLessonEntity.getCode().equals("0")) {
                    view.bespokeLesson();
                } else {
                    view.toast(privateEducationCourseBespokeLessonEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                view.hide(3);
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                view.hide(3);
            }

            @Override
            public void onFinished() {
                view.hide(3);
            }
        });

    }
}