package rxh.shanks.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxh.shanks.adapter.FitnessCalendarAdapter;
import rxh.shanks.base.BaseActivity;
import rxh.shanks.customview.ListViewForScrollView;
import rxh.shanks.datepicker.bizs.calendars.DPCManager;
import rxh.shanks.datepicker.bizs.decors.DPDecor;
import rxh.shanks.datepicker.cons.DPMode;
import rxh.shanks.datepicker.views.DatePicker;
import rxh.shanks.entity.FitnessCalendarEntity;
import rxh.shanks.entity.FitnessCalendarLessonEntity;
import rxh.shanks.entity.FitnessCalendarTimeEntity;
import rxh.shanks.presenter.FitnessCalendarPresenter;
import rxh.shanks.utils.CheckUtils;
import rxh.shanks.utils.CreatTime;
import rxh.shanks.view.FitnessCalendarView;

/**
 * Created by Administrator on 2016/8/2.
 * 健身日历
 */
public class FitnessCalendarActivity extends BaseActivity implements FitnessCalendarView {


    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.datepicker)
    DatePicker date_picker;
    @Bind(R.id.lv)
    ListView lv;

    List<String> tmpTR = new ArrayList<>();

    String check_date = null;
    private List<FitnessCalendarEntity> noteDatedata = new ArrayList<>();
    private List<FitnessCalendarLessonEntity> lessondata = new ArrayList<>();
    FitnessCalendarAdapter fitnessCalendarAdapter;
    FitnessCalendarPresenter fitnessCalendarPresenter;
    List<FitnessCalendarTimeEntity> fitnessCalendarTimeEntityLists = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fitnessCalendarPresenter = new FitnessCalendarPresenter(this);
        setContentView(R.layout.activity_first);
        fitnessCalendarPresenter.getFitCalender();
    }

    public void initview() {
        setContentView(R.layout.activity_fitness_calendar);
        ButterKnife.bind(this);
        title.setText("健身日历");
        back.setOnClickListener(this);
        lv.setEmptyView(findViewById(R.id.prompt));
        date_picker.setDate(CreatTime.getyear(), CreatTime.getmonth() + 1);
        date_picker.setMode(DPMode.SINGLE);
        DPCManager.getInstance().setDecorTR(tmpTR);
        date_picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorTR(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(getResources().getColor(R.color.red));
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 4, paint);
            }
        });
        date_picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                check_date = date;
                fitnessCalendarPresenter.getFitCalender();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void getFitCalenders(List<FitnessCalendarTimeEntity> fitnessCalendarTimeEntityList) {
        fitnessCalendarTimeEntityLists = fitnessCalendarTimeEntityList;
        if (check_date == null) {
            getnoteDatess(CheckUtils.getdate());
        } else {
            getnoteDatess(check_date);
        }

    }


    @Override
    public void onFinished(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }


    public void getnoteDatess(String date) {
        String a[] = date.split("-");
        String time = null;
        if (Integer.parseInt(a[1]) < 10) {
            if (Integer.parseInt(a[2]) < 10) {
                time = a[0] + ".0" + a[1] + ".0" + a[2];
            } else {
                time = a[0] + ".0" + a[1] + "." + a[2];
            }
        } else {
            if (Integer.parseInt(a[2]) < 10) {
                time = a[0] + "." + a[1] + ".0" + a[2];
            } else {
                time = a[0] + "." + a[1] + "." + a[2];
            }
        }
        if (noteDatedata != null) {
            noteDatedata.clear();
        }
        if (lessondata != null) {
            lessondata.clear();
        }
        for (int i = 0; i < fitnessCalendarTimeEntityLists.size(); i++) {
            if (fitnessCalendarTimeEntityLists.get(i).getNoteDate() != null || fitnessCalendarTimeEntityLists.get(i).getLesson() != null) {
                String month = null, day = null;
                String ab[] = fitnessCalendarTimeEntityLists.get(i).getNoteTime().split("\\.");
                if (ab[1].substring(0, 1).equals("0")) {
                    month = ab[1].replace("0", "");
                } else {
                    month = ab[1];
                }
                if (ab[2].substring(0, 1).equals("0")) {
                    day = ab[2].replace("0", "");
                } else {
                    day = ab[2];
                }
                tmpTR.add(ab[0] + "-" + month + "-" + day);
            }
            if (time.equals(fitnessCalendarTimeEntityLists.get(i).getNoteTime())) {
                noteDatedata = fitnessCalendarTimeEntityLists.get(i).getNoteDate();
                lessondata = fitnessCalendarTimeEntityLists.get(i).getLesson();
            }
        }
        initview();
        fitnessCalendarAdapter = new FitnessCalendarAdapter(FitnessCalendarActivity.this, noteDatedata, lessondata);
        lv.setAdapter(fitnessCalendarAdapter);
    }
}
