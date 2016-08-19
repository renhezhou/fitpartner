package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.IntformationEntity;
import rxh.shanks.entity.SystemLVEntity;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface SystemView {

    void getMsg(List<SystemLVEntity> systemLVEntityList);

    void delMsg(String message);

    void delSuccess();

}
