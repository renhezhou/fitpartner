package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/12/7.
 */
public class PaymentEntity {

    private String qr;
    private String pwdInited;//1已设置，0未设置

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getPwdInited() {
        return pwdInited;
    }

    public void setPwdInited(String pwdInited) {
        this.pwdInited = pwdInited;
    }
}
