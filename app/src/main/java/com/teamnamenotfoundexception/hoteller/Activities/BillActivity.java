package com.teamnamenotfoundexception.hoteller.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.teamnamenotfoundexception.hoteller.BillAdapter;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    ArrayList<DishItem> purchased;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        toolbar = (Toolbar) findViewById(R.id.billTools);
        listView = (ListView) findViewById(R.id.bill_dishes);
        purchased = new ArrayList<>();
        purchased.add(new DishItem(1,"Dosa","Hot",20,2,"Nice Looking","gothilla"));
        BillAdapter adapter = new BillAdapter(BillActivity.this,purchased,R.id.bdentry);
        listView.setAdapter(adapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
