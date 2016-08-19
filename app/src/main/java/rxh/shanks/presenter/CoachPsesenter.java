package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CoachCodeEntity;
import rxh.shanks.entity.CourseDetailsCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CoachView;
import rxh.shanks.view.CourseDetailsView;

/**
 * Created by Administrator on 2016/8/9.
 */
public class CoachPsesenter {

    private GetInfo getInfo;
    private CoachView coachView;

    public CoachPsesenter(CoachView coachView) {
        getInfo = new Is_Networking();
        this.coachView = coachView;
    }

    public void getCoach() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("coach", "GetCoach"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                CoachCodeEntity coachCodeEntity = new CoachCodeEntity();
                coachCodeEntity = JsonUtils.getCoach(result);
                if (coachCodeEntity.getCode().equals("0")) {
                    coachView.getCoach(coachCodeEntity.getResult());
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
