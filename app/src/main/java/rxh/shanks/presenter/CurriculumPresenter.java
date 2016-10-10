package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.BrandGymPaidCodeEntity;
import rxh.shanks.entity.BrandInfoCodeEntity;
import rxh.shanks.entity.ConsultantCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.GetBrandGymPaidView;
import rxh.shanks.view.GetBrandInfoView;
import rxh.shanks.view.GetConsultantView;

/**
 * Created by Administrator on 2016/8/8.
 */
public class CurriculumPresenter {

    private GetInfo getInfo;
    private GetBrandGymPaidView getBrandGymPaidView;
    private GetConsultantView getConsultantView;
    private GetBrandInfoView getBrandInfoView;

    public CurriculumPresenter(GetBrandGymPaidView getBrandGymPaidView, GetConsultantView getConsultantView, GetBrandInfoView getBrandInfoView) {
        getInfo = new Is_Networking();
        this.getBrandGymPaidView = getBrandGymPaidView;
        this.getConsultantView = getConsultantView;
        this.getBrandInfoView = getBrandInfoView;
    }

    //获取该品牌下会员拥有的场馆
    public void getBrandGymPaid() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("gym", "getBrandGymPaid"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        params.addBodyParameter("userID", MyApplication.userID);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                BrandGymPaidCodeEntity brandGymPaidCodeEntity = new BrandGymPaidCodeEntity();
                brandGymPaidCodeEntity = JsonUtils.getBrandGymPaid(result);
                if (brandGymPaidCodeEntity.getCode().equals("0")) {
                    getBrandGymPaidView.getBrandGymPaid(brandGymPaidCodeEntity.getResult());
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

    //获取我的会稽顾问
    public void getConsultant() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("sales", "getConsultant"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        params.addBodyParameter("userID", MyApplication.userID);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                ConsultantCodeEntity consultantCodeEntity = new ConsultantCodeEntity();
                consultantCodeEntity = JsonUtils.getConsultant(result);
                if (consultantCodeEntity.getCode().equals("0")) {
                    getConsultantView.getConsultant(consultantCodeEntity.getResult().get(0));
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

    //获取场馆信息
    public void getBrandInfo() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("gym", "getBrandInfo"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", MyApplication.currentClubID);
        getBrandInfoView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                getBrandInfoView.hide();
                BrandInfoCodeEntity brandInfoCodeEntity = new BrandInfoCodeEntity();
                brandInfoCodeEntity = JsonUtils.getBrandInfo(result);
                if (brandInfoCodeEntity.getCode().equals("0")) {
                    getBrandInfoView.getBrandInfo(brandInfoCodeEntity.getResult());
                }

            }

            @Override
            public void onError(Throwable ex) {
                getBrandInfoView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                getBrandInfoView.hide();
            }

            @Override
            public void onFinished() {
                getBrandInfoView.hide();
            }
        });
    }

}
