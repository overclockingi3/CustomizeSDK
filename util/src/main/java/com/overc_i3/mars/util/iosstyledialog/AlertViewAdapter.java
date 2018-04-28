package com.overc_i3.mars.util.iosstyledialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.overc_i3.btwl.R;

import java.util.List;

/**
 * Referenced from Github saiwu-bigkoo
 */
public class AlertViewAdapter extends BaseAdapter{
    private List<String> mDatas;
    private List<String> mDestructive;
    private boolean mNoTitle = true;

    public AlertViewAdapter(List<String> datas,List<String> destructive){
        this.mDatas =datas;
        this.mDestructive =destructive;
    }

    public void setNoTitle(boolean b){
        mNoTitle = b;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String data= mDatas.get(position);
        Holder holder=null;
        View view =convertView;
        if(view==null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view=inflater.inflate(R.layout.item_alertbutton, null);
            holder=creatHolder(view);
            view.setTag(holder);
        }
        else{
            holder=(Holder) view.getTag();
        }
        if( mNoTitle && position == 0)
            holder.divider.setVisibility(View.GONE);
        holder.UpdateUI(parent.getContext(),data,position);
        return view;
    }
    public Holder creatHolder(View view){
        return new Holder(view);
    }
    class Holder {
        private TextView tvAlert;
        private View divider;

        public Holder(View view){
            tvAlert = (TextView) view.findViewById(R.id.tvAlert);
            divider = view.findViewById(R.id.tvAlert_divider);
        }
        public void UpdateUI(Context context,String data,int position){
            tvAlert.setText(data);
            if (mDestructive!= null && mDestructive.contains(data)){
                tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_destructive));
            }
            else{
                tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_others));
            }
        }
    }
}