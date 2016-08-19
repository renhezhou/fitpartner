package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/8/5.
 */
public class LoginCodeEntity {

    private String code;
    private String token;
    private String isNew;
    private String error;
    private LoginEntity result;

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

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LoginEntity getResult() {
        return result;
    }

    public void setResult(LoginEntity result) {
        this.result = result;
    }
}
