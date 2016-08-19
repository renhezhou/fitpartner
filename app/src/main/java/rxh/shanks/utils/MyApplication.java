package rxh.shanks.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.BuildConfig;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import rxh.shanks.provider.MyTextMessageItemProvider;

/**
 * Created by Administrator on 2016/4/11.
 */
public class MyApplication extends Application {

    public static String token = null;
    public static String QNUPToken = null;
    public static String userID = null;
    public static String realName = null;
    public static String nickName = null;
    public static String headImageURL = null;
    public static String currentClubID = null;
    public static String currentClubName = null;
    //定义当前首选会员卡
    public static String defaultmembercard = null;
    //定义教练ID，在教练详情界面，团课预约界面需要用到
    public static String CoachID = null;
    //定义课程ID，在团课未预约，去预约界面需要用到
    public static String lessonID = null;
    //定义一些个人中心界面需要用到的数据
    public static String address = null;
    public static String age = null;
    public static String phoneNumber = null;
    public static String sex = null;
    public static String fitTarget = null;
    public static String userName = null;
    public static String QNDownToken = null;
    //定义融云需要用到的字段
    public static String imToken = null;


    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        //初始化Fresco
        Fresco.initialize(this);

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

        //融云初始化
        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
            //设置聊天界面头像不显示
            RongIM.getInstance().registerMessageTemplate(new MyTextMessageItemProvider());

        }
    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
