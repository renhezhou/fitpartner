package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.MembershipCardEntity;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface MembershipCardView {

    void show();

    void hide();

    void getFitCard(List<MembershipCardEntity> membershipCardEntityList);

    void setDefaultCard();

    void toast(String msg);

    void error();

}
