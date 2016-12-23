package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.PaymentEntity;
import rxh.shanks.entity.PaymentInfoEntity;
import rxh.shanks.entity.PaymentStoredValueCardListEntity;

/**
 * Created by Administrator on 2016/12/6.
 */
public interface PaymentView {


    void show(int flag);

    void hide();

    void toast(String msg);

    void generatedPaidQR(PaymentEntity entity);

    void getpaidcard(List<PaymentStoredValueCardListEntity> entities);

    void get_pay_info(String price);

    void pay_confirm();

}
