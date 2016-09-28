package rxh.shanks.eventity;

/**
 * Created by Administrator on 2016/9/21.
 */
public class PIEEBEntity {

    private String img_path;
    private String username;

    public PIEEBEntity(String img_path, String username) {
        this.img_path = img_path;
        this.username = username;
    }

    public String getImg_path() {
        return img_path;
    }

    public String getUsername() {
        return username;
    }
}
