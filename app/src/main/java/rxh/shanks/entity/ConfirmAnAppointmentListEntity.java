package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class ConfirmAnAppointmentListEntity {

    private String date;
    private String isEmpty;
    private List<ConfirmAnAppointmentEntity> data;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(String isEmpty) {
        this.isEmpty = isEmpty;
    }

    public List<ConfirmAnAppointmentEntity> getData() {
        return data;
    }

    public void setData(List<ConfirmAnAppointmentEntity> data) {
        this.data = data;
    }
}
