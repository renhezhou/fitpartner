package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CoachDetailsCodeEntity;
import rxh.shanks.entity.ViewFreeCoursesCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CoachDetailsView;
import rxh.shanks.view.ViewFreeCoursesActivityView;

/**
 * Created by Administrator on 2016/8/16.
 */
public class ViewFreeCoursesActivityPresenter {


    private GetInfo getInfo;
    private ViewFreeCoursesActivityView viewFreeCoursesActivityView;

    public ViewFreeCoursesActivityPresenter(ViewFreeCoursesActivityView viewFreeCoursesActivityView) {
        getInfo = new Is_Networking();
        this.viewFreeCoursesActivityView = viewFreeCoursesActivityView;
    }


    public void getBortherClubShop() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("gym", "getBortherClubShop"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                ViewFreeCoursesCodeEntity viewFreeCoursesCodeEntity = new ViewFreeCoursesCodeEntity();
                viewFreeCoursesCodeEntity = JsonUtils.getBortherClubShop(result);
                if (viewFreeCoursesCodeEntity.getCode().equals("0")) {
                    viewFreeCoursesActivityView.getBortherClubShop(viewFreeCoursesCodeEntity.getResult());
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
