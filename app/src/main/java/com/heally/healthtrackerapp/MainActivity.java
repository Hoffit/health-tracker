package com.heally.healthtrackerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
     * Application entry point.
     * Impl note - not sure but I think this is analogous to a Java main method in a spring boot app.
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
     * Creates a Java Thread instance to for the stopwatch to run in.
     * @param view The view widget source of the event.
     */
    public void startOrPauseStopwatch(View view) {
        Button button = findViewById(R.id.stopwatchStartOrPause);
        StopwatchButtonState buttonState = StopwatchButtonState.valueOf(button.getText().toString());
        Thread thread = null;

        // A thread implementation for the stopwatch
        class Runnable implements java.lang.Runnable {
            @Override
            public void run() {
                Thread thread = Thread.currentThread();
                TextView stopwatchDisplayTextView = findViewById(R.id.stopwatchReset);
                while (thread.isAlive()) {
                    stopwatchDisplayTextView.setText(String.valueOf(System.currentTimeMillis()));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        switch (buttonState) {
            case START:
                thread = new Thread(new Runnable());
                button.setText(StopwatchButtonState.PAUSE.toString());
//                runOnUiThread(thread); //Got CalledFromWrongThreadException and this helped me out: https://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
                break;
            case PAUSE:
                thread.interrupt();
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
                TextView stopwatchDisplayTextView = findViewById(R.id.stopwatchReset);
                stopwatchDisplayTextView.setText(Constants.STOPWATCH_START_STRING);
                break;
            case PAUSE:
                break;
            default:
                throw new IllegalStateException("Error - invalid button state.");
        }
    }
}
