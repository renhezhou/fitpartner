package rxh.shanks.view;

import rxh.shanks.entity.ScanCodeGetGymEntity;

/**
 * Created by Administrator on 2016/11/8.
 */
public interface ScanView {

    void show();

    void hide();

    void toast(String msg);

    void get_gymInfo(ScanCodeGetGymEntity entity);


}
