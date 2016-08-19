package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/16.
 */
public class ViewFreeCoursesCodeEntity {

    private String code;
    private String error;
    private String token;
    private List<ViewFreeCoursesEntity> result;

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

    public List<ViewFreeCoursesEntity> getResult() {
        return result;
    }

    public void setResult(List<ViewFreeCoursesEntity> result) {
        this.result = result;
    }
}
