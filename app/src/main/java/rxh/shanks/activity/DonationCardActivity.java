package rxh.shanks.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.adapter.DonationCardGVAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.entity.DonationCardEntity;
import rxh.shanks.presenter.DonationCardPresenter;
import rxh.shanks.utils.CreatTime;
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
    @Bind(R.id.prompt)
    TextView prompt;
    @Bind(R.id.residual_frequency)
    TextView residual_frequency;
    @Bind(R.id.generate_two_dimensional_code)
    TextView generate_two_dimensional_code;
    @Bind(R.id.gv)
    GridView gv;

    Bitmap mBitmap;

    View view;
    Dialog photochoosedialog;
    private ImageView cancel, QR_code;
    private TextView card_number, share, save;


    String cardID, clubID, cardType, totalCount;

    DonationCardPresenter donationCardPresenter;
    DonationCardGVAdapter donationCardGVAdapter;
    List<DonationCardEntity> data = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        donationCardPresenter = new DonationCardPresenter(this);
        cardID = getIntent().getStringExtra("cardID");
        clubID = getIntent().getStringExtra("clubID");
        cardType = getIntent().getStringExtra("cardType");
        totalCount = getIntent().getStringExtra("totalCount");
        initview();
        donationCardPresenter.gitSurplusGiftQR(cardID);
    }

    public void initview() {
        setContentView(R.layout.activity_donation_card);
        ButterKnife.bind(this);
        title.setText("转赠次卡");
        prompt.setText("每张二维码仅限单人单次使用\n使用后立即失效\n请谨慎截图转发");
        residual_frequency.setText("剩余次数\n" + totalCount + "次");
        back.setOnClickListener(this);
        generate_two_dimensional_code.setOnClickListener(this);
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
            case R.id.generate_two_dimensional_code:
                donationCardPresenter.giftQR(clubID, cardID, cardType);
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
        donationCardGVAdapter = new DonationCardGVAdapter(getApplicationContext(), data);
        gv.setAdapter(donationCardGVAdapter);
    }

    @Override
    public void giftQR(List<DonationCardEntity> donationCardEntityList) {
        if (donationCardEntityList.size() > 0) {
            residual_frequency.setText("剩余次数\n" + donationCardEntityList.get(0).getCount() + "次");
        }
        if (donationCardEntityList != null) {
            data.clear();
            data = donationCardEntityList;
            donationCardGVAdapter = new DonationCardGVAdapter(getApplicationContext(), data);
            gv.setAdapter(donationCardGVAdapter);
            showphotochooseDialog();
            card_number.setText("次卡编号为:" + data.get(0).getCodeID());
            mBitmap = CodeUtils.createImage(data.get(0).getQRString(), 400, 400, null);
            QR_code.setImageBitmap(mBitmap);
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

        cancel = (ImageView) view.findViewById(R.id.cancel);
        QR_code = (ImageView) view.findViewById(R.id.QR_code);
        card_number = (TextView) view.findViewById(R.id.card_number);
        share = (TextView) view.findViewById(R.id.share);
        save = (TextView) view.findViewById(R.id.save);
        cancel.setOnClickListener(this);
        share.setOnClickListener(this);
        save.setOnClickListener(this);
    }

}
