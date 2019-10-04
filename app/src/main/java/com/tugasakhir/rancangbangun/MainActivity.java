package com.tugasakhir.rancangbangun;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    float x1,x2,y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.activity_depan);

        ImageView logo =  (ImageView) findViewById(R.id.logo);
        hideNav();
        // new Handler().postDelayed(new Runnable() {
        //    @Override
        //    public void run() {
        //        Intent homeIntent = new Intent(MainActivity.this, Login.class);
        //        startActivity(homeIntent);
        //        finish();
        //    }
        // }, SPLASH);

    }
    @Override
    protected void onResume() {
        super.onResume();
        hideNav();
    }

    public void onBackPressed(){
        finish();
        moveTaskToBack(true);
    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch (touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2){
                }
                else if(x1 > x2){
                    Intent i = new Intent(MainActivity.this, Login.class);
                    if(Build.VERSION.SDK_INT> 20){
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                        startActivity(i,options.toBundle());
                    }else {
                        startActivity(i);
                    }
                }
                break;
        }
        return false;
    }

    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(400);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }

    private void hideNav(){
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
    }


}
