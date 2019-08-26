package com.example.vishal.tipcalculatorchandrasekaran;

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class TipCalculatorActivity extends AppCompatActivity {
    private EditText billEditText,tipEditText;
    private TextView finalBillTextView;
    private Button calculateButton;
    private String bill;
    private String tip;
    DecimalFormat decimalFormat = new DecimalFormat("$##.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);

//        ConstraintLayout constraintLayout = findViewById(R.id.layoutCalc);
//        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
//        animationDrawable.setEnterFadeDuration(2000);
//        animationDrawable.setExitFadeDuration(4000);
//        animationDrawable.start();

        billEditText = (EditText) findViewById(R.id.editTextBill);
        tipEditText = (EditText) findViewById(R.id.editTextTip);
        finalBillTextView = (TextView) findViewById(R.id.finalBillTextView);
        calculateButton = (Button) findViewById(R.id.buttonCalculate);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bill = billEditText.getText().toString().trim();
                tip = tipEditText.getText().toString().trim();
                if(bill.isEmpty() || tip.isEmpty()){
                    Toast.makeText(TipCalculatorActivity.this, "Please enter a value!", Toast.LENGTH_SHORT).show();
                }else{
                    calculateBill();
                }
            }
        });

    }
    private void calculateBill(){
        double billNumber = Double.parseDouble(bill);
        double tipNumber = Double.parseDouble(tip);
        double tipPercentage = tipNumber / 100;
        double totalTip = billNumber * tipPercentage;
        double totalBillNumber = billNumber + totalTip;
        totalBillNumber = Math.round(totalBillNumber * 100) / 100.0;
        finalBillTextView.setText("Total Bill: $" + totalBillNumber);
    }
}
