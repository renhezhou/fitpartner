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
    private CoachView view;

    public CoachPsesenter(CoachView view) {
        getInfo = new Is_Networking();
        this.view = view;
    }

    public void getCoach() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("coach", "GetCoach"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        view.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                view.hide();
                CoachCodeEntity coachCodeEntity = new CoachCodeEntity();
                coachCodeEntity = JsonUtils.getCoach(result);
                if (coachCodeEntity.getCode().equals("0")) {
                    view.getCoach(coachCodeEntity.getResult());
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
