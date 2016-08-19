package rxh.shanks.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.entity.AlreadyMakeAnAppointmentCodeEntity;
import rxh.shanks.entity.BrandGymPaidCodeEntity;
import rxh.shanks.entity.BrandInfoCodeEntity;
import rxh.shanks.entity.CheckToCodeEntity;
import rxh.shanks.entity.CoachCodeEntity;
import rxh.shanks.entity.CoachDetailsCodeEntity;
import rxh.shanks.entity.ConfirmAnAppointmentActivityCodeEntity;
import rxh.shanks.entity.ConfirmAnAppointmentListCodeEntity;
import rxh.shanks.entity.ConsultantCodeEntity;
import rxh.shanks.entity.ConversationCodeEntity;
import rxh.shanks.entity.ConversationEntity;
import rxh.shanks.entity.CourseDetailsCodeEntity;
import rxh.shanks.entity.DonationCardCodeEntity;
import rxh.shanks.entity.EvaluateCodeEntity;
import rxh.shanks.entity.FeedbackCodeEntity;
import rxh.shanks.entity.FitCardCodeEntity;
import rxh.shanks.entity.FitnessCalendarCodeEntity;
import rxh.shanks.entity.LoginCodeEntity;
import rxh.shanks.entity.MembershipCardCodeEntity;
import rxh.shanks.entity.MyPrivateEducationHeadCodeEntity;
import rxh.shanks.entity.NotMakeAnAppointmentCodeEntity;
import rxh.shanks.entity.PrivateEducationCourseBespokeLessonEntity;
import rxh.shanks.entity.PrivateEducationCourseGetHoldingTimeCodeEntity;
import rxh.shanks.entity.PrivateEducationCourseGetUserHoldingTimeCodeEntity;
import rxh.shanks.entity.ResetPasswordCodeEntity;
import rxh.shanks.entity.SetUserInformationCodeEntity;
import rxh.shanks.entity.SwitchingVenuesCodeEntity;
import rxh.shanks.entity.SwitchingVenuesEntity;
import rxh.shanks.entity.SwitchingVenuesSuccessCodeEntity;
import rxh.shanks.entity.SystemCodeEntity;
import rxh.shanks.entity.SystemDelCodeEntity;
import rxh.shanks.entity.TestRecordCodeEntity;
import rxh.shanks.entity.ViewAppointmentCodeEntity;
import rxh.shanks.entity.ViewAppointmentState;
import rxh.shanks.entity.ViewFreeCoursesCodeEntity;

/**
 * Created by Administrator on 2016/4/11.
 */
public class JsonUtils {

