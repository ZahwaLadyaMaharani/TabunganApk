package com.example.tabunganapk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    private EditText etText;
    private CatatanTabungan catatanTabungan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        this.etText =  findViewById(R.id.editJudul);
        Button btnSave = findViewById(R.id.btnTambah);
        Button btnCancel =  findViewById(R.id.btnDelete);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            catatanTabungan = (CatatanTabungan) bundle.get("CATATAN TABUNGAN KITA");
            if (catatanTabungan != null){
                this.etText.setText(catatanTabungan.getText());
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked();
            }
        });
        View btnCencel = null;
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onSaveClicked(){
        DatabaseAcces databaseAcces = DatabaseAcces.getInstance(this);
        databaseAcces.open();
        if (catatanTabungan == null){
            CatatanTabungan temp = new CatatanTabungan();
            temp.setText(etText.getText().toString());
            databaseAcces.save(temp);
        }else {
            catatanTabungan.setText(etText.getText().toString());
            databaseAcces.update(catatanTabungan);
        }
        databaseAcces.close();
        this.finish();
    }
}
