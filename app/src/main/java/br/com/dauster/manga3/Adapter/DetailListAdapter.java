package br.com.dauster.manga3.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.dauster.manga3.R;
import br.com.dauster.manga3.database.DataContract;


public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.VH> {

    Context mContext;
    Cursor mCursor;
    OnChapterClickListener mChapterClickListener;

    public DetailListAdapter(Context context) {
        mContext = context;
    }


    public void setChapterClickListener(OnChapterClickListener chapterClickListener) {
        this.mChapterClickListener = chapterClickListener;
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public void swapCursor(Cursor mCursor) {
        this.mCursor = mCursor;
        notifyDataSetChanged();
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.iten_list_chapter,parent,false);

        final VH holder = new VH(view);

        if (mChapterClickListener != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    mCursor.moveToPosition(position);
                    mChapterClickListener.onChapterClick(mCursor,position);
                }
            });
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        mCursor.moveToPosition(position);
        Long chapeterid = mCursor.getLong(mCursor.
                getColumnIndex(DataContract.ChapterContract.COLUMN_CHAPTERID));
        String name = mCursor.getString(mCursor.
                getColumnIndex(DataContract.ChapterContract.COLUMN_NAME));


        holder.txtChapter.setText(String.valueOf(chapeterid));
        holder.txtName.setText(name);

    }

    @Override
    public int getItemCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }

    public static class VH extends RecyclerView.ViewHolder {

         TextView txtChapter;
         TextView txtName;

        public VH(View itemView) {
            super(itemView);
            txtChapter = (TextView) itemView.findViewById(R.id.txtChapter);
            txtName    = (TextView) itemView.findViewById(R.id.txtName);
        }

    }


    public static interface OnChapterClickListener {
         void onChapterClick(Cursor cursor,int position);
    }
}
