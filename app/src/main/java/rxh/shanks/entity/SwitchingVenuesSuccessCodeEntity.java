package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/8/16.
 */
public class SwitchingVenuesSuccessCodeEntity {

    private String code;
    private String error;
    private String token;
    private SwitchingVenuesSuccessEntity result;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SwitchingVenuesSuccessEntity getResult() {
        return result;
    }

    public void setResult(SwitchingVenuesSuccessEntity result) {
        this.result = result;
    }
}
