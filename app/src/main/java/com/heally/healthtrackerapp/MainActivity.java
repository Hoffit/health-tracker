package com.heally.healthtrackerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Application Entry Point for Android app: Health Tracker
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Keeps track of the number of times that the user pushed the finger exercise button.
     * Design note - don't think this is optimal yet - seems like we should model a domain
     * object, where this property would reside.
     */
    private int fingerExerciseCount;

    /**
     * The stopwatch.
     * A thread implementation for the stopwatch feature.
     * API doc was mixed bag: https://developer.android.com/guide/components/processes-and-threads#WorkerThreads
     */
    class Stopwatch implements Runnable {
        long timer;
        final TextView stopwatchDisplayTextView = findViewById(R.id.stopwatchDisplay);

        @Override
        public void run() {
            timer
            stopwatchDisplayTextView.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            stopwatchDisplayTextView.setText(getStopwatchFormattedTime());
                        }
                    },
                    100
            );
        }

        //helpful https://stackoverflow.com/a/10364430
        private String getStopwatchFormattedTime() {
            timer = timer + System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("H:m:ss:SSS");
            Date date = new Date(timer);
            return sdf.format(date);
        }
    }

    /**
     * The stopwatch.
     */
    private Stopwatch stopwatch;
    /**
     * Thread for the stopwatch
     */
    private Thread stopwatchThread;

    /**
     * Application entry point.
     * Impl note - not sure but I think this is analogous to a Java main method in a spring boot app.
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //maybe this shit will fucking work
        stopwatch = new Stopwatch();
        stopwatchThread = new Thread(stopwatch);
    }

    /**
     * Processes an event on the view widget.
     * The view is the fingerExercise button, and this method is called for event onClick.
     * For each call, update the fingerExerciseCount text field.
     * Learning note: defining a method with this specific signature, allows the system to recognize
     * it as a valid event handler that can be associated with a view object, like a button.
     * Must be public void with exactly one param of type View.
     * @param view The view widget source of the event.
     */
    public void fingerExercise(View view) {
        TextView exerciseCountTextView = findViewById(R.id.fingerExerciseCount);
        fingerExerciseCount++;
        exerciseCountTextView.setText( String.valueOf(fingerExerciseCount) );
    }

    /**
     * Processes the onClick event for the Start/Pause stopwatch button. Alternates between starting (or resuming)
     * the stopwatch, and stopping it.
     * The format of the stopwatch is 0:00:00:000 (0H, 00M, 00S, 000MS).
     * Creates a Java Thread instance to for the stopwatch to run in.
     * @param view The view widget source of the event.
     */
    public void startOrPauseStopwatch(View view) {
        Button button = findViewById(R.id.stopwatchStartOrPause);
        StopwatchButtonState buttonState = StopwatchButtonState.valueOf(button.getText().toString());
        switch (buttonState) {
            case START:
                stopwatch.running = true;
                if(!stopwatchThread.isAlive()) {
                    stopwatchThread.start();
                }
                button.setText(StopwatchButtonState.PAUSE.toString());
                break;
            case PAUSE:
                stopwatch.running = false;
                button.setText(StopwatchButtonState.START.toString());
                break;
            default:
                throw new IllegalStateException("Error - invalid button state.");
        }
    }

    /**
     * Processes the onClick event for the reset stopwatch button.
     * @param view The view widget source of the event.
     */
    public void resetStopwatch(View view) {
        Button button = findViewById(R.id.stopwatchStartOrPause);
        StopwatchButtonState buttonState = StopwatchButtonState.valueOf(button.getText().toString());
        switch (buttonState) {
            case START:
                TextView stopwatchDisplayTextView = findViewById(R.id.stopwatchDisplay);
                stopwatchDisplayTextView.setText(Constants.STOPWATCH_START_STRING);
                break;
            case PAUSE:
                break;
            default:
                throw new IllegalStateException("Error - invalid button state.");
        }
    }
}
