package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class TestRecordEventBusEntity {

    private List<TestRecordEntity> testRecordEntityList;

    public TestRecordEventBusEntity(List<TestRecordEntity> testRecordEntityList) {
        this.testRecordEntityList = testRecordEntityList;
    }

    public List<TestRecordEntity> getTestRecordEntityList() {
        return testRecordEntityList;
    }
}
