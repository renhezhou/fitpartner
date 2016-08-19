package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.FeedbackCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CheckToView;
import rxh.shanks.view.FeedbackView;

/**
 * Created by Administrator on 2016/8/9.
 */
public class FeedbackPresenter {

    private GetInfo getInfo;
    private FeedbackView feedbackView;

    public FeedbackPresenter(FeedbackView feedbackView) {
        getInfo = new Is_Networking();
        this.feedbackView = feedbackView;
    }

    public void submitOpinion(String contact, String content) {
        if (contact.length() == 0) {
            feedbackView.check("联系方式不能为空");
            return;
        }
        if (content.length() == 0) {
            feedbackView.check("你还未填写建议内容");
            return;
        }
        RequestParams params = new RequestParams(CreatUrl.creaturl("opinion", "submitOpinion"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("contact", contact);
        params.addBodyParameter("content", content);
        feedbackView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                feedbackView.hide();
                FeedbackCodeEntity feedbackCodeEntity = new FeedbackCodeEntity();
                feedbackCodeEntity = JsonUtils.submitOpinion(result);
                if (feedbackCodeEntity.getCode().equals("0")) {
                    feedbackView.response("");
                } else {
                    feedbackView.check(feedbackCodeEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                feedbackView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                feedbackView.hide();
            }

            @Override
            public void onFinished() {
                feedbackView.hide();
            }
        });
    }

}
