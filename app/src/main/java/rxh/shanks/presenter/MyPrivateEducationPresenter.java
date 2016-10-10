package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.MyPrivateEducationHeadCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CheckToView;
import rxh.shanks.view.MyPrivateEducationView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class MyPrivateEducationPresenter {

    private GetInfo getInfo;
    private MyPrivateEducationView myPrivateEducationView;

    public MyPrivateEducationPresenter(MyPrivateEducationView myPrivateEducationView) {
        getInfo = new Is_Networking();
        this.myPrivateEducationView = myPrivateEducationView;
    }

    public void getMyPrivateCoach() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("coach", "getMyPrivateCoach"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        myPrivateEducationView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                myPrivateEducationView.hide();
                MyPrivateEducationHeadCodeEntity myPrivateEducationHeadCodeEntity = new MyPrivateEducationHeadCodeEntity();
                myPrivateEducationHeadCodeEntity = JsonUtils.getMyPrivateCoach(result);
                if (myPrivateEducationHeadCodeEntity.getCode().equals("0")) {
                    myPrivateEducationView.getMyPrivateCoach(myPrivateEducationHeadCodeEntity.getResult());
                }
            }

            @Override
            public void onError(Throwable ex) {
                myPrivateEducationView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                myPrivateEducationView.hide();
            }

            @Override
            public void onFinished() {
                myPrivateEducationView.hide();
            }
        });

    }

    public void getMyTeamCoach() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("coach", "getMyTeamCoach"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        myPrivateEducationView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                myPrivateEducationView.hide();
                MyPrivateEducationHeadCodeEntity myPrivateEducationHeadCodeEntity = new MyPrivateEducationHeadCodeEntity();
                myPrivateEducationHeadCodeEntity = JsonUtils.getMyPrivateCoach(result);
                if (myPrivateEducationHeadCodeEntity.getCode().equals("0")) {
                    myPrivateEducationView.getMyPrivateCoach(myPrivateEducationHeadCodeEntity.getResult());
                }
            }

            @Override
            public void onError(Throwable ex) {
                myPrivateEducationView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                myPrivateEducationView.hide();
            }

            @Override
            public void onFinished() {
                myPrivateEducationView.hide();
            }
        });

    }

}