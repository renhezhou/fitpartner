package rxh.shanks.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/5.
 */
public class CheckUtils {

    //查看手机中是否安装了某个app
    public static boolean isinstallbyread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    //时间戳转化为具体时间
    public static String timetodate(String seconds) {
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//        return sdf.format(new Date(Long.valueOf(seconds + "000")));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = new Long(Long.parseLong(seconds));
        String d = format.format(time);
        return d;
    }

    //获取当前年月日
    public static String getdate() {
        String time = null;
        Calendar now = Calendar.getInstance();
        time = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        return time;
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    //判断是哪种类型的提醒
    public static String gettype(String type) {
        String result = null;
        if (type.equals("RemindLessonToUser")) {
            result = "上课提醒";
        } else if (type.equals("ReplaceGroupClassToUser")) {
            result = "教练代约团课";
        } else if (type.equals("ReplaceOrderLessonToUser")) {
            result = "教练代约私教课";
        } else if (type.equals("CardCloseExpiredToUser")) {
            result = "会员卡即将到期";
        } else if (type.equals("SendCouponToUser")) {
            result = "新增优惠券";
        } else if (type.equals("DeductionLessonToUser")) {
            result = "课程扣除提醒";
        } else if (type.equals("NormalMessageToUser")) {
            result = "场馆通知";
        } else if (type.equals("EventMessageToUser")) {
            result = "场馆活动";
        } else if (type.equals("DevelopSystemMessage")) {
            result = "系统通知";
        }
        return result;
    }

    //判断是哪种类型的提醒
    public static String getbacktype(String type) {
        String result = null;
        if (type.equals("上课提醒")) {
            result = "RemindLessonToUser";
        } else if (type.equals("教练代约团课")) {
            result = "ReplaceGroupClassToUser";
        } else if (type.equals("教练代约私教课")) {
            result = "ReplaceOrderLessonToUser";
        } else if (type.equals("会员卡即将到期")) {
            result = "CardCloseExpiredToUser";
        } else if (type.equals("新增优惠券")) {
            result = "SendCouponToUser";
        } else if (type.equals("课程扣除提醒")) {
            result = "DeductionLessonToUser";
        } else if (type.equals("场馆通知")) {
            result = "NormalMessageToUser";
        } else if (type.equals("场馆活动")) {
            result = "EventMessageToUser";
        } else if (type.equals("系统通知")) {
            result = "DevelopSystemMessage";
        }
        return result;
    }
}
