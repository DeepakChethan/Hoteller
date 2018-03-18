package com.teamnamenotfoundexception.hoteller.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.teamnamenotfoundexception.hoteller.adapters.BillAdapter;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.R;

public class BillActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    TextView billAmt, billTax,billId;
    Float nbillAmt, nbillTax = 18.0f, nbillTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        toolbar = (Toolbar) findViewById(R.id.billTools);
        listView = (ListView) findViewById(R.id.bill_dishes);

        billAmt = (TextView) findViewById(R.id.billAmt);
        billTax = (TextView) findViewById(R.id.billTax);
        billId = (TextView) findViewById(R.id.billId);
        setItUp();
        CartManager cartManager = CartManager.get(getApplicationContext());
        BillAdapter adapter = new BillAdapter(BillActivity.this,cartManager.getCartItems(),R.id.bdentry);
        listView.setAdapter(adapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setItUp() {
        CartManager cartManager = CartManager.get(getApplicationContext());
        billAmt.setText(cartManager.getTotalOrderPrice()+" ");
        billTax.setText("18 %");
        billId.setText("23234 2342 2342 23423");
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
