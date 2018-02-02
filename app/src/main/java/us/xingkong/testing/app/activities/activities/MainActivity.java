package us.xingkong.testing.app.activities.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import us.xingkong.testing.R;
import us.xingkong.testing.adapter.FragmentAdapter;
import us.xingkong.testing.app.activities.BaseActivity;
import us.xingkong.testing.app.fragments.fragments.LivingListFragment;
import us.xingkong.testing.app.fragments.fragments.MyAppsFragment;

/**
 * Created by SeaLynn0 on 2018/1/30.
 */

public class MainActivity extends BaseActivity {

    /*
    ButterKnife
    Eliminate findViewById calls by using @BindView on fields.
     */
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.tabs)
    TabLayout mTablayout;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState, boolean bindSuccess) {

        /*
        设置ActionBar的属性
         */
        if (getSupportActionBar() != null){
            final ActionBar bar = getSupportActionBar();

            bar.setHomeAsUpIndicator(R.drawable.ic_menu);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowTitleEnabled(true);
            bar.setTitle(getResources().getString(R.string.star_live));

        }

        initHeader();

        /**
         * 监听NavigationView的选择事件
         */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    item.setChecked(true);
                switch (item.getItemId()){
                    case R.id.exit_login:
                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                        editor.putString("username", "");
                        editor.putString("password", "");
                        editor.putBoolean("isAutoLogin", false);
                        editor.apply();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        Toast.makeText(MainActivity.this, R.string.exit_login_succ, Toast.LENGTH_SHORT).show();
                        finish();
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.exit_app:
                        android.os.Process.killProcess(android.os.Process.myPid());
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.create_app:
                        startActivity(new Intent(MainActivity.this,CreateAppActivity.class));
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.search:
                        startActivity(new Intent(MainActivity.this,SearchActivity.class));
                        drawerLayout.closeDrawers();
                        return true;
                }
                //最后关闭导航菜单
                drawerLayout.closeDrawers();
                return true;
            }
        });

        initViewPager();
    }

    /**
     * ButterKnife貌似获取不了headerlayout里的控件
     */
    private void initHeader() {
        AppCompatImageView imageView = findViewById(R.id.iv_header);
        Glide.with(MainActivity.this).load(R.drawable.banner3).into(imageView);

        AppCompatTextView textView = findViewById(R.id.username);
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        textView.setText(pref.getString("username", null));
    }

    /**
     * 初始化ViewPager和TabLayout
     */
    private void initViewPager() {
        List<String>titles = new ArrayList<>();
        titles.add("直播列表");
        titles.add("我的直播");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LivingListFragment());
        fragments.add(new MyAppsFragment());

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragments,titles);
        //给ViewPager设置适配器
        viewPager.setAdapter(fragmentAdapter);
        //给TabLayout设置适配器
        mTablayout.setupWithViewPager(viewPager);
    }

    /**
     * 复写onOptionsItemSelected(MenuItem item)方法
     * 监听返回键
     *
     * @param item
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
