package com.zia.notice.page.edit;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.zia.notice.R;
import com.zia.notice.database.AppDatabase;
import com.zia.notice.database.electric.Electric;
import com.zia.notice.util.DataBox;
import com.zia.notice.util.Util;

public class EditElectricActivity extends AppCompatActivity {

    private Button save;
    private TextInputEditText name, createTime, buyTime, fixTime, phoneNumber, model;
    private Electric electric;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_electric);
        setTitle("录入电器");
        findWidgets();
        electric = DataBox.getElectric();
        if (electric != null) {
            name.setText(electric.getName());
            createTime.setText(electric.getCreateTime());
            buyTime.setText(electric.getBuyTime());
            fixTime.setText(electric.getFixTime());
            phoneNumber.setText(electric.getPhoneNumber());
            model.setText(electric.getModel());
            isUpdate = true;
        } else {
            electric = new Electric();
        }
        createTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditElectricActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        createTime.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, 2018, 4, 15).show();
            }
        });
        buyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditElectricActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        buyTime.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, 2018, 4, 15).show();
            }
        });
        fixTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditElectricActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fixTime.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, 2018, 4, 15).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty() || fixTime.getText().toString().isEmpty()) {
                    Util.toast(EditElectricActivity.this, "名称或截止日期不能为空");
                    return;
                }
                electric.setBuyTime(buyTime.getText().toString());
                electric.setCreateTime(createTime.getText().toString());
                electric.setName(name.getText().toString());
                electric.setFixTime(fixTime.getText().toString());
                electric.setPhoneNumber(phoneNumber.getText().toString());
                electric.setModel(model.getText().toString());
                if (isUpdate) {
                    AppDatabase.getAppDatabase(EditElectricActivity.this).electricDao().update(electric);
                    Util.toast(EditElectricActivity.this, "更新成功");
                } else {
                    AppDatabase.getAppDatabase(EditElectricActivity.this).electricDao().insert(electric);
                    Util.toast(EditElectricActivity.this, "录入成功");
                }
                EditElectricActivity.this.finish();
            }
        });
    }

    private void findWidgets() {
        save = findViewById(R.id.electric_button_save);
        name = findViewById(R.id.electric_edit_name);
        createTime = findViewById(R.id.electric_edit_createTime);
        buyTime = findViewById(R.id.electric_edit_buyTime);
        fixTime = findViewById(R.id.electric_edit_FixTime);
        phoneNumber = findViewById(R.id.electric_edit_phoneNumber);
        model = findViewById(R.id.electric_edit_model);
    }
}
