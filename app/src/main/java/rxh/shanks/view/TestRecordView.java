package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.TestRecordEntity;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface TestRecordView {

    void show();

    void hide();

    void getBodyTestRecord(List<TestRecordEntity> testRecordEntityList);

}
