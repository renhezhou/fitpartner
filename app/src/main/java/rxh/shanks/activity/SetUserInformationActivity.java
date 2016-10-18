package rxh.shanks.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.presenter.SetUserInformationPresenter;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.utils.PicassoLoader;
import rxh.shanks.view.SetUserInformationView;

/**
 * Created by Administrator on 2016/8/1.
 * 用户第一次登录进入这个界面进行用户信息设置
 */
public class SetUserInformationActivity extends BaseActivity implements SetUserInformationView {

    @Bind(R.id.user_name)
    EditText user_name;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.reset_password)
    EditText reset_password;
    @Bind(R.id.next)
    Button next;
    @Bind(R.id.select_head)
    CircleImageView select_head;
    SetUserInformationPresenter presenter;

    List<String> path = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SetUserInformationPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_set_user_information);
        ButterKnife.bind(this);
        next.setOnClickListener(this);
        select_head.setOnClickListener(this);
        user_name.setText(MyApplication.realName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_head:
                ImageConfig imageConfig
                        = new ImageConfig.Builder(new PicassoLoader())
                        .steepToolBarColor(getResources().getColor(R.color.red))
                        .titleBgColor(getResources().getColor(R.color.red))
                        .titleSubmitTextColor(getResources().getColor(R.color.white))
                        .titleTextColor(getResources().getColor(R.color.white))
                        // 开启单选   （默认为多选）
                        .singleSelect()
                        // 开启拍照功能 （默认关闭）
                        .showCamera()
                        // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                        .filePath("/ImageSelector/Pictures")
                        .build();
                ImageSelector.open(SetUserInformationActivity.this, imageConfig);   // 开启图片选择器
                break;
            case R.id.next:
                presenter.setuserinformation(user_name.getText().toString(), password.getText().toString(), reset_password.getText().toString());
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // 获取返回的图片列表
            path = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            if (path.size() > 0) {
                Glide.with(getApplicationContext())
                        .load(path.get(0))
                        .centerCrop()
                        .into(select_head);
            }
        }
    }


    @Override
    public void show() {
        loading("上传中...", "ture");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void onSuccess() {
        MyApplication.userName = user_name.getText().toString();
        if (path.size() > 0) {
            uploadheadportrait(new File(path.get(0)));
        } else {
            dismiss();
            startActivity(new Intent(getApplicationContext(), SetUserInformationNextActivity.class));
            finish();
        }
    }

    @Override
    public void onError(Throwable ex) {
        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void check(String check) {
        Toast.makeText(getApplicationContext(), check, Toast.LENGTH_LONG).show();
    }

    public void uploadheadportrait(File file) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(file, MyApplication.headImageURL, MyApplication.QNUPToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        if (info.isOK()) {
                            dismiss();
                            startActivity(new Intent(getApplicationContext(), SetUserInformationNextActivity.class));
                            finish();
                        } else {
                            dismiss();
                            Toast.makeText(SetUserInformationActivity.this, "头像上传失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, null);
    }

}
