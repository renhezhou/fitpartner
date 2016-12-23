package rxh.shanks.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.CircleImageView;
import rxh.shanks.presenter.SetUserInformationNextPresenter;
import rxh.shanks.utils.MyApplication;
import rxh.shanks.view.SetUserInformationNextView;

/**
 * Created by Administrator on 2016/8/1.
 */
public class SetUserInformationNextActivity extends BaseActivity implements SetUserInformationNextView {


    @Bind(R.id.head_portrait)
    CircleImageView head_portrait;
    @Bind(R.id.male)
    ImageView male;
    @Bind(R.id.NV)
    ImageView NV;
    @Bind(R.id.nick_name)
    EditText nick_name;
    @Bind(R.id.real_name)
    EditText real_name;
    @Bind(R.id.ID_card)
    EditText ID_card;
    @Bind(R.id.contact_address)
    EditText contact_address;
    @Bind(R.id.muscle)
    TextView muscle;
    @Bind(R.id.fat)
    TextView fat;
    @Bind(R.id.shaping)
    TextView shaping;
    @Bind(R.id.next)
    Button next;

    SetUserInformationNextPresenter presenter;
    //sex 1代表男，0代表女；
    //fitness_needs 1代表增肌，2代表减脂，3代表塑形
    String sex = null, fitness_needs = null;
    boolean muscle_flag = false, fat_flag = false, shaping_flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SetUserInformationNextPresenter(this);
        initview();
    }

    public void initview() {
        setContentView(R.layout.activity_set_user_information_next);
        ButterKnife.bind(this);
        muscle.setOnClickListener(this);
        fat.setOnClickListener(this);
        shaping.setOnClickListener(this);
        next.setOnClickListener(this);
        initdata();
    }

    @SuppressLint("NewApi")
    public void initdata() {
        Picasso.with(getApplicationContext())
                .load(MyApplication.QNDownToken)
                .placeholder(R.drawable.loading_cort)
                .error(R.drawable.loading_cort)
                .into(head_portrait);
        if (MyApplication.sex.equals("1")) {
            sex = "1";
            Picasso.with(getApplicationContext()).load(R.drawable.nandown).into(male);
            Picasso.with(getApplicationContext()).load(R.drawable.nvup).into(NV);
        } else {
            sex = "0";
            Picasso.with(getApplicationContext()).load(R.drawable.nanup).into(male);
            Picasso.with(getApplicationContext()).load(R.drawable.nvdown).into(NV);
        }
        if (MyApplication.nickName != null) {
            nick_name.setText(MyApplication.nickName);
            nick_name.setEnabled(false);
        } else {
            nick_name.setEnabled(true);
        }
        if (MyApplication.realName != null) {
            real_name.setText(MyApplication.realName);
            real_name.setEnabled(false);
        } else {
            real_name.setEnabled(true);
        }
        if (MyApplication.IDcardNumber != null) {
            ID_card.setText(MyApplication.IDcardNumber);
            ID_card.setEnabled(false);
        } else {
            ID_card.setEnabled(true);
        }
        if (MyApplication.address != null) {
            contact_address.setText(MyApplication.address);
            contact_address.setEnabled(false);
        } else {
            contact_address.setEnabled(true);
        }
        muscle.setBackground(getResources().getDrawable(R.drawable.muscle_flag_up));
        muscle.setTextColor(getResources().getColor(R.color.textcolor));
        fat.setBackground(getResources().getDrawable(R.drawable.fat_up));
        fat.setTextColor(getResources().getColor(R.color.textcolor));
        shaping.setBackground(getResources().getDrawable(R.drawable.shaping_up));
        shaping.setTextColor(getResources().getColor(R.color.textcolor));
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.muscle:
                if (muscle_flag) {
                    muscle_flag = false;
                    muscle.setBackground(getResources().getDrawable(R.drawable.muscle_down));
                    muscle.setTextColor(getResources().getColor(R.color.white));
                } else {
                    muscle_flag = true;
                    muscle.setBackground(getResources().getDrawable(R.drawable.muscle_flag_up));
                    muscle.setTextColor(getResources().getColor(R.color.textcolor));
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
                    fat.setTextColor(getResources().getColor(R.color.textcolor));
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
                    shaping.setTextColor(getResources().getColor(R.color.textcolor));
                }
                break;
            case R.id.next:
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
                presenter.setuserinformationnext(sex, ID_card.getText().toString(), nick_name.getText().toString(),
                        contact_address.getText().toString(), fitness_needs);
                break;
            default:
                break;

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
        MyApplication.nickName = nick_name.getText().toString();
        MyApplication.address = contact_address.getText().toString();
        MyApplication.fitTarget = fitness_needs;
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    public void onError(Throwable ex) {

    }

    @Override
    public void check(String check) {
        Toast.makeText(getApplicationContext(), check, Toast.LENGTH_LONG).show();
    }
}
