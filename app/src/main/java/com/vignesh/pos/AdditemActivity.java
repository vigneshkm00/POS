package com.vignesh.pos;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vignesh.pos.models.Inventory;
import com.vignesh.pos.models.Itembought;
import com.vignesh.pos.models.Item;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdditemActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.texttospeach)
    EditText recogText;
    @BindView(R.id.otyEdittext)
    EditText quantity;
    @BindView(R.id.pricetext)
    EditText price;
    @BindView(R.id.itemRecyclerView)
    RecyclerView recyclerView;

    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 100;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 100;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    ArrayList<Itembought> itemboughtArrayList = new ArrayList<Itembought>();
    private File filepath = new File(Environment.getExternalStorageDirectory()+"/demo.xls");
    InventoryRecyclerAdaptor adaptor = new InventoryRecyclerAdaptor(itemboughtArrayList);


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("items");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        ButterKnife.bind(this);
        checkPermission();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void checkPermission() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                addItemtoDatabase();
                break;
            case R.id.micBtn:
                recordVoice();
                break;
            case R.id.itemaddbtn:
                additemtolist();
                break;
            case R.id.createExcelBtn:
                createExcel();
                addItemtoSheet();
                break;

        }
    }

    private void addItemtoSheet() {
//        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");
        final String name = recogText.getText().toString().trim();
        final String qty = quantity.getText().toString().trim();
        final String pri = price.getText().toString().trim();
    }

    private void createExcel() {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Today");
        HSSFRow hssfRow = hssfSheet.createRow(0);
        HSSFCell hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue(recogText.getText().toString());
        try{
            if(!filepath.exists()){
                Toast.makeText(getApplicationContext(),"File Created", Toast.LENGTH_SHORT).show();
                filepath.createNewFile();
            }
            else {
                Toast.makeText(getApplicationContext(),"File not created",Toast.LENGTH_SHORT).show();
            }
            FileOutputStream fileOutputStream= new FileOutputStream(filepath);
            hssfWorkbook.write(fileOutputStream);

            if (fileOutputStream!=null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),e.getMessage()+"To write",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void additemtolist() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        if (recogText.getText().toString() != "" && quantity.getText().toString() != "" && price.getText().toString() != "") {
            itemboughtArrayList.add(new Itembought(recogText.getText().toString().toLowerCase(), quantity.getText().toString(), price.getText().toString(),date.toString()));
            Itembought inv = new Itembought(recogText.getText().toString().toLowerCase(), quantity.getText().toString(), price.getText().toString(),date.toString());
            ref.child("rawmaterial").push().setValue(inv);
            recyclerView.setAdapter(adaptor);
            additemtoInventory();

        }
        else {
            Toast.makeText(getApplicationContext(),"Please fill add values",Toast.LENGTH_LONG).show();
        }
    }

    private void additemtoInventory() {
        Inventory inventory = new Inventory(recogText.getText().toString().toLowerCase(),quantity.getText().toString(),true);
        ref.child("inventory").child(recogText.getText().toString().toLowerCase()).setValue(inventory);
    }

    private void recordVoice() {
        Toast.makeText(getApplicationContext(), "button clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addItemtoDatabase() {
        Item item = new Item("frechfry3", "70", "002");
        ref.child("frechfry").setValue(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                recogText.setText(Objects.requireNonNull(result).get(0));
            }
        }
    }
}