package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.BrandInfoEntity;

/**
 * Created by Administrator on 2016/8/8.
 */
public interface GetBrandInfoView {

    void show();

    void hide();

    void getBrandInfo(List<BrandInfoEntity> brandInfoEntity);

}
