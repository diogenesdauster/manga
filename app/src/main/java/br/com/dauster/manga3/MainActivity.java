package br.com.dauster.manga3;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.dauster.manga3.Adapter.MainPagerAdapter;
import br.com.dauster.manga3.Http.MangaIntentService;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adicionado o ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Seta o ViewPager criar seu adapter e adiciona a viewpager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerMain);
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // Seta o tablayout e adiciona o viewpager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if( savedInstanceState == null) {
            Intent it = new Intent(this, MangaIntentService.class);
            it.putExtra(MangaIntentService.SINCRONIZAR, true);
            startService(it);
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
