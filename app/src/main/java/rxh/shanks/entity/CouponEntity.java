package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/8/2.
 */
public class CouponEntity {

    private String img;
    private String club_name;
    private String discount;
    private String applicable_shop;
    private String suitable_product;
    private String term_of_validity;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getApplicable_shop() {
        return applicable_shop;
    }

    public void setApplicable_shop(String applicable_shop) {
        this.applicable_shop = applicable_shop;
    }

    public String getSuitable_product() {
        return suitable_product;
    }

    public void setSuitable_product(String suitable_product) {
        this.suitable_product = suitable_product;
    }

    public String getTerm_of_validity() {
        return term_of_validity;
    }

    public void setTerm_of_validity(String term_of_validity) {
        this.term_of_validity = term_of_validity;
    }
}
