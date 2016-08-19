package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.entity.SystemCodeEntity;
import rxh.shanks.entity.SystemDelCodeEntity;
import rxh.shanks.entity.SystemLVEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.SystemNextView;

/**
 * Created by Administrator on 2016/8/19.
 */
public class SystemNextPresenter {

    private GetInfo getInfo;
    private SystemNextView systemNextView;

    public SystemNextPresenter(SystemNextView systemNextView) {
        getInfo = new Is_Networking();
        this.systemNextView = systemNextView;
    }

    public void getMsg(final String type) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("jpush", "getMsg"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("alias", MyApplication.userID);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                SystemCodeEntity systemCodeEntity = new SystemCodeEntity();
                systemCodeEntity = JsonUtils.getMsg(result);
                if (systemCodeEntity.getCode().equals("0")) {
                    List<SystemLVEntity> systemLVEntityList = new ArrayList<SystemLVEntity>();
                    //根据type获取对应的type信息
                    for (int i = 0; i < systemCodeEntity.getResult().size(); i++) {
                        if (systemCodeEntity.getResult().get(i).getType().equals(type)) {
                            SystemLVEntity systemLVEntity = new SystemLVEntity();
                            systemLVEntity.setTime(systemCodeEntity.getResult().get(i).getSendTime());
                            systemLVEntity.setContent(systemCodeEntity.getResult().get(i).getContent());
                            systemLVEntityList.add(systemLVEntity);
                        }
                    }
                    systemNextView.getMsg(systemLVEntityList);
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


    public void delMsg(String type) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("jpush", "delMsg"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("alias", MyApplication.userID);
        params.addBodyParameter("type", type);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                SystemDelCodeEntity systemDelCodeEntity = new SystemDelCodeEntity();
                systemDelCodeEntity = JsonUtils.delMsg(result);
                if (systemDelCodeEntity.getCode().equals("0")) {
                    systemNextView.delMsg("删除成功");
                    systemNextView.delSuccess();
                } else {
                    systemNextView.delMsg(systemDelCodeEntity.getError());
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
