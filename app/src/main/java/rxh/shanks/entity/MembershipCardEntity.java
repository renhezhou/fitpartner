package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MembershipCardEntity {

    private String isDefault;//1代表默认卡，0非默认卡
    private String surplusPrice;//剩余金额
    private String surplusCount;//剩余次数
    private String surplusTime;//剩余时间
    private String count;//总次数
    private String clubID;
    private String cardName;
    private String cardID;
    private String cardEndTime;
    private String cardType;//卡类型。0时间卡，1次卡，2储值卡
    private String cardDescripton;
    private String cardState;//
    private List<String> supportCludNames;
    private List<MembershipUsedHistoryEntity> usedHistory;

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getSurplusPrice() {
        return surplusPrice;
    }

    public void setSurplusPrice(String surplusPrice) {
        this.surplusPrice = surplusPrice;
    }

    public String getSurplusCount() {
        return surplusCount;
    }

    public void setSurplusCount(String surplusCount) {
        this.surplusCount = surplusCount;
    }

    public String getSurplusTime() {
        return surplusTime;
    }

    public void setSurplusTime(String surplusTime) {
        this.surplusTime = surplusTime;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getClubID() {
        return clubID;
    }

    public void setClubID(String clubID) {
        this.clubID = clubID;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getCardEndTime() {
        return cardEndTime;
    }

    public void setCardEndTime(String cardEndTime) {
        this.cardEndTime = cardEndTime;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardDescripton() {
        return cardDescripton;
    }

    public void setCardDescripton(String cardDescripton) {
        this.cardDescripton = cardDescripton;
    }

    public String getCardState() {
        return cardState;
    }

    public void setCardState(String cardState) {
        this.cardState = cardState;
    }

    public List<String> getSupportCludNames() {
        return supportCludNames;
    }

    public void setSupportCludNames(List<String> supportCludNames) {
        this.supportCludNames = supportCludNames;
    }

    public List<MembershipUsedHistoryEntity> getUsedHistory() {
        return usedHistory;
    }

    public void setUsedHistory(List<MembershipUsedHistoryEntity> usedHistory) {
        this.usedHistory = usedHistory;
    }
}
