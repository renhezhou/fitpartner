package rxh.shanks.view;

/**
 * Created by Administrator on 2016/8/5.
 */
public interface SetUserInformationNextView {

    void show();

    void hide();

    void onSuccess();

    void onError(Throwable ex);

    void check(String check);

}
