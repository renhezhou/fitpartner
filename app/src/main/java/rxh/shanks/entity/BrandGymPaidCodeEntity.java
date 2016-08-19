package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class BrandGymPaidCodeEntity {

    private String code;
    private String error;
    private String token;
    private List<BrandGymPaidEntity> result;


    public BrandGymPaidCodeEntity() {

    }

    public BrandGymPaidCodeEntity(String code, String error, String token, List<BrandGymPaidEntity> result) {
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

    public List<BrandGymPaidEntity> getResult() {
        return result;
    }

    public void setResult(List<BrandGymPaidEntity> result) {
        this.result = result;
    }
}
