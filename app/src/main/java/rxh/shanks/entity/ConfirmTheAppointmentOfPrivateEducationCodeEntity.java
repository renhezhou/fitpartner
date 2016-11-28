package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public class ConfirmTheAppointmentOfPrivateEducationCodeEntity {

    private String code;
    private String error;
    private List<ConfirmTheAppointmentOfPrivateEducationEntity> result;

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

    public List<ConfirmTheAppointmentOfPrivateEducationEntity> getResult() {
        return result;
    }

    public void setResult(List<ConfirmTheAppointmentOfPrivateEducationEntity> result) {
        this.result = result;
    }
}
