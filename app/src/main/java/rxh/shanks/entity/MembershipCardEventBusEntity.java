package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class MembershipCardEventBusEntity {

    private List<MembershipCardEntity> membershipCardEntityList;

    public MembershipCardEventBusEntity(List<MembershipCardEntity> membershipCardEntityList) {
        this.membershipCardEntityList = membershipCardEntityList;
    }

    public List<MembershipCardEntity> getMembershipCardEntityList() {
        return membershipCardEntityList;
    }
}
