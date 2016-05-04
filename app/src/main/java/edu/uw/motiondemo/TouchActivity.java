package edu.uw.motiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class TouchActivity extends Activity {

    private static final String TAG = "Touch";

    private DrawingSurfaceView view;

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (DrawingSurfaceView)findViewById(R.id.drawingView);

        detector = new GestureDetector(this, new MyGestureListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean gestured = detector.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();

        int pointIndex = MotionEventCompat.getActionIndex(event);
        int pointId = MotionEventCompat.getPointerId(event, pointIndex);

        //handle action
        int action = MotionEventCompat.getActionMasked(event);
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG,"Touch!");

                view.addTouch(pointId, event.getX(pointIndex), event.getY(pointIndex));

                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.v(TAG, "Another finger!");

                view.addTouch(pointId, event.getX(pointIndex), event.getY(pointIndex));

                return true;
            case MotionEvent.ACTION_POINTER_UP:
                Log.v(TAG, "Finger up!");

                view.removeTouch(pointId);

                return true;
            case MotionEvent.ACTION_UP:
                Log.v(TAG, "Last finger up!");

                view.removeTouch(pointId);

                return true;
            case (MotionEvent.ACTION_MOVE) : //move
                int pointerCount = event.getPointerCount();

                for (int i = 0; i < pointerCount; i++) {
                    int pntID = event.getPointerId(i);

                    view.moveTouch(pntID, event.getX(i), event.getY(i));
                }
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //what do we do on the fling

            view.ball.dx = .03f*velocityX;
            view.ball.dy = .03f*velocityY;

            return true;
        }
    }
}
