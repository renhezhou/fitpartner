package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class CoachDetailsCodeEntity {

    private String code;
    private String error;
    private String token;
    private List<CoachDetailsEntity> result;

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

    public List<CoachDetailsEntity> getResult() {
        return result;
    }

    public void setResult(List<CoachDetailsEntity> result) {
        this.result = result;
    }
}
