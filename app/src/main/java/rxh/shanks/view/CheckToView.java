package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.FitCardEntity;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface CheckToView {

    void show(int flag);

    void hide();

    void generatedQR(String result);

    void handleCardSuccess(String cardID, String cardType);

    void handleCard(String cardID, String cardType);

    void onFinished(String result);

    void getFitCard(List<FitCardEntity> fitCardEntityList);

}
