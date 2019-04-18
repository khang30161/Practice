package com.example.preferences.shared.khang.practice;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
    List<Manager> managers =new ArrayList<>();

    private CardView cardView;
    private RecyclerView recyclerView;
    private RecycleviewAdapter rvAdapter;
    private String et_name;
    private String et_maso;
    private String et_soluong;
    private TextView tv_change;
    private View view;
    private FloatingActionButton floatingActionButton;
    private Realm realm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        initView();

        getRealmData();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInput();
            }
        });


    }



    private void getRealmData() {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Manager> manager = realm.where(Manager.class).findAll();

        rvAdapter = new RecycleviewAdapter(this, managers);
        RealmQuery<Manager> managerRealmQuery = realm.where(Manager.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAdapter = new RecycleviewAdapter(MainActivity.this, manager);
        recyclerView.setAdapter(rvAdapter);
        rvAdapter.changeData(new RecycleviewAdapter.ChangeInterface() {

            @Override
            public void changeData(View view, int position, boolean b) {
                changedata(view, managers.get(position));

            }
        });


    }



    private void changedata(View view, Manager manager) {
        et_soluong = manager.getSoluong();
        et_name = manager.getName();
        et_maso = manager.getMaso();

        dialogchangeData(et_maso, et_soluong, et_name);
    }









    private void dialogInput() {
        String titleBtn = "Lưu lại";

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

        View itemView = inflater.inflate(R.layout.dialog_add_item, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setView(itemView);

        final EditText numberInput = (EditText) itemView.findViewById(R.id.dl_main_act_code);
        final EditText nameInput = (EditText) itemView.findViewById(R.id.dl_main_act_name);
        final EditText soluongInput = (EditText) itemView.findViewById(R.id.dl_main_act_soluong);


        builder.setCancelable(false);
        builder.setPositiveButton(titleBtn, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (nameInput.length() == 0 || nameInput.length() == 0) {
                    Toast.makeText(MainActivity.this, "dữ liệu không đầy đủ", Toast.LENGTH_SHORT).show();
                } else {
                    et_name = nameInput.getText().toString();
                    et_maso = numberInput.getText().toString();
                    et_soluong = soluongInput.getText().toString();

                    initListener();
                    rvAdapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "dữ liệu lưu thành công", Toast.LENGTH_SHORT).show();

                }
            }
        });
        builder.setNegativeButton("hủy bỏ", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        }
    private void dialogchangeData(final String et_maso, final String et_soluong, final String et_name) {
        String titleBtn = "thay đổi";

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

        View itemView = inflater.inflate(R.layout.dialog_add_item, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setView(itemView);

        final EditText nameInput = (EditText) itemView.findViewById(R.id.dl_main_act_name);
        final EditText soluongInput = (EditText) itemView.findViewById(R.id.dl_main_act_soluong);
        final EditText masoInput = (EditText) itemView.findViewById(R.id.dl_main_act_code);

        nameInput.setText(this.et_name);
        soluongInput.setText(this.et_soluong);
        masoInput.setText(this.et_maso);

        builder.setCancelable(false).setPositiveButton(titleBtn, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (nameInput.length() == 0 || soluongInput.length() == 0) {
                    Toast.makeText(MainActivity.this, "Du lieu ko đầy đủ", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.this.et_name = nameInput.getText().toString();
                    MainActivity.this.et_maso = masoInput.getText().toString();
                    MainActivity.this.et_soluong = soluongInput.getText().toString();

                    changeData();

                    rvAdapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "thay đổi thành công", Toast.LENGTH_SHORT).show();
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

    private void changeData() {
        realm.beginTransaction();

        Manager manager = realm.createObject(Manager.class);;
        manager.setMaso(et_maso);
        manager.setName(et_name);

        realm.commitTransaction();
        getRealmData();


    }
    private void initListener() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Manager manager = realm.createObject(Manager.class);
        manager.setName(et_name);
        manager.setMaso(et_maso);
        manager.setSoluong(et_soluong);

        realm.commitTransaction();
        getRealmData();
    };

    private void initView() {
        recyclerView = findViewById(R.id.main_act_rv);
        floatingActionButton = findViewById(R.id.fab);
        cardView = findViewById(R.id.card_view);


    }
}
