package com.example.tabunganapk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseAcces databaseAcces;
    private List<CatatanTabungan> memos;
    Button btnBuat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseAcces = DatabaseAcces.getInstance(this);

        listView = findViewById(R.id.listView);
         btnBuat = findViewById(R.id.btnBuat);

        btnBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseAcces.open();
        this.memos = databaseAcces.getAllMemos();
        databaseAcces.close();
        MemoAdapter adapter = new MemoAdapter(this,memos);
        this.listView.setAdapter(adapter);
    }


    private class MemoAdapter extends ArrayAdapter<CatatanTabungan>{

        MemoAdapter(@NonNull Context context, List<CatatanTabungan> objects) {
            super(context, 0,  objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.list_catatan_tabungan, parent, false);
            }

            Button btnEdit = convertView.findViewById(R.id.btnEdit);
            Button btnDelete = convertView.findViewById(R.id.btnDelete);

            TextView txtDate =  convertView.findViewById(R.id.txtDate);
            TextView txtMemo = convertView.findViewById(R.id.txtCatatan);

            final CatatanTabungan catatanTabungan = memos.get(position);
            txtDate.setText(catatanTabungan.getDate());
            txtMemo.setText(catatanTabungan.getShortText());

            btnEdit = findViewById(R.id.btnEdit);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    intent.putExtra("CATATAN TABUNGANN", catatanTabungan);
                    startActivity(intent);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseAcces.open();
                    databaseAcces.delete(catatanTabungan);
                    databaseAcces.close();

                    ArrayAdapter<CatatanTabungan> adapter = (ArrayAdapter<CatatanTabungan>) listView.getAdapter();
                    adapter.remove(catatanTabungan);
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }
}