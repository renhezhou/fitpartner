package rxh.shanks.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class SwitchingVenuesAdapterEntity {


    private String brandName;
    private List<SwitchingVenuesEntity> entityList;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<SwitchingVenuesEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<SwitchingVenuesEntity> entityList) {
        this.entityList = entityList;
    }
}
