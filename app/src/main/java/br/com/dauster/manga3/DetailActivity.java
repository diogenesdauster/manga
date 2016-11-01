package br.com.dauster.manga3;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.dauster.manga3.Adapter.DetailPagerAdapter;
import br.com.dauster.manga3.Model.Manga;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MANGAID ="mangaId" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Arquivo de Layout
        setContentView(R.layout.activity_manga_detail);

        // Cria um objeto manga e passa coloca seu Id vindo da tela principal
        Manga manga = new Manga();
        manga.setHref(getIntent().getExtras().getString(EXTRA_MANGAID));

        // Adicionado o ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Adiciona o Botão de voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Adapter que será responsavel por criação das duas abas do viewpager
        DetailPagerAdapter detailPagerAdapter =
                new DetailPagerAdapter(getSupportFragmentManager(),manga);

        // Seta o ViewPager e adiciona o adapter
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerDetail);
        viewPager.setAdapter(detailPagerAdapter);

        // Seta o tablayout e adiciona o viewpager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


}
