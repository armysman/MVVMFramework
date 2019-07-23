package gnnt.practice.dailypage.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import butterknife.BindView;
import gnnt.mebs.base.component.BaseActivity;
import gnnt.mebs.common.RouteMap;
import gnnt.mebs.common.util.SharedPreferenceUtils;
import gnnt.practice.dailypage.MemeoryData;
import gnnt.practice.dailypage.R;
import gnnt.practice.dailypage.ui.current.CurrentWorkFragment;
import gnnt.practice.dailypage.ui.history.HistoryFragment;
import gnnt.practice.dailypage.ui.week.WeekFragment;

@Route(path = RouteMap.DailyPage.MAIN)
public class MainActivity extends BaseActivity<MainViewMode> {


    private FragmentManager mFragmentManager;

    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.nav)
    NavigationView nav;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;

    private View mHeaderView;

    @Override
    protected int getStatusBarColor() {
        return R.color.colorPrimaryDark;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected Class<? extends MainViewMode> getViewModelClass() {
        return MainViewMode.class;
    }

    @Override
    protected void setupView() {
        super.setupView();
        mFragmentManager = getSupportFragmentManager();
        //nav view setting
        mHeaderView = nav.getHeaderView(0);
        TextView tvUsername = mHeaderView.findViewById(R.id.username);
        tvUsername.setText(SharedPreferenceUtils.getParam(this, "username", "***").toString());
        nav.setNavigationItemSelectedListener(mItemSelectedListener);
        //toolbar左侧按钮联动nav
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ///默认加载当前日报页面
        showFragment(new CurrentWorkFragment(), CurrentWorkFragment.TAG);
    }

    private NavigationView.OnNavigationItemSelectedListener mItemSelectedListener = (@NonNull MenuItem menuItem) -> {
        menuItem.setChecked(true);
        drawer.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.current:
                this.setTitle(menuItem.getTitle().toString());
                Fragment curFragment = mFragmentManager.findFragmentByTag(CurrentWorkFragment.TAG);
                if (curFragment == null) {
                    curFragment = new CurrentWorkFragment();
                }
                showFragment(curFragment, CurrentWorkFragment.TAG);
                return true;
            case R.id.history:
                this.setTitle(menuItem.getTitle().toString());
                Fragment hisFragment = mFragmentManager.findFragmentByTag(HistoryFragment.TAG);
                if (hisFragment == null) {
                    hisFragment = new HistoryFragment();
                }
                showFragment(hisFragment, HistoryFragment.TAG);
                return true;
            case R.id.week:
                Fragment weekFragment = mFragmentManager.findFragmentByTag(WeekFragment.TAG);
                if (weekFragment == null) {
                    weekFragment = new WeekFragment();
                }
                showFragment(weekFragment, WeekFragment.TAG);
                return true;
            case R.id.retain:
                return true;
            case R.id.change:
                showMessage("待开发...");
                return true;
            case R.id.logout:
                exit();
                return true;
            default:
                return false;
        }
    };

    private void setTitle(String title) {
        toolbar.setTitle(title);
    }

    private void showFragment(Fragment showFragment, String tag) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        List<Fragment> fragmentList = mFragmentManager.getFragments();
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                fragmentTransaction.hide(fragment);
            }
        }

        if (fragmentList != null && fragmentList.contains(showFragment)) {
            fragmentTransaction.show(showFragment);
        } else {
            fragmentTransaction.add(R.id.content, showFragment, tag);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible()) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void exit() {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setTitle("信息提示");
        builder.setMessage("是否退出系统");
        // 设置确定按钮
        builder.setPositiveButton("确定", (DialogInterface dialogv, int which) -> {
            dialogv.dismiss();
            MemeoryData.destroyInstance();
            System.exit(0);
        });
        // 设置取消按钮
        builder.setNegativeButton("取消", (DialogInterface dialogv, int which) -> {
            dialogv.dismiss();
        });
        // 创建一个消息对话框
        dialog = builder.create();
        //设置点击其他位置dialog不取消
        dialog.setCancelable(true);
        dialog.show();

    }
}
