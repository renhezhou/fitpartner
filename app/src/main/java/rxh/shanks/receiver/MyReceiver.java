package rxh.shanks.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;
import rxh.shanks.EBEntity.PEntity;
import rxh.shanks.activity.LoginActivity;
import rxh.shanks.activity.SystemNextActivity;
import rxh.shanks.activity.SystemSecondActivity;
import rxh.shanks.entity.PaymentReceiverCodeEntity;
import rxh.shanks.entity.ReceiverEntity;
import rxh.shanks.utils.JsonUtils;

/**
 * Created by Administrator on 2016/8/16.
 * 极光推送监听
 */
public class MyReceiver extends BroadcastReceiver {

    private SharedPreferences sp;

    @Override
    public void onReceive(Context context, Intent intent) {
        sp = context.getSharedPreferences("user_info", 0);
        Bundle bundle = intent.getExtras();
        //Log.e("", "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            //接收推送的id
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            //接收到推送下来的自定义消息
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_TITLE);
            PaymentReceiverCodeEntity entity = new PaymentReceiverCodeEntity();
            entity = JsonUtils.get_pr(message);
            if (entity.getType().equals("paidcardOrder")) {
                EventBus.getDefault().post(new PEntity(0, entity.getOrder().getId(), entity.getOrder().getPrice()));
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            //接收到推送下来的通知
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            ReceiverEntity entity = new ReceiverEntity();
            entity = JsonUtils.get_receiver_info(extra);
            SharedPreferences.Editor editor = sp.edit();
            //上课提醒
            int RemindLessonToUser_num = sp.getInt("RemindLessonToUser", 0);
            //教练代约团课
            int ReplaceGroupClassToUser_num = sp.getInt("ReplaceGroupClassToUser", 0);
            //教练代约私教课
            int ReplaceOrderLessonToUser_num = sp.getInt("ReplaceOrderLessonToUser", 0);
            //会员卡即将到期
            int CardCloseExpiredToUser_num = sp.getInt("CardCloseExpiredToUser", 0);
            //新增优惠券
            int SendCouponToUser_num = sp.getInt("SendCouponToUser", 0);
            //课程扣除提醒
            int DeductionLessonToUser_num = sp.getInt("DeductionLessonToUser", 0);
            //场馆通知
            int NormalMessageToUser_num = sp.getInt("NormalMessageToUser", 0);
            //场馆活动
            int EventMessageToUser_num = sp.getInt("EventMessageToUser", 0);
            //系统通知
            int DevelopSystemMessage_num = sp.getInt("DevelopSystemMessage", 0);
            if (entity.getType().equals("RemindLessonToUser")) {
                RemindLessonToUser_num++;
            } else if (entity.getType().equals("ReplaceGroupClassToUser")) {
                ReplaceGroupClassToUser_num++;
            } else if (entity.getType().equals("ReplaceOrderLessonToUser")) {
                ReplaceOrderLessonToUser_num++;
            } else if (entity.getType().equals("CardCloseExpiredToUser")) {
                CardCloseExpiredToUser_num++;
            } else if (entity.getType().equals("SendCouponToUser")) {
                SendCouponToUser_num++;
            } else if (entity.getType().equals("DeductionLessonToUser")) {
                DeductionLessonToUser_num++;
            } else if (entity.getType().equals("NormalMessageToUser")) {
                NormalMessageToUser_num++;
            } else if (entity.getType().equals("EventMessageToUser")) {
                EventMessageToUser_num++;
            } else if (entity.getType().equals("DevelopSystemMessage")) {
                DevelopSystemMessage_num++;
            }
            editor.putInt("RemindLessonToUser", RemindLessonToUser_num);
            editor.putInt("ReplaceGroupClassToUser", ReplaceGroupClassToUser_num);
            editor.putInt("ReplaceOrderLessonToUser", ReplaceOrderLessonToUser_num);
            editor.putInt("CardCloseExpiredToUser", CardCloseExpiredToUser_num);
            editor.putInt("SendCouponToUser", SendCouponToUser_num);
            editor.putInt("DeductionLessonToUser", DeductionLessonToUser_num);
            editor.putInt("NormalMessageToUser", NormalMessageToUser_num);
            editor.putInt("EventMessageToUser", EventMessageToUser_num);
            editor.putInt("DevelopSystemMessage", DevelopSystemMessage_num);
            editor.commit();
            //接收到推送下来的通知的ID:
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //用户点击打开了通知
            String title = bundle.getString(JPushInterface.EXTRA_ALERT);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            ReceiverEntity entity = new ReceiverEntity();
            entity = JsonUtils.get_receiver_info(extra);
            Intent i = new Intent();
            if (entity.getType().equals("ReplaceGroupClassToUser") || entity.getType().equals("ReplaceOrderLessonToUser")) {
                i.setClass(context, SystemNextActivity.class);
            } else {
                i.setClass(context, SystemSecondActivity.class);
            }
            i.putExtra("type", entity.getType());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
            //Log.e(".....", "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        } else {
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i("...", "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e("...", "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

}
