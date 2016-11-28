package rxh.shanks.utils;

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

}
