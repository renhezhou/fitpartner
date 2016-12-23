package rxh.shanks.view;

import de.greenrobot.event.EventBus;
import io.rong.imlib.RongIMClient;
import rxh.shanks.EBEntity.RYEntity;

/**
 * Created by Administrator on 2016/11/29.
 * 融云连接监听
 */
public class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {
    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        switch (connectionStatus) {

            case CONNECTED://连接成功。

                break;
            case DISCONNECTED://断开连接。

                break;
            case CONNECTING://连接中。

                break;
            case NETWORK_UNAVAILABLE://网络不可用。

                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                EventBus.getDefault().post(new RYEntity());
                break;
        }
    }
}
