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
import com.zia.notice.database.drug.Drug;
import com.zia.notice.util.DataBox;
import com.zia.notice.util.Util;

public class EditDrugActivity extends AppCompatActivity {

    private Button save;
    private TextInputEditText name, createTime, liveTime, endTime, material, useMaterail, notice;
    private boolean isUpdate = false;
    private Drug drug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drug);
        setTitle("录入药品");
        findWidgets();
        drug = DataBox.getDrug();
        if (drug != null) {
            name.setText(drug.getName());
            createTime.setText(drug.getCreateTime());
            liveTime.setText(drug.getLiveTime());
            endTime.setText(drug.getEndTime());
            material.setText(drug.getMaterial());
            useMaterail.setText(drug.getUse_material());
            notice.setText(drug.getNotice());
            isUpdate = true;
        } else {
            drug = new Drug();
        }
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditDrugActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endTime.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, 2018, 4, 15).show();
            }
        });
        createTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditDrugActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        createTime.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, 2018, 4, 15).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty() || endTime.getText().toString().isEmpty()) {
                    Util.toast(EditDrugActivity.this, "名称或截止日期不能为空");
                    return;
                }
                drug.setName(name.getText().toString());
                drug.setCreateTime(createTime.getText().toString());
                drug.setLiveTime(liveTime.getText().toString());
                drug.setEndTime(endTime.getText().toString());
                drug.setMaterial(material.getText().toString());
                drug.setUse_material(useMaterail.getText().toString());
                drug.setNotice(notice.getText().toString());
                if (isUpdate) {
                    AppDatabase.getAppDatabase(EditDrugActivity.this).drugDao().update(drug);
                    Util.toast(EditDrugActivity.this, "更新成功");
                } else {
                    AppDatabase.getAppDatabase(EditDrugActivity.this).drugDao().insert(drug);
                    Util.toast(EditDrugActivity.this, "录入成功");
                }
                EditDrugActivity.this.finish();
            }
        });
    }

    private void findWidgets() {
        save = findViewById(R.id.drug_button_save);
        name = findViewById(R.id.drug_name);
        createTime = findViewById(R.id.drug_createTime);
        liveTime = findViewById(R.id.drug_liveTime);
        endTime = findViewById(R.id.drug_endTime);
        material = findViewById(R.id.drug_material);
        useMaterail = findViewById(R.id.drug_use_material);
        notice = findViewById(R.id.drug_notice);
    }


}
