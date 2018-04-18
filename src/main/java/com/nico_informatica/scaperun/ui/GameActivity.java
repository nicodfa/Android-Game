package com.nico_informatica.scaperun.ui;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nico_informatica.scaperun.R;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.Timer;


public class GameActivity extends AppCompatActivity {

    //ImageView IvFondo;
    //ValueAnimator animator;
    AnimationDrawable dinoCaminando;
    AnimationDrawable dinoSaltando;
    LinearLayout fondoScroll;
    Animation animationBg;
    Animation saltoDino;
    Animation animationNube;
    ImageView IvDino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        final TextView distance = (TextView) findViewById(R.id.tvLong);

        distance.setText(String.valueOf(0));

        ///////////////////////////////ANIMACION DINO/////////////////////////////////////

        IvDino = (ImageView) findViewById(R.id.dino);

        IvDino.setBackgroundResource(R.drawable.animacion_dino);
        dinoCaminando = (AnimationDrawable) IvDino.getBackground();

        dinoCaminando.start();

        saltoDino = AnimationUtils.loadAnimation(this, R.anim.animacion_salto);

        saltoDino.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        dinoSaltando.stop();
                        IvDino.setBackgroundResource(R.drawable.animacion_dino);
                        dinoCaminando = (AnimationDrawable) IvDino.getBackground();
                        dinoCaminando.start();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
        ///////////////////////AL TOCAR LA PANTALLA PARA SALTO //////////
        LinearLayout LlTouch = (LinearLayout) findViewById(R.id.LlTouch);
        LlTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dinoCaminando.stop();
                IvDino.setBackgroundResource(R.drawable.animacion_salto);
                dinoSaltando = (AnimationDrawable) IvDino.getBackground();
                dinoSaltando.start();
                IvDino.startAnimation(saltoDino);
                return false;
            }
        });

        /////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////FONDO////////////////////////////////////////

        fondoScroll = (LinearLayout) findViewById(R.id.fondo);
        animationBg = AnimationUtils.loadAnimation(this,R.anim.fondo_animacion);
        fondoScroll.startAnimation(animationBg);

        animationBg.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                UpdateScore(distance);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                UpdateScore(distance);
            }
        });

        ImageView IvNube = (ImageView) findViewById(R.id.IvNube);

        nubeAleatoria(IvNube);

        /*final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);


        animator = ValueAnimator.ofFloat(1.0f, 0.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000L);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();*/

        //////////////////////////////////PAUSA/////////////////////////////////////////////

        ImageButton btPause = (ImageButton) findViewById(R.id.btPause);
        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dinoCaminando.stop();
                fondoScroll.clearAnimation();
                registerForContextMenu(v);
                openContextMenu(v);

            }
        });



    }

    public void UpdateScore(TextView tw){
        int score = Integer.parseInt(tw.getText().toString());
        tw.setText(String.valueOf(score+5));
    }


    public void nubeAleatoria( ImageView nube){
        Random r = new Random();
        int aleator = r.nextInt(2);
        if(System.currentTimeMillis() % aleator == 0 ){
            animationNube = AnimationUtils.loadAnimation(this, R.anim.animacion_nube);
            animationNube.setFillAfter(true);

            nube.startAnimation(animationNube);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        this.getMenuInflater().inflate(R.menu.game_menu, menu);
        menu.setHeaderTitle("PAUSE");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean toret = false;

        switch (item.getItemId()){
            case R.id.btInicio:

                this.finish();
                break;
            case R.id.btReanudar:
                GameActivity.this.fondoScroll.startAnimation(animationBg);
                GameActivity.this.dinoCaminando.start();
                this.closeContextMenu();
                break;
        }

        return toret;

    }
}
