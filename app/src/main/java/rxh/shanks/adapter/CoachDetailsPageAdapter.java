package rxh.shanks.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.fragment.DataFragment;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CoachDetailsPageAdapter extends FragmentPagerAdapter {

    String descrip = null;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();


    public CoachDetailsPageAdapter(FragmentManager fm, List<Fragment> fragments, String descrip) {
        super(fm);
        this.fragments = fragments;
        this.descrip = descrip;
        list_title.add("资料");
        list_title.add("私教");
        list_title.add("团体课");
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return DataFragment.newInstance(descrip);
        } else {
            return fragments.get(position);
        }
    }

    @Override
    public int getCount() {
        return list_title.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return list_title.get(position % list_title.size());
    }

}
