package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19.
 */
public class PaymentStoredValueCodeCardListEntity {

    private String code;
    private String token;
    private String error;
    private List<PaymentStoredValueCardListEntity> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<PaymentStoredValueCardListEntity> getResult() {
        return result;
    }

    public void setResult(List<PaymentStoredValueCardListEntity> result) {
        this.result = result;
    }
}
