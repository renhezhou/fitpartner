package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.TestRecordCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CheckToView;
import rxh.shanks.view.TestRecordView;

/**
 * Created by Administrator on 2016/8/9.
 */
public class TestRecordPresenter {

    private GetInfo getInfo;
    private TestRecordView testRecordView;

    public TestRecordPresenter(TestRecordView testRecordView) {
        getInfo = new Is_Networking();
        this.testRecordView = testRecordView;
    }

    public void getBodyTestRecord() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("bodyrecord", "getBodyTestRecord"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        testRecordView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                testRecordView.hide();
                TestRecordCodeEntity testRecordCodeEntity = new TestRecordCodeEntity();
                testRecordCodeEntity = JsonUtils.getBodyTestRecord(result);
                if (testRecordCodeEntity.getCode().equals("0")) {
                    testRecordView.getBodyTestRecord(testRecordCodeEntity.getResult());
                }
            }

            @Override
            public void onError(Throwable ex) {
                testRecordView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                testRecordView.hide();
            }

            @Override
            public void onFinished() {
                testRecordView.hide();
            }
        });
    }

}
