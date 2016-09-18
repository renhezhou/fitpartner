package rxh.shanks.view;

import rxh.shanks.entity.LoginCodeEntity;

/**
 * Created by Administrator on 2016/8/5.
 */
public interface LoginView {

    void show();

    void hide();

    void onSuccess(String result, LoginCodeEntity response);

    void onError(Throwable ex);

    void check(String check);


}
