package rxh.shanks.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.fragment.ConfirmAnAppointmentFragment;
import rxh.shanks.fragment.CourseDetailsFragment;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CourseDetailsPageAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<String> date = new ArrayList<>();
    private List<String> seclet = new ArrayList<>();

    public CourseDetailsPageAdapter(FragmentManager fm, Context context, List<String> date, List<String> seclet) {
        super(fm);
        this.context = context;
        this.date = date;
        this.seclet = seclet;
    }

    @Override
    public Fragment getItem(int position) {
        return CourseDetailsFragment.newInstance(seclet.get(position));
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
