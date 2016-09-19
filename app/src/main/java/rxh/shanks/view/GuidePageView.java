package rxh.shanks.view;

import rxh.shanks.entity.LoginCodeEntity;

/**
 * Created by Administrator on 2016/9/19.
 */
public interface GuidePageView {


    void onSuccess(String result, LoginCodeEntity response);

    void onError(Throwable ex);

    void check(String check);

}
