package com.company.larinotines;

import static com.company.larinotines.Settings.action;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.company.larinotines.databinding.ActivityGameBinding;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private ActivityGameBinding binding;
    public static boolean active = false;
    Dialog dialog;
    int current = 0;
    int counter = 0;
    ObjectAnimator greyBasket;
    ObjectAnimator blackBasket;
    ObjectAnimator animDown;
    int[] clothes = {R.drawable.red_1, R.drawable.red_2, R.drawable.red_3, R.drawable.red_4,
                     R.drawable.pink_1, R.drawable.pink_2, R.drawable.pink_3,
                     R.drawable.orange_1, R.drawable.orange_2, R.drawable.orange_3, R.drawable.orange_4, R.drawable.orange_5,
                     R.drawable.green_1, R.drawable.green_2, R.drawable.green_3, R.drawable.green_4,
                     R.drawable.blue_1, R.drawable.blue_2, R.drawable.blue_3,
                     R.drawable.yellow_1, R.drawable.yellow_2, R.drawable.yellow_3, R.drawable.yellow_4, R.drawable.yellow_5,
                     R.drawable.black_1, R.drawable.black_2, R.drawable.black_3, R.drawable.black_4, R.drawable.black_5,
                     R.drawable.grey_1, R.drawable.grey_2, R.drawable.grey_3, R.drawable.grey_4, R.drawable.grey_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        audioSettings();
        windowSettings();
        dialogSettings();
        active = true;
    }

    public void greyBasket(View v) {
        if (!animDown.isRunning()) {
            action(GameActivity.this);
            greyBasket.start();
            animDown.start();
        }
    }

    public void redBasket(View v) {
        if (!animDown.isRunning()) {
            action(GameActivity.this);
            animDown.start();
        }
    }

    public void blackBasket(View v) {
        if (!animDown.isRunning()) {
            action(GameActivity.this);
            blackBasket.start();
            animDown.start();
        }
    }

    public void audioSettings() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
    }

    public void windowSettings() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        current = new Random().nextInt(33);
        binding.clotheFrame.setImageResource(clothes[current]);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                greyBasket = ObjectAnimator.ofFloat(binding.clotheFrame, "translationX", -binding.redBasket.getX()).setDuration(1000);
                blackBasket = ObjectAnimator.ofFloat(binding.clotheFrame, "translationX", binding.redBasket.getX()).setDuration(1000);
                animDown = ObjectAnimator.ofFloat(binding.clotheFrame, "translationY", binding.linearLayout.getY() - binding.clotheFrame.getY()).setDuration(1000);

                animDown.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (binding.clotheFrame.getX() > binding.linearLayout.getX() + binding.blackBasket.getX()) {
                            if (current >= 24 && current <= 33) {
                                ++counter;
                                binding.counter.setText(counter+"");
                            } else {
                                dialog.show();
                            }
                        }
                        else if (binding.clotheFrame.getX() > binding.linearLayout.getX() + binding.redBasket.getX()) {
                            if (current >= 0 && current <= 11) {
                                ++counter;
                                binding.counter.setText(counter+"");
                            } else {
                                dialog.show();
                            }
                        }
                        else {
                            if (current >= 12 && current <= 23) {
                                ++counter;
                                binding.counter.setText(counter+"");
                            } else {
                                dialog.show();
                            }
                        }
                        binding.clotheFrame.setTranslationX(0);
                        binding.clotheFrame.setTranslationY(0);
                        current = new Random().nextInt(33);
                        binding.clotheFrame.setImageResource(clothes[current]);
                    }
                });
            }
        }, 100);


    }

    private void dialogSettings() {
        dialog = new Dialog(GameActivity.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        WindowManager.LayoutParams wlp = dialog.getWindow().getAttributes();
        wlp.dimAmount = 0.7f;
        dialog.getWindow().setAttributes(wlp);
        dialog.setContentView(R.layout.dialog);
    }

    @Override public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void home(View v) {
        action(GameActivity.this);
        finish();
    }

    public void replay(View v){
        action(GameActivity.this);
        counter = 0;
        binding.counter.setText("0");
        dialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}

