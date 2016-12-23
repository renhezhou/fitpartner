package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/12/19.
 */
public class PaymentReceiverCodeEntity {

    private String type;
    private PaymentReceiverEntity order;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PaymentReceiverEntity getOrder() {
        return order;
    }

    public void setOrder(PaymentReceiverEntity order) {
        this.order = order;
    }
}
