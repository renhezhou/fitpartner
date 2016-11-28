package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public class MyPrivateTeachingAppointmentCodeEntity {

    private String code;
    private String error;
    private List<MyPrivateTeachingAppointmentEntity> result;

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

    public List<MyPrivateTeachingAppointmentEntity> getResult() {
        return result;
    }

    public void setResult(List<MyPrivateTeachingAppointmentEntity> result) {
        this.result = result;
    }
}
