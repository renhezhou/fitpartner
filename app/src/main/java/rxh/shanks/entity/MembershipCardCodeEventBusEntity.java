package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/8/15.
 */
public class MembershipCardCodeEventBusEntity {

    private String cardID;

    public MembershipCardCodeEventBusEntity(String cardID) {
        this.cardID = cardID;
    }

    public String getCardID() {
        return cardID;
    }
}
