package rxh.shanks.view;

/**
 * Created by Administrator on 2016/8/5.
 */
public interface LoginView {

    void show();

    void hide();

    void onSuccess(String result);

    void onError(Throwable ex);

    void check(String check);


}
