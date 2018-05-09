package com.zia.notice.page.main;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.zia.notice.R;
import com.zia.notice.page.edit.EditDrugActivity;
import com.zia.notice.page.edit.EditElectricActivity;
import com.zia.notice.page.edit.EditFoodActivity;
import com.zia.notice.util.TakePictureManager;
import com.zia.notice.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private TabLayout tabLayout;
    public ViewPager viewPager;
    private FloatingActionsMenu floatingActionsMenu;
    private FloatingActionButton camera, edit;
    private TakePictureManager takePictureManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findWidgets(view);
        setOnclick();
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setupWithViewPager(viewPager);
        takePictureManager = new TakePictureManager(this);
        takePictureManager.setFileSavePath("jian");
        //处理拍照返回结果
        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
            @Override
            public void successful(boolean isTailor, File outFile, Uri filePath) {
                Util.log(outFile.getAbsolutePath());
                Util.toast(getContext(), "已保存在" + outFile.getAbsolutePath());
                // TODO: 2018/5/8 照片接口，outFile对象是jpg格式文件

            }

            @Override
            public void failed(int errorCode, List<String> deniedPermissions) {
                Util.log(deniedPermissions.toString());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        takePictureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void findWidgets(View view) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        floatingActionsMenu = view.findViewById(R.id.floatingMenu);
        camera = view.findViewById(R.id.button_camera);
        edit = view.findViewById(R.id.button_edit);
        camera.setVisibility(View.GONE);
    }

    private void setOnclick() {
        //点击编辑按钮
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        startActivity(new Intent(getContext(), EditFoodActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getContext(), EditDrugActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getContext(), EditElectricActivity.class));
                        break;
                }
                floatingActionsMenu.collapse();
            }
        });
        //点击拍照按钮
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureManager.startTakeWayByCarema();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        camera.setVisibility(View.GONE);
                        break;
                    case 1:
                        camera.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        camera.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        private String titles[] = {"食品", "药品", "电器"};
        private List<Fragment> fragments = new ArrayList<>();

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new FoodFragment());
            fragments.add(new DrugFragment());
            fragments.add(new ElectricFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
