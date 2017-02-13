package sunxl8.your_diary.ui.diary;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jakewharton.rxbinding.support.v4.view.RxViewPager;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;

import butterknife.BindView;
import sunxl8.your_diary.R;
import sunxl8.your_diary.base.BaseActivity;
import sunxl8.your_diary.base.IPresenter;
import sunxl8.your_diary.ui.diary.list.DiaryListFragment;

/**
 * Created by sunxl8 on 2017/2/13.
 */

public class DiaryActivity extends BaseActivity {

    @BindView(R.id.tab_diary)
    SegmentTabLayout mTabLayout;
    @BindView(R.id.vp_diary)
    ViewPager mViewPager;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"项目", "日历", "日记"};

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_diary;
    }

    @Override
    protected void initView() {
        mTabLayout.setTabData(mTitles);
        mFragments.add(new DiaryListFragment());
        mFragments.add(new DiaryListFragment());
        mFragments.add(new DiaryListFragment());
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        RxViewPager.pageSelections(mViewPager)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(integer -> {
                    mTabLayout.setCurrentTab(integer);
                });
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void startDiaryActivity(Context context) {
        Intent intent = new Intent(context, DiaryActivity.class);
        context.startActivity(intent);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
