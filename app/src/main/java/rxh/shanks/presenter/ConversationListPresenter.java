package rxh.shanks.presenter;

import android.net.Uri;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import io.rong.imlib.model.UserInfo;
import rxh.shanks.entity.ConversationCodeEntity;
import rxh.shanks.entity.ConversationEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.view.ConversationListView;

/**
 * Created by Administrator on 2016/8/17.
 */
public class ConversationListPresenter {


    private GetInfo getInfo;
    private ConversationListView conversationListView;

    public ConversationListPresenter(ConversationListView conversationListView) {
        getInfo = new Is_Networking();
        this.conversationListView = conversationListView;
    }

    public UserInfo getUserInfo(final String userID) {
        final UserInfo[] userInfo = {null};
        RequestParams params = new RequestParams(CreatUrl.creaturl("user", "getUserInfo"));
        params.addBodyParameter("userID", userID);
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                ConversationCodeEntity conversationCodeEntity = new ConversationCodeEntity();
                conversationCodeEntity = JsonUtils.getUserInfo(result);
                if (conversationCodeEntity.getCode().equals("0")) {
                    ConversationEntity conversationEntity = new ConversationEntity();
                    conversationEntity = conversationCodeEntity.getResult();
                    userInfo[0] = new UserInfo(userID, conversationEntity.getRealName(), Uri.parse(conversationEntity.getHeadImageURL()));
                    conversationListView.getuserinfo(userInfo[0]);
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
        return userInfo[0];
    }

}
