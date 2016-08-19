package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.MyPrivateEducationHeadEntity;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface MyPrivateEducationView {

    void show();

    void hide();

    void getMyPrivateCoach(List<MyPrivateEducationHeadEntity> myPrivateEducationHeadEntityList);

}
