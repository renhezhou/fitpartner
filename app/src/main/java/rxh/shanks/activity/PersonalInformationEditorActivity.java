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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.presenter.PersonalInformationEditorPresenter;
import rxh.shanks.utils.MyApplication;
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

    View view;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/rxh/" + "head_img.jpg";
    File imgFile;
    Dialog photochoosedialog;
    Button tuku, paizhao, quxiao;

    PersonalInformationEditorPresenter personalInformationEditorPresenter;

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
                showphotochooseDialog();
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
            case R.id.tuku:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");// 相片类型
                startActivityForResult(intent, 2);
                photochoosedialog.dismiss();
                break;
            case R.id.paizhao:
                Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(getImageByCamera, 3);
                photochoosedialog.dismiss();
                break;
            case R.id.quxiao:
                photochoosedialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2) {
            photochoosedialog.dismiss();
            // 选择图片Uri
            Uri uri = data.getData();
            head_portrait.setImageBitmap(getDrawable(uri));
            personalInformationEditorPresenter.uploadheadportrait(imgFile);
        } else if (resultCode == RESULT_OK && requestCode == 3) {
            photochoosedialog.dismiss();
            Uri uri_camera = data.getData();
            if (uri_camera == null) {
                // use bundle to get data
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data"); // get
                    try {
                        saveMyBitmap(photo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    head_portrait.setImageBitmap(photo);
                    personalInformationEditorPresenter.uploadheadportrait(imgFile);
                } else {
                    Toast.makeText(getApplicationContext(), "发生未知错误", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                head_portrait.setImageBitmap(getDrawable(uri_camera));
                personalInformationEditorPresenter.uploadheadportrait(imgFile);
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
        finish();
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


    private void showphotochooseDialog() {
        view = getLayoutInflater().inflate(R.layout.activity_set_user_information_head_portrait, null);
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

        tuku = (Button) view.findViewById(R.id.tuku);
        tuku.setOnClickListener(this);
        paizhao = (Button) view.findViewById(R.id.paizhao);
        paizhao.setOnClickListener(this);
        quxiao = (Button) view.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(this);
    }

    /**
     * Bitmap对象保存味图片文件
     *
     * @param mBitmap
     * @throws IOException
     */
    public void saveMyBitmap(Bitmap mBitmap) throws IOException {
        imgFile = new File(path);
        imgFile.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(imgFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param uri
     * @return
     */

    private Bitmap getDrawable(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        imgFile = new File(img_path);
        Bitmap current_bitmap = BitmapFactory.decodeFile(img_path, getBitmapOption(2)); // 将图片的长和宽缩小味原来的1/2
        return current_bitmap;
    }

    /**
     * 剪切图片
     *
     * @param inSampleSize
     * @return
     */
    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

}
