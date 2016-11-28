package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/11/8.
 */
public class ScanCodeEntity {

    private String code;
    private String error;
    private ScanEntity result;

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

    public ScanEntity getResult() {
        return result;
    }

    public void setResult(ScanEntity result) {
        this.result = result;
    }
}
