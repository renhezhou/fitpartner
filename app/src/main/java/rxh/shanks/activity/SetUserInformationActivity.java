package rxh.shanks.activity;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.presenter.SetUserInformationPresenter;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.SetUserInformationView;

/**
 * Created by Administrator on 2016/8/1.
 * 用户第一次登录进入这个界面进行用户信息设置
 */
public class SetUserInformationActivity extends BaseActivity implements SetUserInformationView {

    @Bind(R.id.head_portrait)
    CircleImageView head_portrait;
    @Bind(R.id.user_name)
    TextView user_name;
    @Bind(R.id.nickname)
    EditText nick_name;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.reset_password)
    EditText reset_password;
    @Bind(R.id.next)
    Button next;
    @Bind(R.id.select_head)
    ImageView select_head;
    SetUserInformationPresenter setUserInformationPresenter;

    View view;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/rxh/" + "head_img.jpg";
    File imgFile;
    Dialog photochoosedialog;
    Button tuku, paizhao, quxiao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserInformationPresenter = new SetUserInformationPresenter(this);
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
                showphotochooseDialog();
                break;
            case R.id.next:
                setUserInformationPresenter.setuserinformation(nick_name.getText().toString(), password.getText().toString(), reset_password.getText().toString());
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
            setUserInformationPresenter.uploadheadportrait(imgFile);
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
                    setUserInformationPresenter.uploadheadportrait(imgFile);
                } else {
                    Toast.makeText(getApplicationContext(), "发生未知错误", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                head_portrait.setImageBitmap(getDrawable(uri_camera));
                setUserInformationPresenter.uploadheadportrait(imgFile);
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
        startActivity(new Intent(getApplicationContext(), SetUserInformationNextActivity.class));
        finish();
    }

    @Override
    public void onError(Throwable ex) {
        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void check(String check) {
        Toast.makeText(getApplicationContext(), check, Toast.LENGTH_LONG).show();
    }


    private void showphotochooseDialog() {
        view = getLayoutInflater().inflate(R.layout.activity_set_user_information_head_portrait, null);
        photochoosedialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        photochoosedialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
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
