package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public class DataSouceCoachCodeEntity {

    private String code;
    private String error;
    private List<DataSouceCoachEntity> result;

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

    public List<DataSouceCoachEntity> getResult() {
        return result;
    }

    public void setResult(List<DataSouceCoachEntity> result) {
        this.result = result;
    }
}
