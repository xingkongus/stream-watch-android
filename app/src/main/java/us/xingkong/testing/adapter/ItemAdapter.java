package us.xingkong.testing.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import us.xingkong.streamsdk.model.App;
import us.xingkong.testing.R;
import us.xingkong.testing.app.activitys.LiveActivity;

/**
 * Created by SeaLynn0 on 2018/1/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.AppViewHolder> {

    private Context mContext;

    private List<App> mItemList;

    private boolean isGetLiving;

    public ItemAdapter(List<App> mItemList,boolean isGetLiving) {
        this.mItemList = mItemList;
        this.isGetLiving = isGetLiving;
    }
    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.appitem, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        final App app = mItemList.get(position);
//        System.out.print(app.getAppname());
        holder.appTitle.setText(app.getTitle());
        holder.appUser.setText(app.getUser());
        holder.appMaintext.setText(app.getMaintext());

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,LiveActivity.class);
                intent.putExtra("app",app.getAppname());
                mContext.startActivity(intent);
            }
        };

        holder.appImg.setOnClickListener(onClickListener);
        holder.appTitle.setOnClickListener(onClickListener);
        holder.appUser.setOnClickListener(onClickListener);
        holder.appMaintext.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        if (isGetLiving){
            int count = 0;
            for (int i = 0;i<mItemList.size();i++){
                App app = mItemList.get(i);
                if (app.isAlive()){
                    mItemList.remove(i);
                    mItemList.add(0,app);
                    count++;
                }
            }
            return count;
        }else return mItemList.size();

    }

    class AppViewHolder extends RecyclerView.ViewHolder {
        ImageView appImg;
        TextView appTitle;
        TextView appUser;
        TextView appMaintext;
        CardView cardView;

        AppViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView;
            appImg = itemView.findViewById(R.id.app_img);
            appTitle = itemView.findViewById(R.id.app_title);
            appUser = itemView.findViewById(R.id.app_user);
            appMaintext = itemView.findViewById(R.id.app_maintext);
        }
    }
}
