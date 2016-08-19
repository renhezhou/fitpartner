package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29.
 */
public class CoachEntity {

    private String coachID;
    private String name;
    private String descrip;
    private String introduction;
    private String numOfServiced;
    private String minimunPrice;
    private String headImageURL;
    private List<String> coverImageArray;
    private String specialty;
    private String teachTime;
    private String evaluate;
    private String clubName;
    private String workTime;
    private String restTime;

    public String getCoachID() {
        return coachID;
    }

    public void setCoachID(String coachID) {
        this.coachID = coachID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNumOfServiced() {
        return numOfServiced;
    }

    public void setNumOfServiced(String numOfServiced) {
        this.numOfServiced = numOfServiced;
    }

    public String getMinimunPrice() {
        return minimunPrice;
    }

    public void setMinimunPrice(String minimunPrice) {
        this.minimunPrice = minimunPrice;
    }

    public String getHeadImageURL() {
        return headImageURL;
    }

    public void setHeadImageURL(String headImageURL) {
        this.headImageURL = headImageURL;
    }

    public List<String> getCoverImageArray() {
        return coverImageArray;
    }

    public void setCoverImageArray(List<String> coverImageArray) {
        this.coverImageArray = coverImageArray;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getTeachTime() {
        return teachTime;
    }

    public void setTeachTime(String teachTime) {
        this.teachTime = teachTime;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getRestTime() {
        return restTime;
    }

    public void setRestTime(String restTime) {
        this.restTime = restTime;
    }
}
