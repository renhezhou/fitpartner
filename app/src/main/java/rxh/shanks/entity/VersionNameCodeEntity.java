package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/11/25.
 */
public class VersionNameCodeEntity {

    private String code;
    private String error;
    private VersionNameEntity result;

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

    public VersionNameEntity getResult() {
        return result;
    }

    public void setResult(VersionNameEntity result) {
        this.result = result;
    }
}
