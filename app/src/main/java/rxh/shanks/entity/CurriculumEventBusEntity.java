package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class CurriculumEventBusEntity {

    private List<String> urls;

    public CurriculumEventBusEntity(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }
}
