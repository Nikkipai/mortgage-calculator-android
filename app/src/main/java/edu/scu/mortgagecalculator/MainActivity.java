package edu.scu.mortgagecalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {


    TextView principalAmount;
    SeekBar aSeekBar;
    TextView seekText;
    RadioGroup rg;
    CheckBox taxIn;
    TextView monthlyPayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aSeekBar=(SeekBar) findViewById(R.id.aseekBar);
        seekText=(TextView) findViewById(R.id.seekText);
        Button calculate=(Button) findViewById(R.id.calculateButton);
        principalAmount =(TextView) findViewById(R.id.principalAmount);
        rg=(RadioGroup) findViewById(R.id.radioGroup1);
        taxIn=(CheckBox) findViewById(R.id.taxesCheckBox);
        monthlyPayment=(TextView)findViewById(R.id.monthlyPayment);

        aSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            double progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                double value =(progressValue/10.0);
                progress=value;

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
               seekText.setText(Double.toString(progress));

            }
        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(principalAmount.getText())){
                    Toast.makeText(MainActivity.this,"Please Enter Amount Borrowed",Toast.LENGTH_SHORT).show();
                    return;
                }
                double principalAmt = Double.parseDouble(principalAmount.getText().toString());
                double interest = Double.parseDouble(String.valueOf(seekText.getText().toString()));
                int loanTerm=0;
                double ans=0.0;
                int selectedId = rg.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    if(selectedId==R.id.term7){
                        loanTerm=7*12;
                    }else if(selectedId==R.id.term15){
                        loanTerm=15*12;
                    }else{
                        loanTerm=30*12;
                    }
                } else {
                    Toast.makeText(MainActivity.this,"Please select loan term",Toast.LENGTH_SHORT).show();
                    return;
                }
                double T=0.0;
                if(taxIn.isChecked()){
                   T=(0.1/100)*principalAmt;

                }
                if(interest!=0.0){
                    double J=interest/1200;
                    ans =(principalAmt * (J / (1 - Math.pow(1 + J,-loanTerm)))) + T;
                }else{
                    ans=(principalAmt/loanTerm) + T;
                }
                monthlyPayment.setText(String.format("$%.2f", ans));
            }

        });


    }

}
