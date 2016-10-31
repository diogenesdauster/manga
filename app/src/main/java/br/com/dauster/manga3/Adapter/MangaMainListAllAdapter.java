package br.com.dauster.manga3.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.dauster.manga3.R;
import br.com.dauster.manga3.database.DataContract;

public class MangaMainListAllAdapter extends RecyclerView.Adapter<MangaMainListAllAdapter.VH> {

    Context mContext;
    OnMangaClickListener mMangaClickListener;
    Cursor mCursor;


    public MangaMainListAllAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.iten_list_manga,parent,false);
        final VH viewHolder = new VH(view);

        if(mMangaClickListener != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    mCursor.moveToPosition(position);
                    mMangaClickListener.onMangaClick(mCursor,position);
                }
            });
        }

        return viewHolder;
    }


    public Cursor getCursor() {
        return mCursor;
    }

    public void swapCursor(Cursor mCursor) {
        this.mCursor = mCursor;
        notifyDataSetChanged();
    }

    public void setMangaClickListener(OnMangaClickListener mMangaClickListener) {
        this.mMangaClickListener = mMangaClickListener;
    }


    @Override
    public void onBindViewHolder(final VH holder, int position) {
        //Manga manga = mMangas.get(position);

        mCursor.moveToPosition(position);
        String cover = mCursor.getString(mCursor
                .getColumnIndex(DataContract.MangaContract.COLUMN_COVER));
        String name  = mCursor.getString(mCursor
                .getColumnIndex(DataContract.MangaContract.COLUMN_NAME));

        Glide.with(mContext).load(cover)
                .placeholder(R.drawable.ic_do_not_disturb_black_200dp)
                .into(holder.mImgCover);
        holder.mTxtTitle.setText(name);


//        Glide.with(mContext).load(manga.getCover()).placeholder(R.drawable.ic_do_not_disturb_black_200dp).into(holder.mImgCover);
//        holder.mTxtTitle.setText(manga.getName());
/*
        Glide.with(mContext).load(manga.getCover()).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                holder.mImgCover.setImageBitmap(resource);
                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        int mutedLight = palette.getMutedColor(Color.BLACK);
                        int vibrantColor = palette.getVibrantColor(Color.BLACK);
                        holder.mMangaNameHolder.setBackgroundColor(vibrantColor);
                    }
                });
            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }



    public static class VH extends RecyclerView.ViewHolder {

        ImageView mImgCover;
        TextView mTxtTitle;
        LinearLayout mMangaNameHolder;

        public VH(View itemView) {
            super(itemView);
            mImgCover = (ImageView) itemView.findViewById(R.id.imgCover);
            mTxtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            mMangaNameHolder = (LinearLayout) itemView.findViewById(R.id.MangaNameHolder);
        }
    }

    public static interface OnMangaClickListener {
        void onMangaClick(Cursor cursor,int position);
    }

}
