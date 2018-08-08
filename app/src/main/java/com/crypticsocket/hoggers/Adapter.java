package com.crypticsocket.hoggers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ResourceBundle;

/**
 * Created by saket on 09-Dec-17.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    String data1[], data2[];
    Context ctx;
    public Adapter(Context ct, String s1[], String s2[]) {
        ctx = ct;
        data1 = s1;
        data2 = s2;

    }

    @Override

    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.menu,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.t1.setText(data1[position]);
        holder.t2.setText(data2[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView t1, t2;
        public Holder(View itemView) {
            super(itemView);
            t1 = (TextView) itemView.findViewById(R.id.nameFood);
            t2 = (TextView) itemView.findViewById(R.id.priceFood);
        }
    }
}
