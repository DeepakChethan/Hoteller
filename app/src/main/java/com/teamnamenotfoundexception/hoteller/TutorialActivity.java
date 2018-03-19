package com.teamnamenotfoundexception.hoteller;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.teamnamenotfoundexception.hoteller.fragments.Tut1Fragment;
import com.teamnamenotfoundexception.hoteller.fragments.Tut2Fragment;
import com.teamnamenotfoundexception.hoteller.fragments.Tut3Fragment;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    private CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);



    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments ;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
            fragments.add(new Tut1Fragment());
            fragments.add(new Tut2Fragment());
            fragments.add(new Tut3Fragment());
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
