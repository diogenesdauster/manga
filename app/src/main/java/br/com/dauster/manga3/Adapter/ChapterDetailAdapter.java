package br.com.dauster.manga3.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.dauster.manga3.Model.Chapter;
import br.com.dauster.manga3.R;


public class ChapterDetailAdapter extends RecyclerView.Adapter<ChapterDetailAdapter.VH> {

    Context mContext;
    List <Chapter> mChapters;
    OnChapterClickListener mChapterClickListener;

    public ChapterDetailAdapter(Context context, List<Chapter> chapters) {
        mContext = context;
        mChapters = chapters;
    }

    public OnChapterClickListener getChapterClickListener() {
        return mChapterClickListener;
    }

    public void setChapterClickListener(OnChapterClickListener chapterClickListener) {
        this.mChapterClickListener = chapterClickListener;
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
                    mChapterClickListener.onChapterClick(mChapters.get(position),position);
                }
            });
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Chapter chapter = mChapters.get(position);
        holder.txtChapter.setText(String.valueOf(chapter.getChapterId()));
        holder.txtName.setText(chapter.getName());

    }

    @Override
    public int getItemCount() {
        return mChapters.size();
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
         void onChapterClick(Chapter chapter,int position);
    }
}
