package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/11/8.
 */
public class ScanEntity {


    private String type;//in代表迁入，out代表迁出
    private String gymName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }
}
