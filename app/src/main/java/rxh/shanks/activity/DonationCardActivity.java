package rxh.shanks.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import rxh.shanks.EBEntity.DCEntity;
import rxh.shanks.adapter.DonationCardGVAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.DonationCardEntity;
import rxh.shanks.presenter.DonationCardPresenter;
import rxh.shanks.utils.ScreenShotTools;
import rxh.shanks.view.DonationCardView;

/**
 * Created by Administrator on 2016/8/12.
 * 转赠次卡
 */
public class DonationCardActivity extends BaseActivity implements DonationCardView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.surplus)
    TextView surplus;
    @Bind(R.id.generate_code)
    TextView generate_code;
    @Bind(R.id.gv)
    GridView gv;

    Bitmap mBitmap;
    int selected_position = -1;

    View view;
    Dialog photochoosedialog;
    private ImageView QR_code;
    private TextView cancel, card_number, share, save;


    String cardID, clubID, surplus_String;
    int surpluscount;
    DonationCardPresenter presenter;
    DonationCardGVAdapter adapter;
    List<DonationCardEntity> data = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DonationCardPresenter(this);
        cardID = getIntent().getStringExtra("cardID");
        clubID = getIntent().getStringExtra("clubID");
        surplus_String = getIntent().getStringExtra("surplus");
        surpluscount = Integer.parseInt(surplus_String);
        initview();
        presenter.gitSurplusGiftQR(cardID);
    }

    public void initview() {
        setContentView(R.layout.activity_donation_card);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        generate_code.setOnClickListener(this);
        title.setText("转赠次卡");
        surplus.setText("剩余：" + surpluscount + "次");
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showphotochooseDialog();
                card_number.setText("次卡编号为:" + data.get(position).getCodeID());
                mBitmap = CodeUtils.createImage(data.get(position).getQRString(), 400, 400, null);
                QR_code.setImageBitmap(mBitmap);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.generate_code:
                if (surpluscount > 0) {
                    presenter.giftQR(clubID, cardID);
                } else {
                    Toast.makeText(getApplicationContext(), "次数不足", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel:
                photochoosedialog.dismiss();
                break;
            case R.id.share:
                Toast.makeText(getApplicationContext(), "功能未开放，敬请期待", Toast.LENGTH_LONG).show();
                break;
            case R.id.save:
                boolean result = ScreenShotTools.shotBitmap(this);
                if (result) {
                    Toast.makeText(getApplicationContext(), "二维码保存成功", Toast.LENGTH_LONG).show();
                    photochoosedialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void gitSurplusGiftQR(List<DonationCardEntity> donationCardEntityList) {
        data = donationCardEntityList;
        adapter = new DonationCardGVAdapter(getApplicationContext(), data, selected_position);
        gv.setAdapter(adapter);
    }

    @Override
    public void giftQR(List<DonationCardEntity> donationCardEntityList) {
        EventBus.getDefault().post(new DCEntity());
        surpluscount--;
        surplus.setText("剩余：" + surpluscount + "次");
        if (donationCardEntityList.size() > 0) {

        }
        if (donationCardEntityList != null) {
            data.clear();
            data = donationCardEntityList;
            adapter = new DonationCardGVAdapter(getApplicationContext(), data, selected_position);
            gv.setAdapter(adapter);
        }
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void showphotochooseDialog() {
        view = getLayoutInflater().inflate(R.layout.activity_donation_card_pop_window, null);
        photochoosedialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        photochoosedialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = photochoosedialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        photochoosedialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        photochoosedialog.setCanceledOnTouchOutside(true);
        photochoosedialog.show();
        cancel = (TextView) view.findViewById(R.id.cancel);
        QR_code = (ImageView) view.findViewById(R.id.QR_code);
        card_number = (TextView) view.findViewById(R.id.card_number);
        share = (TextView) view.findViewById(R.id.share);
        save = (TextView) view.findViewById(R.id.save);
        cancel.setOnClickListener(this);
        share.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    //截屏实现类
    private void screenshot() {
        // 获取屏幕
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                long currentTime = System.currentTimeMillis();
                SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
                Date date = new Date(currentTime);
                String filePath = sdCardPath + File.separator + "fitparther/" + format.format(date) + ".png";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
            }
        }
    }

}
