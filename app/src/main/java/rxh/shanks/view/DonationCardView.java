package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.DonationCardEntity;

/**
 * Created by Administrator on 2016/8/12.
 */
public interface DonationCardView {

    void gitSurplusGiftQR(List<DonationCardEntity> donationCardEntityList);

    void giftQR(List<DonationCardEntity> donationCardEntityList);

    void toast(String msg);

}
