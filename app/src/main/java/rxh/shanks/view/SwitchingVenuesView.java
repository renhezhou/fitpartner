package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.SwitchingVenuesAdapterEntity;
import rxh.shanks.entity.SwitchingVenuesCodeEntity;

/**
 * Created by Administrator on 2016/8/11.
 */
public interface SwitchingVenuesView {

    void show();

    void hide();

    void getallvenues(List<SwitchingVenuesAdapterEntity> SwitchingVenuesAdapterEntitys);

    void switchingvenues();

}
