package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.FitCardEntity;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface CheckToView {

    void show();

    void hide();

    void generatedQR(String result);

    void onFinished(String result);

    void getFitCard(List<FitCardEntity> fitCardEntityList);

}
