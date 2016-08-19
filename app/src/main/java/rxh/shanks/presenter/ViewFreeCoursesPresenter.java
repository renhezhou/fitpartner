package rxh.shanks.presenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import rxh.shanks.entity.CourseDetailsCodeEntity;
import rxh.shanks.model.GetInfo;
import rxh.shanks.model.Is_Networking;
import rxh.shanks.model.Response;
import rxh.shanks.utils.CreatUrl;
import rxh.shanks.utils.JsonUtils;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.CourseDetailsView;
import rxh.shanks.view.ViewFreeCoursesView;

/**
 * Created by Administrator on 2016/8/16.
 */
public class ViewFreeCoursesPresenter {


    private GetInfo getInfo;
    private ViewFreeCoursesView viewFreeCoursesView;

    public ViewFreeCoursesPresenter(ViewFreeCoursesView viewFreeCoursesView) {
        getInfo = new Is_Networking();
        this.viewFreeCoursesView = viewFreeCoursesView;
    }

    public void getFreeLesson(String clubID, String freeTime) {
        RequestParams params = new RequestParams(CreatUrl.creaturl("lesson", "getFreeLesson"));
        params.addBodyParameter("token", MyApplication.token);
        params.addBodyParameter("clubID", clubID);
        params.addBodyParameter("freeTime", freeTime);
        params.addBodyParameter("type", "1");
        getInfo.getinfo(params, new Response() {
            @Override
            public void onSuccess(String result) {
                CourseDetailsCodeEntity courseDetailsCodeEntity = new CourseDetailsCodeEntity();
                courseDetailsCodeEntity = JsonUtils.getFreeLesson(result);
                if (courseDetailsCodeEntity.getCode().equals("0")) {
                    viewFreeCoursesView.getFreeLesson(courseDetailsCodeEntity.getResult());
                }

            }

            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
