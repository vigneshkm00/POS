package com.vignesh.pos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vignesh.pos.models.Itembought;

import java.util.ArrayList;

public class InventoryRecyclerAdaptor extends RecyclerView.Adapter<InventoryRecyclerAdaptor.ViewHolder>{
    ArrayList<Itembought> itemboughtArrayList = new ArrayList<>();
    private Itembought[] itemboughtList;
//
//    public InventoryRecyclerAdaptor(Inventory[] inventoryList,ArrayList<Inventory> inventoryArrayList) {
//        this.inventoryList = inventoryList;
//
//
//    }


    public InventoryRecyclerAdaptor(ArrayList<Itembought> itemboughtArrayList) {
        this.itemboughtArrayList = itemboughtArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        final Inventory inventory = inventoryList[position];
//        holder.textView.setText("Item: "+inventoryList[position].getpName()+"\nQuatity: "+inventoryList[position].getpQty()+"\nPrice: "+inventoryList[position].getpPrice());
        holder.textView.setText((position+1)+") Item: "+ itemboughtArrayList.get(position).getpName()+"\nQuatity: "+ itemboughtArrayList.get(position).getpQty()+"\nPrice: "+ itemboughtArrayList.get(position).getpPrice());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"click on item: ",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemboughtArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageButton imageButton;
        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.list_item_name);
            this.imageButton = (ImageButton) itemView.findViewById(R.id.list_item_delete);
            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.list_item_layout);

        }
    }
}
