package br.com.dauster.manga3.Adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.dauster.manga3.Model.Page;
import br.com.dauster.manga3.R;


public class GalleryAdapter extends PagerAdapter {

    Context mContext;
//    Cursor mCursor;
    List<Page> mPages;
    ImageView imageView;

    public GalleryAdapter(Context mContext,List<Page> pages) {
        this.mContext = mContext;
        this.mPages = pages;
    }

//    public Cursor getCursor() {
//        return mCursor;
//    }
//
//    public void swapCursor(Cursor mCursor) {
//        this.mCursor = mCursor;
//        notifyDataSetChanged();
//    }

//    @Override
//    public int getCount() {
//        return (mCursor != null) ? mCursor.getCount() : 0;
//    }

    @Override
    public int getCount() {
        return (mPages != null) ? mPages.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        new Handler().post(new Runnable() {
            @Override
            public void run() {

                imageView = new ImageView(mContext);
                int padding = mContext.getResources().getDimensionPixelSize(
                        R.dimen.padding_medium);
                imageView.setPadding(padding, padding, padding, padding);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                Glide.with(mContext).load(mPages.get(position).getUrl())
                        .placeholder(R.drawable.ic_do_not_disturb_black_200dp)
                        .into(imageView);
            }
        });


        ((ViewPager) container).addView(imageView, 0);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

}
