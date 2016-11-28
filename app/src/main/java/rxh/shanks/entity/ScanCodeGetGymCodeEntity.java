package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/11/10.
 */
public class ScanCodeGetGymCodeEntity {

    private String code;
    private String error;
    private ScanCodeGetGymEntity result;

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

    public ScanCodeGetGymEntity getResult() {
        return result;
    }

    public void setResult(ScanCodeGetGymEntity result) {
        this.result = result;
    }
}
