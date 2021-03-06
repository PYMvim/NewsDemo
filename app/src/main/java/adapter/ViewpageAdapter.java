package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by yls on 2017/5/16.
 */

public class ViewpageAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"头条","军事","体育","国际"};
    private Context context;

    public ViewpageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragment.PageFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
