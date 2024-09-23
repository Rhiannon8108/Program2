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
    private float roundedEnteredBill;
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
        inputField = "0.00";
        binding.bill.setText(inputField);
        enteredBill = Float.parseFloat(inputField);
        System.out.println(enteredBill);
        binding.calculate.setOnClickListener(this);
        binding.noRound.setOnClickListener(this);
        binding.roundTip.setOnClickListener(this);
        binding.roundTotal.setOnClickListener(this);
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

            }
        });

        return binding.getRoot();
    }

    public void onClick(View view) {
        if (view == binding.calculate) {
            calculateBill();

        }
        if (view == binding.noRound) {
            noRoundBill();
            binding.noRound.setEnabled(false);
            binding.roundTip.setEnabled(true);
            binding.roundTotal.setEnabled(true);
            calculateBill();
            System.out.println(roundedEnteredBill);

        } else if (view == binding.roundTip) {
            roundTip();
            binding.noRound.setEnabled(true);
            binding.roundTip.setEnabled(false);
            binding.roundTotal.setEnabled(true);
            calculateBill();
            System.out.println(roundedTip);

        } else if (view == binding.roundTotal) {
            roundTotal();
            binding.noRound.setEnabled(true);
            binding.roundTip.setEnabled(true);
            binding.roundTotal.setEnabled(false);
            calculateBill();
            System.out.println(roundedTotal);
        }
    }

    public void calculateBill(){
        if (roundChoice == 0){
            enteredBill = Float.parseFloat(binding.bill.getText().toString());
            tipResult = enteredBill * seekBarPercentage/ 100;
            binding.tipAmount.setText(" Tip $" + String.valueOf(tipResult));
            totalBill = enteredBill + tipResult;
            System.out.println(tipResult);
            System.out.println(roundChoice);
            binding.Total.setText(" Total $" +(totalBill));

        }else if(roundChoice == 1) {
            enteredBill = Float.parseFloat(binding.bill.getText().toString());
            binding.tipAmount.setText(" Tip $" + String.valueOf(roundedTip));
            System.out.println(roundedTip);
            totalBill = enteredBill + roundedTip;
            System.out.println(roundedTip);
            binding.Total.setText(" Total $" +(totalBill));
            System.out.println(roundChoice);

        }else if(roundChoice == 2){
            totalBill = roundedTotal;
            binding.Total.setText(" Total $" +(totalBill));
            System.out.println(roundChoice);

        }
    }
   public  void noRoundBill() {
        roundChoice = 0;
       binding.tipAmount.setText(" Tip $" + String.valueOf(tipResult));
       totalBill = enteredBill + tipResult;
       binding.Total.setText(" Total $" +(totalBill));

   }
   public void roundTip(){
       int rounded;
       rounded = (int) Math.ceil(tipResult);
       roundedTip = rounded;
       binding.tipAmount.setText(String.valueOf(roundedTip));
       roundChoice = 1;
       System.out.println(roundChoice);
   }

   public void roundTotal(){
       int rounded;
       rounded = (int) Math.ceil(totalBill);
       roundedTotal = rounded;
       System.out.println(roundedTotal);
       tipResult = enteredBill * seekBarPercentage/ 100;
       binding.tipAmount.setText(" Tip $" + String.valueOf(tipResult));
       binding.Total.setText(String.valueOf(roundedTotal));
       roundChoice = 2;
       System.out.println(roundChoice);

   }

}