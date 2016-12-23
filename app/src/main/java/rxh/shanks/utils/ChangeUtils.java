package rxh.shanks.utils;

import android.view.WindowManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Administrator on 2016/11/15.
 */
public class ChangeUtils {


    //私教，团课课程预约时候状态码转换
    public static String get_state(String state) {
        String result = null;
        if (state == null) {
            result = "预约成功";
        } else {
            if (state.equals("3")) {
                result = "已完成";
            } else if (state.equals("2")) {
                result = "上课中";
            } else if (state.equals("1")) {

            } else if (state.equals("0")) {
                result = "预约成功";
            }
        }
        return result;
    }

    //保留小数点后一位的校验方法4.9621
    public static String get_star(String get_star) {
        String result = null;
        Double d = get_star == null ? 0 : Double.valueOf(get_star);
        NumberFormat n = NumberFormat.getCurrencyInstance();
        n.setMaximumFractionDigits(1);
        result = n.format(d);
        result.replaceAll("￥", "");
        return result;
    }

    //会员卡状态
    public static String get_card_type(String card_state) {
        String result = null;
        if (card_state != null) {
            if (card_state.equals("1")) {
                result = "正常";
            } else if (card_state.equals("2")) {
                result = "续卡";
            } else if (card_state.equals("3")) {
                result = " 过期";
            } else if (card_state.equals("4")) {
                result = "注销";
            } else if (card_state.equals("5")) {
                result = "请假";
            } else if (card_state.equals("6")) {
                result = "待审核";
            } else if (card_state.equals("7")) {
                result = "未开卡";
            }
        } else {
            result = "请切换默认卡，该默认卡不能签到";
        }
        return result;
    }

    //消费类型
    public static String get_consumption_type(String type) {
        String result = null;
        if (type != null) {
            if (type.equals("1")) {
                result = "购买卡";
            } else if (type.equals("2")) {
                result = "购买商品";
            } else if (type.equals("3")) {
                result = "购买柜子";
            } else if (type.equals("4")) {
                result = "购买私教课";
            } else if (type.equals("5")) {
                result = "购买团体课";
            }
        } else {
            result = "";
        }
        return result;
    }


}
