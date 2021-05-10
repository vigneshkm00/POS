package com.vignesh.pos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vignesh.pos.models.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddbillActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.quantityTextview)
    TextView quantityTextview;
    @BindView(R.id.spinner)
    Spinner itemlistSpinner;
    int quantityCount = 0;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbill);
        ButterKnife.bind(this);
        Toast.makeText(getApplicationContext(),"Application opened",Toast.LENGTH_SHORT).show();
        quantityTextview.setText(String.valueOf(quantityCount));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        updateSpinner();


    }


    private void updateSpinner() {
        mDatabase = FirebaseDatabase.getInstance().getReference("items");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> itemlist = new ArrayList<String>();
                for(DataSnapshot itemsnapshot : snapshot.getChildren()){
                    String name = itemsnapshot.child("itemName").getValue(String.class);
                    itemlist.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddbillActivity.this, android.R.layout.simple_spinner_item,itemlist);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                itemlistSpinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.minusBtn:
                Toast.makeText(getApplicationContext(),"minus clicked",Toast.LENGTH_LONG).show();
//                System.out.println("minusclicked");
                if(quantityCount!=0) {
                    quantityCount--;
                    quantityTextview.setText(String.valueOf(quantityCount));
                }
                break;
            case R.id.plusBtn:
                quantityCount++;
                quantityTextview.setText(String.valueOf(quantityCount));
                break;
            case R.id.addBtn:
                Toast.makeText(getApplicationContext(),"Item added",Toast.LENGTH_SHORT).show();
                Item item = new Item("juice","40","001");
                mDatabase.child("items").child("juice").setValue(item).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}