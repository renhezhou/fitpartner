package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.entity.FitnessCalendarCodeEntity;
import rxh.shanks.entity.FitnessCalendarEntity;
import rxh.shanks.entity.FitnessCalendarTimeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CheckUtils;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.FitnessCalendarView;

/**
 * Created by Administrator on 2016/8/9.
 */
public class FitnessCalendarPresenter {

    private GetInfo getInfo;
    private FitnessCalendarView fitnessCalendarView;
    List<FitnessCalendarTimeEntity> fitnessCalendarTimeEntityList = new ArrayList<>();

    public FitnessCalendarPresenter(FitnessCalendarView fitnessCalendarView) {
        getInfo = new Is_Networking();
        this.fitnessCalendarView = fitnessCalendarView;
    }

    public void getFitCalender() {
        RequestParams params = new RequestParams(CreatUrl.creaturl("checkrecord", "getFitCalender"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("userID", MyApplication.userID);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                FitnessCalendarCodeEntity fitnessCalendarCodeEntity = new FitnessCalendarCodeEntity();
                fitnessCalendarCodeEntity = JsonUtils.getFitCalender(result);
                if (fitnessCalendarCodeEntity.getCode().equals("0")) {
                    fitnessCalendarTimeEntityList = fitnessCalendarCodeEntity.getResult();
                    //chonsedata(CheckUtils.getdate());
                    fitnessCalendarView.getFitCalenders(fitnessCalendarTimeEntityList);
                } else {
                    fitnessCalendarView.onFinished(fitnessCalendarCodeEntity.getError());
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
