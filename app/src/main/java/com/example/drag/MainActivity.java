package com.example.drag;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnTouchListener {

    TextView textView1;
    TextView textView2;
    float dX, dY;
    int centerX;
    int centerY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = (TextView) findViewById(R.id.textview1);
        textView2 = (TextView) findViewById(R.id.textview2);
        textView1.setOnTouchListener(this);

        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        centerX = mdispSize.x;
        centerY = mdispSize.y;

        textView1.setX(451);
        textView1.setY(848);

        textView2.setX(451);
        textView2.setY(848 + 300);

        System.out.println(centerY + ", " + centerY/2);

    }

    float x, y;
    float differenceX;
    float differenceY;

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:

                dX = view.getX() - event.getRawX();
                y = 848;
                differenceX = textView2.getX();
                differenceY = view.getX();
                break;

            case MotionEvent.ACTION_MOVE:

                x = event.getRawX() + dX;
                System.out.println(x);

                if (x <= 451 && view.getY() == 848 && textView2.getY() >= 848){

                    view.animate()
                            .x(x)
                            .setDuration(0)
                            .start();


                    textView2.animate().y(differenceY + textView1.getX() + 200).setDuration(0).start();
                }
                else if (view.getX() == 451 && view.getY() >= 848 && view.getY() <= 848 + 300){
                    view.animate()
                            .y(y/2+event.getRawX())
                            .setDuration(0)
                            .start();

                    textView2.animate().x(differenceX + view.getY() - 900).setDuration(0).start();
                }
//                }


                break;
            case MotionEvent.ACTION_UP:
                view.animate().x(centerX/2 - view.getWidth()/2).y(centerY/2 - view.getHeight()/2).setDuration(500).start();
                textView2.animate().x(centerX/2 - view.getWidth()/2).y(centerY/2 - view.getHeight()/2 + 300).setDuration(500).start();
                break;
            default:
                return false;
        }
        return true;
    }
}