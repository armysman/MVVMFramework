package gnnt.practice.dailypage.ui.week;

import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import gnnt.mebs.base.component.BaseFragment;
import gnnt.practice.dailypage.R;
import gnnt.practice.dailypage.ui.welcome.WelcomeViewModel;

/**********************************************************
 *  WeekFragment.java  2019-07-10
 *  <p>
 *  描述
 *  </p>
 *  Copyright2019 by GNNT Company. All Rights Reserved.
 *
 *  @author:shuhj
 ***********************************************************/
public class WeekFragment extends BaseFragment<WeekViewModel> {

    public static final String TAG = "WeekFragment";

    /*@BindView(R.id.ck_time)
    CheckBox ckTime;
    @BindView(R.id.ck_project)
    CheckBox ckProject;*/
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    private List<Fragment> mFragments;
    private String[] mTitles = {"   本周工作   ", "   上周工作   "};
    private MyAdapter mAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_week;
    }

    @NonNull
    @Override
    protected Class getViewModelClass() {
        return WeekViewModel.class;
    }

    @Override
    protected void setupView(View rootView) {
        super.setupView(rootView);
        mFragments = new ArrayList<>();
        mFragments.add(WeekChildFragment.getWeekChildFragment("",WeekChildFragment.THIS_WEEK));
        mFragments.add(WeekChildFragment.getWeekChildFragment("",WeekChildFragment.LAST_WEEK));
        mAdapter = new MyAdapter(getChildFragmentManager());
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void setupViewModel(@NonNull WeekViewModel viewModel) {
        super.setupViewModel(viewModel);
    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
