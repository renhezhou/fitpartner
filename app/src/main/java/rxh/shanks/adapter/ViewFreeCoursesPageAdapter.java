package rxh.shanks.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.fragment.CourseDetailsFragment;
import rxh.shanks.fragment.ViewFreeCoursesFragment;

/**
 * Created by Administrator on 2016/8/16.
 */
public class ViewFreeCoursesPageAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<String> date = new ArrayList<>();
    private List<String> seclet = new ArrayList<>();
    String clubID;

    public ViewFreeCoursesPageAdapter(FragmentManager fm, Context context, List<String> date, List<String> seclet, String clubID) {
        super(fm);
        this.context = context;
        this.date = date;
        this.seclet = seclet;
        this.clubID = clubID;
    }

    @Override
    public Fragment getItem(int position) {
        return ViewFreeCoursesFragment.newInstance(seclet.get(position), clubID);
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
