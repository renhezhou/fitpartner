package rxh.shanks.EBEntity;

/**
 * Created by Administrator on 2016/12/7.
 */
public class PEntity {

    private int flag;
    private String id;
    private String price;

    public PEntity(int flag, String id, String price) {
        this.flag = flag;
        this.id = id;
        this.price = price;
    }

    public int getFlag() {
        return flag;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }
}
