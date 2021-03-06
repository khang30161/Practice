package com.example.preferences.shared.khang.practice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    List<Manager> managers = new ArrayList<>();


    private RecyclerView recyclerView;
    private RecycleviewAdapter rvAdapter;
    private String nameA, masoA,soluongA;
    private EditText ho_va_ten;
    private TextView login;
    private FloatingActionButton floatingActionButton;
    private Realm realm = null;
    private String namelogin;
    String prefname="my_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        initView();
        setDatabyBundle();
        getRealmData();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogin();

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent additem=new Intent(MainActivity.this, AddItem.class);
                startActivity(additem);
            }
        });
    }

    private void dialogLogin() {
        String titleBtn = "SAVE";

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

        View itemView	= inflater.inflate(R.layout.activity_login, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setView(itemView);

        ho_va_ten = (EditText) itemView.findViewById(R.id.et_main_act_login);


        builder.setCancelable(false).setPositiveButton(titleBtn, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String prefname="my_data";
                SharedPreferences pre=getSharedPreferences(prefname, MODE_PRIVATE);
                SharedPreferences.Editor editor=pre.edit();
                String user=ho_va_ten.getText().toString();
                editor.putString("user", user);
                editor.apply();
                getData(user);

                    rvAdapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void getData(String user) {
        SharedPreferences pre=getSharedPreferences (prefname,MODE_PRIVATE);
        String user1=pre.getString("user","");
        login.setText(user1);
    }


    private void setDatabyBundle() {
        Intent intent = getIntent();
        if (intent != null){
        Bundle bundle = intent.getBundleExtra(AddItem.BUNDLE);
            if(bundle!=null){
            nameA = bundle.getString(AddItem.NAME);
             masoA = bundle.getString(AddItem.MASO);
             soluongA=  bundle.getString(AddItem.SOLUONG);}

            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            Manager manager = realm.createObject(Manager.class);
            if(manager !=null){
            manager.setName(nameA);
            manager.setMaso(masoA);
            manager.setSoluong(soluongA);
            realm.commitTransaction();
            }
            managers=realm.where(Manager.class).findAll();
        }};




    private void getRealmData() {
        Realm realm = Realm.getDefaultInstance();
        managers = realm.where(Manager.class).findAll();

        rvAdapter = new RecycleviewAdapter(this, managers, new RecycleviewAdapter.Action() {
            @Override
            public void onClickItem(Manager manager, int position) {
               // dialogInput();
            }

            @Override
            public void onLongClickItem(Manager manager, int position) {
                dialogchangeData(manager.getName(), manager.getMaso(), manager.getSoluong());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(rvAdapter);
    }


    private void dialogchangeData(final String name, final String maso, final String soluong) {
        String titleBtn = "Thay đổi";

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

        View itemView = inflater.inflate(R.layout.dialog_add_item, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setView(itemView);

        final EditText nameInput = (EditText) itemView.findViewById(R.id.dl_main_act_name);
        final EditText masoInput = (EditText) itemView.findViewById(R.id.dl_main_act_code);
        final EditText soluongInput = (EditText) itemView.findViewById(R.id.dl_main_act_soluong);

        nameInput.setText(name);
        masoInput.setText(maso);
        soluongInput.setText(soluong);
        masoInput.setEnabled(false);

        builder.setCancelable(false).setPositiveButton(titleBtn, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (nameInput.length() == 0 || soluongInput.length() == 0) {
                    Toast.makeText(MainActivity.this, "Du lieu ko đầy đủ", Toast.LENGTH_SHORT).show();
                } else {
                    updateManager(nameInput.getText().toString(), maso, soluongInput.getText().toString());
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    void updateManager(String name, String maso, String soluong) {
        // This query is fast because "character" is an indexed field
        realm=Realm.getDefaultInstance();

        Manager manager = realm.where(Manager.class)
                .equalTo("maso", maso)
                .findFirst();
        realm.beginTransaction();
        rvAdapter.notifyDataSetChanged();
        if (manager != null) {
            manager.setName(name);
            manager.setSoluong(soluong);
        }
        realm.commitTransaction();
        managers = realm.where(Manager.class).findAll();

    }



    private void initView() {
        recyclerView = findViewById(R.id.main_act_rv);
        floatingActionButton = findViewById(R.id.fab);
        ho_va_ten=findViewById(R.id.et_main_act_login);
        login=findViewById(R.id.tv_act_main_login);
    }
}