    //解析登录时服务器返回的数据
    public static LoginCodeEntity login(String data) {
        LoginCodeEntity result = new LoginCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), LoginCodeEntity.class);
        return result;
    }

    //解析第一次登录时进入个人信息设置界面服务器返回的数据
    public static SetUserInformationCodeEntity setuserinformation(String data) {
        SetUserInformationCodeEntity result = new SetUserInformationCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), SetUserInformationCodeEntity.class);
        return result;
    }

    //解析用户重置密码时服务器返回的数据
    public static ResetPasswordCodeEntity resetPassword(String data) {
        ResetPasswordCodeEntity result = new ResetPasswordCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), ResetPasswordCodeEntity.class);
        return result;
    }


    //我的课程模块，主页面获取该品牌下会员拥有的场馆
    public static BrandGymPaidCodeEntity getBrandGymPaid(String data) {
//        List<BrandGymPaidEntity> brandGymPaidEntityList = new ArrayList<>();
//        JSONObject obj = JSONObject.parseObject(data);
//        String code = obj.getString("code");
//        String error = obj.getString("error");
//        String token = obj.getString("token");
//        JSONArray jsonArray = obj.getJSONArray("result");
//        // Gson解析Json
//        if (jsonArray != null) {
//            for (int i = 0; i < jsonArray.size(); i++) {
//                BrandGymPaidEntity brandGymPaidEntity = new BrandGymPaidEntity();
//                GsonBuilder gsonb = new GsonBuilder();
//                Gson gson = gsonb.create();
//                brandGymPaidEntity = gson.fromJson(jsonArray.get(i).toString(),
//                        BrandGymPaidEntity.class);
//                brandGymPaidEntityList.add(brandGymPaidEntity);
//            }
//        }
//        BrandGymPaidCodeEntity result = new BrandGymPaidCodeEntity(code, error, token, brandGymPaidEntityList);
        BrandGymPaidCodeEntity result = new BrandGymPaidCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), BrandGymPaidCodeEntity.class);
        return result;
    }

    //我的课程模块，主页面获取我的会稽顾问
    public static ConsultantCodeEntity getConsultant(String data) {
        ConsultantCodeEntity result = new ConsultantCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), ConsultantCodeEntity.class);
        return result;
    }

    //我的课程模块，主页面获取banner及其信息
    public static BrandInfoCodeEntity getBrandInfo(String data) {
        BrandInfoCodeEntity result = new BrandInfoCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), BrandInfoCodeEntity.class);
        return result;
    }

    //我的课程模块，免费课程及团体课的数据解析
    public static CourseDetailsCodeEntity getFreeLesson(String data) {
        CourseDetailsCodeEntity result = new CourseDetailsCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), CourseDetailsCodeEntity.class);
        return result;
    }

    //我的教练模块的所有数据
    public static CoachCodeEntity getCoach(String data) {
        CoachCodeEntity result = new CoachCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), CoachCodeEntity.class);
        return result;
    }

    //我的教练模块教练详情下的私教和团课
    public static CoachDetailsCodeEntity getPrivateLessongetTeamLesson(String data) {
        CoachDetailsCodeEntity result = new CoachDetailsCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), CoachDetailsCodeEntity.class);
        return result;
    }

    //获取签到二维码
    public static CheckToCodeEntity generatedQR(String data) {
        CheckToCodeEntity result = new CheckToCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), CheckToCodeEntity.class);
        return result;
    }

    //获取签到二维码界面的卡信息
    public static FitCardCodeEntity getFitCard(String data) {
        FitCardCodeEntity result = new FitCardCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), FitCardCodeEntity.class);
        return result;
    }

    //意见反馈
    public static FeedbackCodeEntity submitOpinion(String data) {
        FeedbackCodeEntity result = new FeedbackCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), FeedbackCodeEntity.class);
        return result;
    }

    //体测记录
    public static TestRecordCodeEntity getBodyTestRecord(String data) {
        TestRecordCodeEntity result = new TestRecordCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), TestRecordCodeEntity.class);
        return result;
    }

    //体测记录
    public static MembershipCardCodeEntity getmyFitCard(String data) {
        MembershipCardCodeEntity result = new MembershipCardCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), MembershipCardCodeEntity.class);
        return result;
    }

    //健身日历
    public static FitnessCalendarCodeEntity getFitCalender(String data) {
        FitnessCalendarCodeEntity result = new FitnessCalendarCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), FitnessCalendarCodeEntity.class);
        return result;
    }

    //我的私教我的团课
    public static MyPrivateEducationHeadCodeEntity getMyPrivateCoach(String data) {
        MyPrivateEducationHeadCodeEntity result = new MyPrivateEducationHeadCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), MyPrivateEducationHeadCodeEntity.class);
        return result;
    }

    //获取用户未预约的私教课程
    public static NotMakeAnAppointmentCodeEntity getMyUnorderPrivateLesson(String data) {
        NotMakeAnAppointmentCodeEntity result = new NotMakeAnAppointmentCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), NotMakeAnAppointmentCodeEntity.class);
        return result;
    }

    //获取用户已预约的私教课程
    public static AlreadyMakeAnAppointmentCodeEntity getMyOrderPrivateLesson(String data) {
        AlreadyMakeAnAppointmentCodeEntity result = new AlreadyMakeAnAppointmentCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), AlreadyMakeAnAppointmentCodeEntity.class);
        return result;
    }

    //获取我的预约
    public static ViewAppointmentCodeEntity getOrderLesson(String data) {
        ViewAppointmentCodeEntity result = new ViewAppointmentCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), ViewAppointmentCodeEntity.class);
        return result;
    }

    //课程评价
    public static EvaluateCodeEntity makeEvaluate(String data) {
        EvaluateCodeEntity result = new EvaluateCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), EvaluateCodeEntity.class);
        return result;
    }

    //我的预约界面的操作
    public static ViewAppointmentState viewAppointmentState(String data) {
        ViewAppointmentState result = new ViewAppointmentState();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), ViewAppointmentState.class);
        return result;
    }

    //预约私教课程，获取教练占用的时间
    public static PrivateEducationCourseGetHoldingTimeCodeEntity getHoldingTime(String data) {
        PrivateEducationCourseGetHoldingTimeCodeEntity result = new PrivateEducationCourseGetHoldingTimeCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), PrivateEducationCourseGetHoldingTimeCodeEntity.class);
        return result;
    }

    //预约私教课程，获取用户占用的时间
    public static PrivateEducationCourseGetUserHoldingTimeCodeEntity getUserHoldingTime(String data) {
        PrivateEducationCourseGetUserHoldingTimeCodeEntity result = new PrivateEducationCourseGetUserHoldingTimeCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), PrivateEducationCourseGetUserHoldingTimeCodeEntity.class);
        return result;
    }

    //获取系统消息
    public static SystemCodeEntity getMsg(String data) {
        SystemCodeEntity result = new SystemCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), SystemCodeEntity.class);
        return result;
    }

    //删除某条系统消息
    public static SystemDelCodeEntity delMsg(String data) {
        SystemDelCodeEntity result = new SystemDelCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), SystemDelCodeEntity.class);
        return result;
    }

    //切换场馆 界面的所有场馆
    public static SwitchingVenuesCodeEntity getClub(String data) {
        List<List<SwitchingVenuesEntity>> SwitchingVenuesEntitys = new ArrayList<>();
        JSONObject obj = JSONObject.parseObject(data);
        String code = obj.getString("code");
        String error = obj.getString("error");
        String token = obj.getString("token");
        JSONArray jsonArray = obj.getJSONArray("result");
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.size(); i++) {
                List<SwitchingVenuesEntity> switchingVenuesEntities = new ArrayList<>();
                JSONArray array = jsonArray.getJSONArray(i);
                if (array != null) {
                    for (int j = 0; j < array.size(); j++) {
                        // Gson解析Json
                        GsonBuilder gsonb = new GsonBuilder();
                        Gson gson = gsonb.create();
                        SwitchingVenuesEntity switchingVenuesEntity = new SwitchingVenuesEntity();
                        switchingVenuesEntity = gson.fromJson(array.get(j).toString(), SwitchingVenuesEntity.class);
                        switchingVenuesEntities.add(switchingVenuesEntity);
                    }
                }
                SwitchingVenuesEntitys.add(switchingVenuesEntities);
            }
        }
        SwitchingVenuesCodeEntity result = new SwitchingVenuesCodeEntity(code, error, token, SwitchingVenuesEntitys);
        return result;
    }


    //我的 团课去预约
    public static ConfirmAnAppointmentListCodeEntity getTeamLessonFreeTime(String data) {
        ConfirmAnAppointmentListCodeEntity result = new ConfirmAnAppointmentListCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), ConfirmAnAppointmentListCodeEntity.class);
        return result;
    }

    //我的预约团课
    public static ConfirmAnAppointmentActivityCodeEntity orderTeamLesson(String data) {
        ConfirmAnAppointmentActivityCodeEntity result = new ConfirmAnAppointmentActivityCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), ConfirmAnAppointmentActivityCodeEntity.class);
        return result;
    }

    //转赠次卡
    public static DonationCardCodeEntity DonationCard(String data) {
        DonationCardCodeEntity result = new DonationCardCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), DonationCardCodeEntity.class);
        return result;
    }

    //预约私教课
    public static PrivateEducationCourseBespokeLessonEntity bespokeLesson(String data) {
        PrivateEducationCourseBespokeLessonEntity result = new PrivateEducationCourseBespokeLessonEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), PrivateEducationCourseBespokeLessonEntity.class);
        return result;
    }

    //切换场馆成功
    public static SwitchingVenuesSuccessCodeEntity switchingvenues(String data) {
        SwitchingVenuesSuccessCodeEntity result = new SwitchingVenuesSuccessCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), SwitchingVenuesSuccessCodeEntity.class);
        return result;
    }


    //获取免费课程
    public static ViewFreeCoursesCodeEntity getBortherClubShop(String data) {
        ViewFreeCoursesCodeEntity result = new ViewFreeCoursesCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), ViewFreeCoursesCodeEntity.class);
        return result;
    }

    //获取用户信息
    public static ConversationCodeEntity getUserInfo(String data) {
        ConversationCodeEntity result = new ConversationCodeEntity();
        JSONObject obj = JSONObject.parseObject(data);
        // Gson解析Json
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        result = gson.fromJson(obj.toString(), ConversationCodeEntity.class);
        return result;
    }

}
