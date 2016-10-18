package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.SystemCodeEntity;
import rxh.shanks.entity.SystemDelCodeEntity;
import rxh.shanks.entity.SystemLVEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CheckToView;
import rxh.shanks.view.SystemView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class SystemPresenter {

    private GetInfo getInfo;
    private SystemView systemView;

    public SystemPresenter(SystemView systemView) {
        getInfo = new Is_Networking();
        this.systemView = systemView;
    }

    public void getMsg() {
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
                    //获取第一个上课提醒
                    for (int i = 0; i < systemCodeEntity.getResult().size(); i++) {
                        if (systemCodeEntity.getResult().get(i).getType().equals("RemindLessonToUser")) {
                            SystemLVEntity systemLVEntity = new SystemLVEntity();
                            systemLVEntity.setType("上课提醒");
                            systemLVEntity.setTime(systemCodeEntity.getResult().get(i).getSendTime());
                            systemLVEntity.setContent(systemCodeEntity.getResult().get(i).getContent());
                            systemLVEntityList.add(systemLVEntity);
                            break;
                        }
                    }
                    //获取第一个教练代约团课
                    for (int i = 0; i < systemCodeEntity.getResult().size(); i++) {
                        if (systemCodeEntity.getResult().get(i).getType().equals("ReplaceGroupClassToUser")) {
                            SystemLVEntity systemLVEntity = new SystemLVEntity();
                            systemLVEntity.setType("教练代约团课");
                            systemLVEntity.setTime(systemCodeEntity.getResult().get(i).getSendTime());
                            systemLVEntity.setContent(systemCodeEntity.getResult().get(i).getContent());
                            systemLVEntityList.add(systemLVEntity);
                            break;
                        }
                    }
                    //获取第一个教练代约私教课
                    for (int i = 0; i < systemCodeEntity.getResult().size(); i++) {
                        if (systemCodeEntity.getResult().get(i).getType().equals("ReplaceOrderLessonToUser")) {
                            SystemLVEntity systemLVEntity = new SystemLVEntity();
                            systemLVEntity.setType("教练代约私教课");
                            systemLVEntity.setTime(systemCodeEntity.getResult().get(i).getSendTime());
                            systemLVEntity.setContent(systemCodeEntity.getResult().get(i).getContent());
                            systemLVEntityList.add(systemLVEntity);
                            break;
                        }
                    }
                    //获取第一个会员卡即将到期
                    for (int i = 0; i < systemCodeEntity.getResult().size(); i++) {
                        if (systemCodeEntity.getResult().get(i).getType().equals("CardCloseExpiredToUser")) {
                            SystemLVEntity systemLVEntity = new SystemLVEntity();
                            systemLVEntity.setType("会员卡即将到期");
                            systemLVEntity.setTime(systemCodeEntity.getResult().get(i).getSendTime());
                            systemLVEntity.setContent(systemCodeEntity.getResult().get(i).getContent());
                            systemLVEntityList.add(systemLVEntity);
                            break;
                        }
                    }
                    //获取第一个新增优惠券
                    for (int i = 0; i < systemCodeEntity.getResult().size(); i++) {
                        if (systemCodeEntity.getResult().get(i).getType().equals("SendCouponToUser")) {
                            SystemLVEntity systemLVEntity = new SystemLVEntity();
                            systemLVEntity.setType("新增优惠券");
                            systemLVEntity.setTime(systemCodeEntity.getResult().get(i).getSendTime());
                            systemLVEntity.setContent(systemCodeEntity.getResult().get(i).getContent());
                            systemLVEntityList.add(systemLVEntity);
                            break;
                        }
                    }
                    //获取第一个课程扣除提醒
                    for (int i = 0; i < systemCodeEntity.getResult().size(); i++) {
                        if (systemCodeEntity.getResult().get(i).getType().equals("DeductionLessonToUser")) {
                            SystemLVEntity systemLVEntity = new SystemLVEntity();
                            systemLVEntity.setType("课程扣除提醒");
                            systemLVEntity.setTime(systemCodeEntity.getResult().get(i).getSendTime());
                            systemLVEntity.setContent(systemCodeEntity.getResult().get(i).getContent());
                            systemLVEntityList.add(systemLVEntity);
                            break;
                        }
                    }
                    //获取第一个场馆通知
                    for (int i = 0; i < systemCodeEntity.getResult().size(); i++) {
                        if (systemCodeEntity.getResult().get(i).getType().equals("NormalMessageToUser")) {
                            SystemLVEntity systemLVEntity = new SystemLVEntity();
                            systemLVEntity.setType("场馆通知");
                            systemLVEntity.setTime(systemCodeEntity.getResult().get(i).getSendTime());
                            systemLVEntity.setContent(systemCodeEntity.getResult().get(i).getContent());
                            systemLVEntityList.add(systemLVEntity);
                            break;
                        }
                    }
                    //获取第一个场馆活动
                    for (int i = 0; i < systemCodeEntity.getResult().size(); i++) {
                        if (systemCodeEntity.getResult().get(i).getType().equals("EventMessageToUser")) {
                            SystemLVEntity systemLVEntity = new SystemLVEntity();
                            systemLVEntity.setType("场馆活动");
                            systemLVEntity.setTime(systemCodeEntity.getResult().get(i).getSendTime());
                            systemLVEntity.setContent(systemCodeEntity.getResult().get(i).getContent());
                            systemLVEntityList.add(systemLVEntity);
                            break;
                        }
                    }
                    //获取第一个系统通知
                    for (int i = 0; i < systemCodeEntity.getResult().size(); i++) {
                        if (systemCodeEntity.getResult().get(i).getType().equals("DevelopSystemMessage")) {
                            SystemLVEntity systemLVEntity = new SystemLVEntity();
                            systemLVEntity.setType("系统通知");
                            systemLVEntity.setTime(systemCodeEntity.getResult().get(i).getSendTime());
                            systemLVEntity.setContent(systemCodeEntity.getResult().get(i).getContent());
                            systemLVEntityList.add(systemLVEntity);
                            break;
                        }
                    }
                    systemView.getMsg(systemLVEntityList);
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
                    systemView.delMsg("删除成功");
                    systemView.delSuccess();
                } else {
                    systemView.delMsg(systemDelCodeEntity.getError());
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


    public void readMsg(String type) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("jpush", "readMsg"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("alias", MyApplication.userID);
        params.addBodyParameter("type", type);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                SystemDelCodeEntity systemDelCodeEntity = new SystemDelCodeEntity();
                systemDelCodeEntity = JsonUtils.delMsg(result);
                if (systemDelCodeEntity.getCode().equals("0")) {

                } else {
                    systemView.delMsg(systemDelCodeEntity.getError());
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
