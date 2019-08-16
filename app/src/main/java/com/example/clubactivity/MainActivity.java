package com.example.clubactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.clubactivity.Class.ClassFragment;
import com.example.clubactivity.Home.HomeFragment;
import com.example.clubactivity.Login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; //하단 네비 바
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private HomeFragment homeFragment;
    private ClassFragment classFragment;
    private ClubFragment clubFragment;
    private MyPageFragment myPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //로그인 액티비티 죽이기
       Activity loginActivity = LoginActivity.loginActivity;
        loginActivity.finish();

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView); // 애니메이션 삭제

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        setFragment(0);
                        break;
                    case R.id.action_class:
                        setFragment(1);
                        break;
                    case R.id.action_people:
                        setFragment(2);
                        break;
                    case R.id.action_my:
                        setFragment(3);
                        break;
                }
                return true;
            }
        });

        homeFragment = new HomeFragment();
        classFragment = new ClassFragment();
        clubFragment = new ClubFragment();
        myPageFragment = new MyPageFragment();
        setFragment(0); //홈화면으로 지정

    }

    //프래크먼트 교체가 일어나는 곳
    private void setFragment(int menu){

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        switch (menu){
            case 0:
                fragmentTransaction.replace(R.id.main_frame, homeFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.main_frame, classFragment);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.main_frame, clubFragment);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction.replace(R.id.main_frame, myPageFragment);
                fragmentTransaction.commit();
                break;
        }

    }

  /*  public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment).commit();
    }*/


}
