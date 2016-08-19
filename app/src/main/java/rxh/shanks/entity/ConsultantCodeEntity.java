package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class ConsultantCodeEntity {

    private String code;
    private String error;
    private String token;
    private List<ConsultantEntity> result;

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

    public List<ConsultantEntity> getResult() {
        return result;
    }

    public void setResult(List<ConsultantEntity> result) {
        this.result = result;
    }
}
