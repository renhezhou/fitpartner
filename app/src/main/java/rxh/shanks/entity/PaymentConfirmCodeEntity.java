package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/12/19.
 */
public class PaymentConfirmCodeEntity {

    private String code;
    private String token;
    private String error;

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
}
