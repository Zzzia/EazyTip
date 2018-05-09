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
import com.zia.notice.database.food.Food;
import com.zia.notice.util.DataBox;
import com.zia.notice.util.Util;

public class EditFoodActivity extends AppCompatActivity {

    private Button save;
    private TextInputEditText name, createTime, liveTime, endTime;
    private Food food;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);
        setTitle("录入食品");
        findWidgets();
        food = DataBox.getFood();
        if (food != null) {
            name.setText(food.getName());
            createTime.setText(food.getCreateTime());
            liveTime.setText(food.getLiveTime());
            endTime.setText(food.getEndTime());
            isUpdate = true;
        } else {
            food = new Food();
        }
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditFoodActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                new DatePickerDialog(EditFoodActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    Util.toast(EditFoodActivity.this, "名称或截止日期不能为空");
                    return;
                }
                food.setName(name.getText().toString());
                food.setCreateTime(createTime.getText().toString());
                food.setLiveTime(liveTime.getText().toString());
                food.setEndTime(endTime.getText().toString());
                if (isUpdate) {
                    AppDatabase.getAppDatabase(EditFoodActivity.this).foodDao().update(food);
                    Util.toast(EditFoodActivity.this, "更新成功");
                } else {
                    AppDatabase.getAppDatabase(EditFoodActivity.this).foodDao().insert(food);
                    Util.toast(EditFoodActivity.this, "录入成功");
                }
                EditFoodActivity.this.finish();
            }
        });
    }

    private void findWidgets() {
        save = findViewById(R.id.food_button_save);
        name = findViewById(R.id.food_edit_name);
        createTime = findViewById(R.id.food_edit_createTime);
        liveTime = findViewById(R.id.food_edit_liveTime);
        endTime = findViewById(R.id.food_edit_endTime);
    }
}
