package app.com.stapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by AKASH on 1/7/2018.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter {


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                IntradayCallFragment intradayCallFragment = new IntradayCallFragment();
                return intradayCallFragment;

            case 1:
                PositionalCallFragment positionalCallFragment = new PositionalCallFragment();
                return positionalCallFragment;

            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {

        switch (position){

            case 0:
                return "Intraday Calls";

            case 1:
                return "Positional calls";

            default:
                return null;


        }



    }


}
