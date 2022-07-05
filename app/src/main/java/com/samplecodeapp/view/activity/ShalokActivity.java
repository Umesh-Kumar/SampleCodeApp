package com.samplecodeapp.view.activity;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.samplecodeapp.R;
import com.samplecodeapp.Utils.Util;
import com.samplecodeapp.view.fragments.DetailListFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShalokActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_list_viewpager);
        ButterKnife.bind(this);

        // initial toolbar setup
        toolbar.setSubtitle("अध्याय " + getIntent().getIntExtra("adhyay_no", 1));
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
        }
        try {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        } catch (Exception e) {
        }

        try {
            JSONArray jsonArray = new JSONArray(getIntent().getStringExtra("json_data"));

            if (viewPager != null) {
                setupViewPager(viewPager, jsonArray, getIntent().getIntExtra("shalok_position", 0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager(ViewPager viewPager, JSONArray jsonArray, int position) {
        Adapter adapter = new Adapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                HashMap<String, String> hashMap = new HashMap<>();
                String title = jsonArray.getJSONObject(i).getString("s_n");
                hashMap.put("sh_sno", "श्लोक: " + title);
                hashMap.put("sh_title", jsonArray.getJSONObject(i).getString("s"));
                hashMap.put("sh_description", jsonArray.getJSONObject(i).getString("t"));
                hashMap.put("sh_upvach", jsonArray.getJSONObject(i).getString("d"));
                hashMap.put("sh_heading", jsonArray.getJSONObject(i).getString("sub_heading"));
//                hashMap.put("sh_language", jsonArray.getJSONObject(i).getString("language"));

                Bundle bundle = new Bundle();
                bundle.putString("sh_sno", "श्लोक: " + title);
                bundle.putString("sh_title", jsonArray.getJSONObject(i).getString("s"));
                bundle.putString("sh_description", jsonArray.getJSONObject(i).getString("t"));
                bundle.putString("sh_upvach", jsonArray.getJSONObject(i).getString("d"));
                bundle.putString("sh_heading", jsonArray.getJSONObject(i).getString("sub_heading"));
//                bundle.putString("sh_language", jsonArray.getJSONObject(i).getString("language"));
                bundle.putInt("adhyay_no", getIntent().getIntExtra("adhyay_no", 1));
                bundle.putInt("shalok_position", i);

                adapter.addFragment(new DetailListFragment(), "श्लोक " + title, bundle);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        viewPager.setAdapter(adapter);

        tabs.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(position);
    }

    private class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title, Bundle bundle) {
            fragment.setArguments(bundle);
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_my_apps:
                Util.showToast(this, getString(R.string.coming_soon));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
