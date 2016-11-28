package rxh.shanks.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.fragment.PrivateEducationCourseFragment;

/**
 * Created by Administrator on 2016/8/3.
 */
public class PrivateEducationCoursePageAdapter extends FragmentPagerAdapter {

    String lessonID, time;
    private List<String> date = new ArrayList<>();
    private List<String> fragmentdatelist = new ArrayList<>();

    public PrivateEducationCoursePageAdapter(FragmentManager fm, List<String> date, List<String> fragmentdatelist, String lessonID, String time) {
        super(fm);
        this.date = date;
        this.fragmentdatelist = fragmentdatelist;
        this.lessonID = lessonID;
        this.time = time;
    }

    @Override
    public Fragment getItem(int position) {
        return PrivateEducationCourseFragment.newInstance(lessonID, fragmentdatelist.get(position), time);
    }

    @Override
    public int getCount() {
        return date.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return date.get(position);
    }

    @Override
    public long getItemId(int position) {
        // 获取当前数据的hashCode
        int hashCode = date.get(position).hashCode();
        return hashCode;
    }

}
