package rxh.shanks.model;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/4/11.
 */
public class Is_Networking implements GetInfo {
    @Override
    public void getinfo(RequestParams params, final Response response) {
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
               response.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                response.onError(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
               response.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                response.onFinished();
            }
        });
    }
}
