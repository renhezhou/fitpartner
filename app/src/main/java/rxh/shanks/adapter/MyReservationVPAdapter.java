package rxh.shanks.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.activity.R;
import rxh.shanks.customview.SpringProgressView;
import rxh.shanks.entity.MyPrivateTeachingAppointmentEntity;
import rxh.shanks.fragment.AlreadyMakeAnAppointmentFragment;
import rxh.shanks.fragment.NotMakeAnAppointmentFragment;

/**
 * Created by Administrator on 2016/11/7.
 */
public class MyReservationVPAdapter extends FragmentPagerAdapter {


    String flag;
    public FragmentManager fm;
    public List<Fragment> list = new ArrayList<Fragment>();


    public MyReservationVPAdapter(FragmentManager fm, String flag, List<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.flag = flag;
        this.list = fragments;
    }

    @Override
    public Fragment getItem(int arg0) {
        if (arg0 == 0) {
            return AlreadyMakeAnAppointmentFragment.newInstance(flag);
        } else {
            return NotMakeAnAppointmentFragment.newInstance(flag);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fm.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        Fragment fragment = list.get(position);
        fm.beginTransaction().hide(fragment).commit();
    }


}
