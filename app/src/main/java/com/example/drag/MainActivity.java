package com.example.drag;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnTouchListener {

    float dX, dY;
    int centerX;
    int centerY;

    TextView[] textViews;
    int startX = 400;
    int startY = 800;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout myLayout = (FrameLayout) findViewById(R.id.layout_counter);

        textViews = new TextView[10];

        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = new TextView(this);
            textViews[i].setText("TextView # " + (i+1));
            textViews[i].setTextSize(20);
            textViews[i].setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT));

            myLayout.addView(textViews[i]);

        }



        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        centerX = mdispSize.x;
        centerY = mdispSize.y;

        textViews[0].setX(startX);
        textViews[0].setY(startY);
        textViews[0].setOnTouchListener(this);

        textViews[1].setX(startX);
        textViews[1].setY(startY + 300);

        System.out.println(centerY + ", " + centerY/2);

    }

    float x, y;
    float differenceX;
    float differenceY;
    int counter = 0;

    @Override
    public boolean onTouch(final View view, MotionEvent event) {

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:

                dX = textViews[counter].getX() - event.getRawX();
                y = startY;
                differenceX = textViews[counter + 1].getX();
                differenceY = textViews[counter].getX();
                break;

            case MotionEvent.ACTION_MOVE:

                x = event.getRawX() + dX;

                if (x <= startX && textViews[counter].getY() == startY && textViews[counter + 1].getY() >= startY){

                    textViews[counter].animate()
                            .x(x)
                            .setDuration(0)
                            .start();


                    textViews[counter + 1].animate().y(differenceY + textViews[counter].getX() + 200).setDuration(0).start();
                }
                else if (textViews[counter].getX() >= startX && textViews[counter].getY() >= startY && textViews[counter].getY() <= startY + 300){
                    textViews[counter].animate()
                            .y(y/2+event.getRawX())
                            .setDuration(0)
                            .start();

                    textViews[counter + 1].animate().x(differenceX + textViews[counter].getY() - 900).setDuration(0).start();
                }

                break;
            case MotionEvent.ACTION_UP:


                if (textViews[counter].getX() <= 150 && counter < 8){
                    textViews[counter].animate().x(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            textViews[counter].setVisibility(View.GONE);
                            textViews[counter].setOnTouchListener(null);
                            counter++;
                            textViews[counter].setOnTouchListener(MainActivity.this);
                            System.out.println("Counter Left: " + counter);

                        }
                    }).start();

                    textViews[counter + 1].animate().x(startX).y(startY).setDuration(500).start();

                    textViews[counter + 2].animate().x(startX).y(startY + 300).setDuration(500).start();


                }else if(textViews[counter + 1].getX() >= 650 && counter > 1){

                    textViews[counter + 1].animate().x(1080).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            textViews[counter + 1].setVisibility(View.GONE);
                            counter--;
                            textViews[counter].setOnTouchListener(MainActivity.this);
                            System.out.println("Counter: " + counter);

                        }
                    }).start();

                    textViews[counter].animate().x(startX).y(startY + 300).setDuration(500).start();

                    textViews[counter - 1].animate().x(startX).y(startY).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            textViews[counter - 1].setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            textViews[counter ].setOnTouchListener(null);
                            textViews[counter - 1].setOnTouchListener(MainActivity.this);
                        }
                    }).setDuration(500).start();
                }
                else{
                    textViews[counter].animate().x(startX).y(startY).setDuration(500).start();
                    textViews[counter + 1].animate().x(startX).y(startY + 300).setDuration(500).start();
                }

                break;
            default:
                return false;
        }
        return true;
    }
}