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

import com.bumptech.glide.Glide;

import java.util.List;

import us.xingkong.streamsdk.model.App;
import us.xingkong.testing.Global;
import us.xingkong.testing.R;
import us.xingkong.testing.app.activitys.LiveActivity;
import us.xingkong.testing.app.activitys.StreamActivity;
import us.xingkong.testing.app.activitys.UpdateAppActivity;

/**
 * Created by SeaLynn0 on 2018/1/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.AppViewHolder> {

    private Context mContext;

    private List<App> mItemList;

    private boolean isGetLiving;

    private int jumpTo;

    public ItemAdapter(List<App> mItemList, boolean isGetLiving, int jumpTo) {
        this.mItemList = mItemList;
        this.isGetLiving = isGetLiving;
        this.jumpTo = jumpTo;
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
        Global.app = mItemList.get(position);

        holder.appTitle.setText(Global.app.getTitle());
        holder.appUser.setText(Global.app.getUser());
        holder.appMaintext.setText(Global.app.getMaintext());

        Glide.with(mContext).load("http://live.xingkong.us/screen/" + Global.app.getAppname() + ".png").into(holder.appImg);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jumpTo == Global.JUMP_TO_LIVE_ACTIVITY) {
                    Intent intent = new Intent(mContext, LiveActivity.class);
                    intent.putExtra("app", Global.app.getAppname());
                    mContext.startActivity(intent);
                } else if (jumpTo == Global.JUMP_TO_STREAM_ACTIVITY) {
                    Intent intent = new Intent(mContext, StreamActivity.class);
                    intent.putExtra("token", Global.app.getToken());
                    mContext.startActivity(intent);
                }

            }
        };

        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(mContext, UpdateAppActivity.class);
                intent.putExtra("app", Global.app.getAppname());
                mContext.startActivity(intent);
                return true;
            }
        };

        holder.cardView.setOnClickListener(onClickListener);
        holder.cardView.setOnLongClickListener(onLongClickListener);
    }

    @Override
    public int getItemCount() {
        if (isGetLiving) {
            int count = 0;
            for (int i = 0; i < mItemList.size(); i++) {
                App app = mItemList.get(i);
                if (app.isAlive()) {
                    mItemList.remove(i);
                    mItemList.add(0, app);
                    count++;
                }
            }
            return count;
        } else return mItemList.size();

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
