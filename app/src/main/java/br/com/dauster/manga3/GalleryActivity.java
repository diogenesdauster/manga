package br.com.dauster.manga3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.dauster.manga3.Fragments.ChapterDetailFragment;
import br.com.dauster.manga3.Fragments.PageGalleryFragment;
import br.com.dauster.manga3.Model.Chapter;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Chapter chapter = (Chapter) savedInstanceState.getSerializable(ChapterDetailFragment.CHAPTER_INFO);

        PageGalleryFragment.newInstace(chapter);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragGallery,PageGalleryFragment.newInstace(chapter)).commit();


    }

}
