package rxh.shanks.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.fragment.ConfirmAnAppointmentFragment;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ConfirmAnAppointmentPageAdapter extends FragmentPagerAdapter {

    String lessonID;
    private List<String> date = new ArrayList<>();
    private List<String> fragmentdatelist = new ArrayList<>();

    public ConfirmAnAppointmentPageAdapter(FragmentManager fm, List<String> date, List<String> fragmentdatelist, String lessonID) {
        super(fm);
        this.date = date;
        this.fragmentdatelist = fragmentdatelist;
        this.lessonID = lessonID;
    }

    @Override
    public Fragment getItem(int position) {
        return ConfirmAnAppointmentFragment.newInstance(fragmentdatelist.get(position), lessonID);
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
