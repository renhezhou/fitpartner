package rxh.shanks.entity;

/**
 * Created by Administrator on 2016/8/10.
 */
public class MyPrivateEducationEventBusEntity {

    private String flag;
    private MyPrivateEducationHeadEntity myPrivateEducationHeadEntity;

    public MyPrivateEducationEventBusEntity(String flag, MyPrivateEducationHeadEntity myPrivateEducationHeadEntity) {
        this.flag = flag;
        this.myPrivateEducationHeadEntity = myPrivateEducationHeadEntity;
    }

    public String getFlag() {
        return flag;
    }

    public MyPrivateEducationHeadEntity getMyPrivateEducationHeadEntity() {
        return myPrivateEducationHeadEntity;
    }
}
