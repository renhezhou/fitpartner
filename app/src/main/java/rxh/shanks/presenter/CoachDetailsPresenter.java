package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CoachCodeEntity;
import rxh.shanks.entity.CoachDetailsCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CoachDetailsView;
import rxh.shanks.view.CoachView;

/**
 * Created by Administrator on 2016/8/9.
 */
public class CoachDetailsPresenter {

    private GetInfo getInfo;
    private CoachDetailsView view;

    public CoachDetailsPresenter(CoachDetailsView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    //获取私教课程
    public void getPrivateLesson() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "getPrivateLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        params.addBodyParameter("coachID", MyApplication.CoachID);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                CoachDetailsCodeEntity coachDetailsCodeEntity = new CoachDetailsCodeEntity();
                coachDetailsCodeEntity = JsonUtils.getPrivateLessongetTeamLesson(result);
                if (coachDetailsCodeEntity.getCode().equals("0")) {
                    view.getPrivateLessongetTeamLesson(coachDetailsCodeEntity.getResult());
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

    //获取团课
    public void getTeamLesson() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "getTeamLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        params.addBodyParameter("coachID", MyApplication.CoachID);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                CoachDetailsCodeEntity coachDetailsCodeEntity = new CoachDetailsCodeEntity();
                coachDetailsCodeEntity = JsonUtils.getPrivateLessongetTeamLesson(result);
                if (coachDetailsCodeEntity.getCode().equals("0")) {
                    view.getPrivateLessongetTeamLesson(coachDetailsCodeEntity.getResult());
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
