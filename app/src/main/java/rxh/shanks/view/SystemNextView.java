package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.SystemLVEntity;

/**
 * Created by Administrator on 2016/8/19.
 */
public interface SystemNextView {

    void show();

    void hide();


    void getMsg(List<SystemLVEntity> systemLVEntityList);

    void delMsg(String message);

    void delSuccess();

}
