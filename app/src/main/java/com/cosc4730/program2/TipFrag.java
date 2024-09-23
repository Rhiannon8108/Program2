package com.cosc4730.program2;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.app.Activity;
import android.content.Context;

import com.cosc4730.program2.databinding.TipFragBinding;


public class TipFrag extends Fragment implements View.OnClickListener {

    private TipFragBinding binding;
    private com.cosc4730.program2.TipFrag.TipFragBindingListener listener;
    private int seekBarPercentage;
    private String inputField;
    private float enteredBill;
    private float totalBill;
    private float tipResult = 0.0f;
    private float roundedTip;
    private float roundedTotal;
    private int roundChoice = 0;

    public interface TipFragBindingListener {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = TipFragBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        //sets EditText to 0
        inputField = "0.00";
        binding.bill.setText(inputField);
        //from string to float
        enteredBill = Float.parseFloat(inputField);
        binding.calculate.setOnClickListener(this);
        binding.noRound.setOnClickListener(this);
        binding.roundTip.setOnClickListener(this);
        binding.roundTotal.setOnClickListener(this);

        //seek bar to calculate tip percent
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.tipPercent.setText(String.valueOf(seekBar.getProgress() + "%"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarPercentage = binding.seekBar.getProgress();
                //set the seekbar percent

            }
        });

        return binding.getRoot();
    }

    public void onClick(View view) {
        if (view == binding.calculate) {
            //calculate the initial bill
            calculateBill();

        }
        if (view == binding.noRound) {
            noRoundBill();
            //set the back button to false and the round buttons to true
            binding.noRound.setEnabled(false);
            binding.roundTip.setEnabled(true);
            binding.roundTotal.setEnabled(true);
            //recalculates the bill with no rounding
            calculateBill();

        } else if (view == binding.roundTip) {
            roundTip();
            //sets roundTip button to false so rounding is XOR
            binding.noRound.setEnabled(true);
            binding.roundTip.setEnabled(false);
            binding.roundTotal.setEnabled(true);
            //calculates bill with tip rounded
            calculateBill();

        } else if (view == binding.roundTotal) {
            roundTotal();
            binding.noRound.setEnabled(true);
            binding.roundTip.setEnabled(true);
            //sets roundTotal button false for XOR rounding
            binding.roundTotal.setEnabled(false);
            //recalculate bill with rounded total
            calculateBill();

        }
    }

    public void calculateBill(){
        //roundChoice decides which if statement is called
        if (roundChoice == 0){
            //interpret the bill as a float
            enteredBill = Float.parseFloat(binding.bill.getText().toString());
            tipResult = enteredBill * seekBarPercentage/ 100;
            //sets the tip result TextView
            binding.tipAmount.setText(" Tip $" + String.valueOf(tipResult));
            //calculate the total bill
            totalBill = enteredBill + tipResult;
            //set the total TextView
            binding.Total.setText(" Total $" +(totalBill));

        }else if(roundChoice == 1) {
            //interpret the bill as a float
            enteredBill = Float.parseFloat(binding.bill.getText().toString());
            //sets the tip result TextView as the rounded tip
            binding.tipAmount.setText(" Tip $" + String.valueOf(roundedTip));
            //calculates the new final bill
            totalBill = enteredBill + roundedTip;;
            //set the total TextView
            binding.Total.setText(" Total $" +(totalBill));

        }else if(roundChoice == 2){
            totalBill = roundedTotal;
            //set the total TextView to be rounded
            binding.Total.setText(" Total $" +(totalBill));

        }
    }
   public  void noRoundBill() {
        //sets roundChoice
        roundChoice = 0;
        //sets the tip amount Textview
       binding.tipAmount.setText(" Tip $" + String.valueOf(tipResult));
       //calculates bill
       totalBill = enteredBill + tipResult;
       //sets the total TextView
       binding.Total.setText(" Total $" +(totalBill));

   }
   public void roundTip(){
       //sets roundChoice
       roundChoice = 1;
       int rounded;
       //round the value of tipResult
       rounded = (int) Math.ceil(tipResult);
       //set roundTip to rounded value
       roundedTip = rounded;
       //set TextViw to rounded tip
       binding.tipAmount.setText(String.valueOf(roundedTip));
   }

   public void roundTotal(){
       //sets roundChoice
       roundChoice = 2;
       int rounded;
       //round the value of totalBill
       rounded = (int) Math.ceil(totalBill);
       //set roundTotal to rounded value
       roundedTotal = rounded;
       //set tip back to not rounded value
       tipResult = enteredBill * seekBarPercentage/ 100;
       //set TextViews to reflect the values
       binding.tipAmount.setText(" Tip $" + String.valueOf(tipResult));
       binding.Total.setText(String.valueOf(roundedTotal));

   }

}