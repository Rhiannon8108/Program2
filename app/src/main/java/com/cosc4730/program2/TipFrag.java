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
    private float enteredBill;
    private float totalBill;
    private float tipResult = 0.0f;
    private float roundedEnteredBill;
    private float roundedTip;
    private float roundedTotal;

    public interface TipFragBindingListener {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = TipFragBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String inputField = binding.bill.getText().toString();
        binding.bill.setText(inputField);
        //enteredBill = Float.parseFloat(inputField);
        System.out.println(enteredBill);
        binding.calculate.setOnClickListener(this);
        binding.roundBill.setOnClickListener(this);
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

      //  binding.button01.setOnClickListener(this);

        return binding.getRoot();
    }

    public void onClick(View view) {
        if (view == binding.calculate) {
            calculateBill();
            System.out.println("Button clicked01");

        }
        if (view == binding.roundBill) {
            roundBill();
            binding.roundTip.setEnabled(false);
            binding.roundTotal.setEnabled(false);
            System.out.println(roundedEnteredBill);

        } else if (view == binding.roundTip) {
            roundTip();
            binding.roundBill.setEnabled(false);
            binding.roundTotal.setEnabled(false);
            System.out.println(roundedTip);

        } else if (view == binding.roundTotal) {
            roundTotal();
            binding.roundBill.setEnabled(false);
            binding.roundTip.setEnabled(false);
            System.out.println(roundedTotal);

        }
    }

    public void calculateBill(){
        if (!binding.bill.getText().toString().isEmpty()){
            enteredBill = Float.parseFloat(binding.bill.getText().toString());
            tipResult = enteredBill * seekBarPercentage/ 100;
            binding.tipAmount.setText(" Tip $" + String.valueOf(tipResult));
            totalBill = enteredBill + tipResult;
            binding.Total.setText(" Total $" +(totalBill));
        }else {
          binding.bill.setText("0");
        }

    }

   public  void roundBill() {
       int rounded;
       System.out.println(enteredBill);
       rounded = (int) Math.ceil(enteredBill);
       roundedEnteredBill = rounded;
       binding.bill.setText(String.valueOf(roundedEnteredBill));
   }
   public void roundTip(){
       int rounded;
       rounded = (int) Math.ceil(tipResult);
       roundedTip =rounded;
       binding.tipAmount.setText(String.valueOf(roundedTip));
   }

   public void roundTotal(){
       int rounded;
       rounded = (int) Math.ceil(totalBill);
       roundedTotal = rounded;
       binding.Total.setText(String.valueOf(roundedTotal));
   }
}