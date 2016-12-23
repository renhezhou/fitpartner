package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/12/7.
 */
public class PaymentCodeEntity {


    private String code;
    private String error;
    private PaymentEntity result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public PaymentEntity getResult() {
        return result;
    }

    public void setResult(PaymentEntity result) {
        this.result = result;
    }
}
