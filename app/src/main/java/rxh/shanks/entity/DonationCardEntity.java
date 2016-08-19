package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/8/12.
 */
public class DonationCardEntity {

    private String QRString;
    private String codeID;
    private String count;

    public String getQRString() {
        return QRString;
    }

    public void setQRString(String QRString) {
        this.QRString = QRString;
    }

    public String getCodeID() {
        return codeID;
    }

    public void setCodeID(String codeID) {
        this.codeID = codeID;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
