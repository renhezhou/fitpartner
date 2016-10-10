package rxh.shanks.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CreatTime {

    public static Date creatdate(Date date, int amount) {
        Date mDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, amount);
            mDate = calendar.getTime();
        }
        return mDate;
    }


    public static List<String> creat(Date data) {
        List<String> date = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        // public final String format(Date date)
        for (int i = 0; i < 5; i++) {
            String time = sdf.format(creatdate(data, i));
            String week = getWeekOfDate(creatdate(data, i));
            date.add(time + "\n" + week);
        }
        return date;
    }

    public static List<String> seclet(Date data) {
        List<String> date = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        // public final String format(Date date)
        for (int i = 0; i < 5; i++) {
            String time = sdf.format(creatdate(data, i));
            date.add(time);
        }
        return date;
    }

    public static List<String> creatfragment(Date data) {
        List<String> date = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        // public final String format(Date date)
        for (int i = 0; i < 5; i++) {
            String time = sdf.format(creatdate(data, i));
            date.add(time);
        }
        return date;
    }

    public static String creatfragment() {
        String date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        // public final String format(Date date)
        date = sdf.format(new Date());
        return date;
    }


    /**
     * 获取指定日期是星期几
     * 参数为null时表示获取当前日期是星期几
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }


    //将时间戳转化为Date
    public static Date conversion(String dateStr) {
        Date date = null;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("yyyy-MM-dd");
        try {
            date = formater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String creatfilename() {
        String dates = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // public final String format(Date date)
        dates = sdf.format(new Date());
        return dates;
    }


    //判断某天距今天有多少天
    public static int test(String d) throws ParseException {
        Date date = new Date();
        long a = date.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long b = sdf.parse(d).getTime();
        int success = (int) ((b - a) / (1000 * 60 * 60 * 24));  //1000毫秒*60分钟*60秒*24小时 = 天
        System.out.println("距离" + d + "还有" + success + "天");
        return success;
    }

    //我的私教课程构造请求参数
    public static String qingqiu(String date, String time, int restTime) {
        String result = null;
        String[] strs = time.split(":");
        int hour = Integer.parseInt(strs[0]);
        int minuts = Integer.parseInt(strs[1]);
        if (minuts + restTime > 60) {
            hour = hour + 1;
            minuts = minuts + restTime - 60;
        } else {
            minuts = minuts + restTime;
        }
        result = date + "." + time + "." + hour + ":" + minuts;
        return result;
    }

    //获取当前年
    public static int getyear() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        return year;
    }

    //获取当前月
    public static int getmonth() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        return month;
    }

    //获取当前日
    public static int getday() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        return day;
    }

    //获取当前小时
    public static int gethours() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    //获取当前分钟
    public static int getminutes() {
        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.MINUTE);
        return minute;
    }
}

