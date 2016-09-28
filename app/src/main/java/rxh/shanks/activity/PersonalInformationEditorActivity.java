package rxh.shanks.activity;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import de.greenrobot.event.EventBus;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.eventity.PIEEBEntity;
import rxh.shanks.presenter.PersonalInformationEditorPresenter;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.utils.PicassoLoader;
import rxh.shanks.view.PersonalInformationEditorView;

/**
 * Created by Administrator on 2016/8/1.
 */
public class PersonalInformationEditorActivity extends BaseActivity implements PersonalInformationEditorView {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.edit)
    TextView edit;
    @Bind(R.id.head_portrait)
    CircleImageView head_portrait;
    @Bind(R.id.boy)
    ImageView boy;
    @Bind(R.id.girl)
    ImageView girl;
    @Bind(R.id.age)
    EditText age;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.nickname)
    EditText nickname;
    @Bind(R.id.phone_num)
    EditText phone_num;
    @Bind(R.id.muscle)
    TextView muscle;
    @Bind(R.id.fat)
    TextView fat;
    @Bind(R.id.shaping)
    TextView shaping;
    @Bind(R.id.address)
    EditText address;

    //sex 1代表男，0代表女；
    //fitness_needs 1代表增肌，2代表减脂，3代表塑形
    String sex = null, fitness_needs = null;
    boolean muscle_flag = false, fat_flag = false, shaping_flag = false;
    PersonalInformationEditorPresenter personalInformationEditorPresenter;

    List<String> path = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personalInformationEditorPresenter = new PersonalInformationEditorPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_personal_information_editor);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        edit.setOnClickListener(this);
        head_portrait.setOnClickListener(this);
        boy.setOnClickListener(this);
        girl.setOnClickListener(this);
        muscle.setOnClickListener(this);
        fat.setOnClickListener(this);
        shaping.setOnClickListener(this);
        setenabled(false);
        initdata();
    }

    @SuppressLint("NewApi")
    public void initdata() {
        Glide
                .with(getApplicationContext())
                .load(MyApplication.QNDownToken)
                .centerCrop()
                .crossFade()
                .into(head_portrait);
        title.setText(MyApplication.realName);
        age.setText(MyApplication.age);
        name.setText(MyApplication.userName);
        nickname.setText(MyApplication.nickName);
        phone_num.setText(MyApplication.phoneNumber);
        address.setText(MyApplication.address);
        if (MyApplication.sex.equals("1")) {
            sex = "1";
            boy.setBackground(getResources().getDrawable(R.drawable.nandown));
            girl.setBackground(getResources().getDrawable(R.drawable.nvup));
        } else {
            sex = "0";
            boy.setBackground(getResources().getDrawable(R.drawable.nanup));
            girl.setBackground(getResources().getDrawable(R.drawable.nvdown));
        }
        //fitTarget格式为1_4类型的
        String str = MyApplication.fitTarget;
        if (str != null) {
            String[] strs = str.split("_");
            for (int i = 0; i < strs.length; i++) {
                if (strs[i].equals("1")) {
                    muscle_flag = true;
                    muscle.setBackground(getResources().getDrawable(R.drawable.muscle_down));
                    muscle.setTextColor(getResources().getColor(R.color.white));
                } else if (strs[i].equals("2")) {
                    fat_flag = true;
                    fat.setBackground(getResources().getDrawable(R.drawable.fat_down));
                    fat.setTextColor(getResources().getColor(R.color.white));
                } else if (strs[i].equals("3")) {
                    shaping_flag = true;
                    shaping.setBackground(getResources().getDrawable(R.drawable.shaping_down));
                    shaping.setTextColor(getResources().getColor(R.color.white));
                }
            }
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.edit:
                if (edit.getText().toString().equals("编辑")) {
                    edit.setText("提交");
                    setenabled(true);
                } else {
                    if (muscle_flag) {
                        fitness_needs = "1";
                        if (fat_flag) {
                            fitness_needs = fitness_needs + "_2";
                            if (shaping_flag) {
                                fitness_needs = fitness_needs + "_3";
                            }
                        }
                    } else {
                        if (fat_flag) {
                            fitness_needs = "2";
                            if (shaping_flag) {
                                fitness_needs = fitness_needs + "_3";
                            }
                        } else {
                            if (shaping_flag) {
                                fitness_needs = "3";
                            }
                        }
                    }
                    personalInformationEditorPresenter.setuserinformationnext(sex, age.getText().toString(), nickname.getText().toString(), phone_num.getText().toString(),
                            address.getText().toString(), fitness_needs);
                }
                break;
            case R.id.head_portrait:
                if (edit.getText().toString().equals("提交")) {
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
                    ImageSelector.open(PersonalInformationEditorActivity.this, imageConfig);   // 开启图片选择器
                }
                break;
            case R.id.boy:
                sex = "1";
                boy.setBackground(getResources().getDrawable(R.drawable.nandown));
                girl.setBackground(getResources().getDrawable(R.drawable.nvup));
                break;
            case R.id.girl:
                sex = "0";
                boy.setBackground(getResources().getDrawable(R.drawable.nanup));
                girl.setBackground(getResources().getDrawable(R.drawable.nvdown));
                break;
            case R.id.muscle:
                if (muscle_flag) {
                    muscle_flag = false;
                    muscle.setBackground(getResources().getDrawable(R.drawable.muscle_down));
                    muscle.setTextColor(getResources().getColor(R.color.white));
                } else {
                    muscle_flag = true;
                    muscle.setBackground(getResources().getDrawable(R.drawable.muscle_flag_up));
                    muscle.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.fat:
                if (fat_flag) {
                    fat_flag = false;
                    fat.setBackground(getResources().getDrawable(R.drawable.fat_down));
                    fat.setTextColor(getResources().getColor(R.color.white));
                } else {
                    fat_flag = true;
                    fat.setBackground(getResources().getDrawable(R.drawable.fat_up));
                    fat.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.shaping:
                if (shaping_flag) {
                    shaping_flag = false;
                    shaping.setBackground(getResources().getDrawable(R.drawable.shaping_down));
                    shaping.setTextColor(getResources().getColor(R.color.white));
                } else {
                    shaping_flag = true;
                    shaping.setBackground(getResources().getDrawable(R.drawable.shaping_up));
                    shaping.setTextColor(getResources().getColor(R.color.black));
                }
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
                        .into(head_portrait);
            }
        }
    }

    @Override
    public void show() {
        loading("上传中", "true");
    }

    @Override
    public void hide() {
        dismiss();
    }

    @Override
    public void success() {
        MyApplication.fitTarget = fitness_needs;
        MyApplication.sex = sex;
        MyApplication.age = age.getText().toString();
        if (path.size() > 0) {
            uploadheadportrait(path.get(0));
        } else {
            dismiss();
            Toast.makeText(getApplicationContext(), "资料上传成功", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void setenabled(boolean flag) {
        age.setEnabled(flag);
        name.setEnabled(flag);
        nickname.setEnabled(flag);
        phone_num.setEnabled(flag);
        address.setEnabled(flag);
        boy.setClickable(flag);
        girl.setClickable(flag);
        muscle.setClickable(flag);
        fat.setClickable(flag);
        shaping.setClickable(flag);
    }

    public void uploadheadportrait(final String path) {
        File imgfile = new File(path);
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(imgfile, MyApplication.headImageURL, MyApplication.QNUPToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        dismiss();
                        if (info.isOK()) {
                            EventBus.getDefault().post(new PIEEBEntity(path, name.getText().toString()));
                            Toast.makeText(getApplicationContext(), "头像上传成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "头像上传失败", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                }, null);
    }

}
