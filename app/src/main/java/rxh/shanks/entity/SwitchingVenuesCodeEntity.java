package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class SwitchingVenuesCodeEntity {

    private String code;
    private String error;
    private String token;
    private List<List<SwitchingVenuesEntity>> result;

    public SwitchingVenuesCodeEntity() {

    }

    public SwitchingVenuesCodeEntity(String code, String error, String token, List<List<SwitchingVenuesEntity>> result) {
        this.code = code;
        this.error = error;
        this.token = token;
        this.result = result;
    }


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

    public List<List<SwitchingVenuesEntity>> getResult() {
        return result;
    }

    public void setResult(List<List<SwitchingVenuesEntity>> result) {
        this.result = result;
    }
}
