package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class TestRecordCodeEntity {

    private String code;
    private String error;
    private String token;
    private List<TestRecordEntity> result;

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

    public List<TestRecordEntity> getResult() {
        return result;
    }

    public void setResult(List<TestRecordEntity> result) {
        this.result = result;
    }
}
