package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MembershipCardEntity {

    private String cardID;
    private String clubID;
    private String clubName;
    private String remaindCount;
    private String remainingTimeOff;
    private String totalCount;
    private String type;
    private String validiPeriod;
    private String cardName;
    private String cardState;
    private String introduce;
    private List<String> clubNames;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getClubID() {
        return clubID;
    }

    public void setClubID(String clubID) {
        this.clubID = clubID;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getRemaindCount() {
        return remaindCount;
    }

    public void setRemaindCount(String remaindCount) {
        this.remaindCount = remaindCount;
    }

    public String getRemainingTimeOff() {
        return remainingTimeOff;
    }

    public void setRemainingTimeOff(String remainingTimeOff) {
        this.remainingTimeOff = remainingTimeOff;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValidiPeriod() {
        return validiPeriod;
    }

    public void setValidiPeriod(String validiPeriod) {
        this.validiPeriod = validiPeriod;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public List<String> getClubNames() {
        return clubNames;
    }

    public void setClubNames(List<String> clubNames) {
        this.clubNames = clubNames;
    }

    public String getCardState() {
        return cardState;
    }

    public void setCardState(String cardState) {
        this.cardState = cardState;
    }
}
