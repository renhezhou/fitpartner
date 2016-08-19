package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ConfirmAnAppointmentEntity {

    private String maxPeople;
    private String orderPeople;
    private String orderState;
    private String planID;
    private String time;

    public String getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(String maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getOrderPeople() {
        return orderPeople;
    }

    public void setOrderPeople(String orderPeople) {
        this.orderPeople = orderPeople;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
