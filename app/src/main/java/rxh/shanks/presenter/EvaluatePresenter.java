package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.EvaluateCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CheckToView;
import rxh.shanks.view.EvaluateView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class EvaluatePresenter {

    private GetInfo getInfo;
    private EvaluateView evaluateView;

    public EvaluatePresenter(EvaluateView evaluateView) {
        getInfo = new Is_Networking();
        this.evaluateView = evaluateView;
    }

    public void makeEvaluate(String coachID, String appointmentID, String lessonScore, String coachScore, String evaluate) {

        if (lessonScore==null) {
            evaluateView.toast("课程评分不能为空");
            return;
        }
        if (coachScore==null) {
            evaluateView.toast("教练评分不能为空");
            return;
        }
        if (evaluate.length() == 0) {
            evaluateView.toast("评价内容不能为空");
            return;
        }

        RequestParams params = new RequestParams(CreatUrl.creaturl("evaluate", "makeEvaluate"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        params.addBodyParameter("coachID", coachID);
        params.addBodyParameter("AppointmentID", appointmentID);
        params.addBodyParameter("lessonScore", lessonScore);
        params.addBodyParameter("coachScore", coachScore);
        params.addBodyParameter("evaluate", evaluate);
        evaluateView.show();
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                evaluateView.hide();
                EvaluateCodeEntity evaluateCodeEntity = new EvaluateCodeEntity();
                evaluateCodeEntity = JsonUtils.makeEvaluate(result);
                if (evaluateCodeEntity.getCode().equals("0")) {
                    evaluateView.toast("评价上传成功");
                    evaluateView.success();
                } else {
                    evaluateView.toast(evaluateCodeEntity.getError());
                }
            }

            @Override
            public void onError(Throwable ex) {
                evaluateView.hide();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
                evaluateView.hide();
            }

            @Override
            public void onFinished() {
                evaluateView.hide();
            }
        });
    }
}
