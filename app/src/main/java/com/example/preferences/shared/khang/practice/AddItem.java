package com.example.preferences.shared.khang.practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItem extends AppCompatActivity {
    EditText name, maso, soluong;
    private Button btn_submit;
    public static final String NAME ="NAME";
    public static final String MASO ="MASO";
    public static final String SOLUONG ="SOLUONG";
    public static final String BUNDLE ="BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        name = (EditText) findViewById(R.id.main_act_name);
        maso = (EditText) findViewById(R.id.main_act_code);
        soluong = (EditText) findViewById(R.id.main_act_soluong);
        btn_submit=(Button) findViewById(R.id.act_main_btn);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameA = name.getText().toString();
                String masoA = maso.getText().toString();
                String soluongA=soluong.getText().toString();
                //byExtras(title,description);
                byBundle(nameA,masoA,soluongA);
            }
        });}
    public void byBundle(String nameA, String masoA, String soluongA){
        Intent intent = new Intent(AddItem.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(NAME,nameA);
        bundle.putString(MASO,masoA);
        bundle.putString(SOLUONG,soluongA);
        intent.putExtra(BUNDLE,bundle);
        startActivity(intent);
    }


}
