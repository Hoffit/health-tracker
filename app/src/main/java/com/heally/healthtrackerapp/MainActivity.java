package com.heally.healthtrackerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

/**
 * Application Entry Point for Android app: Health Tracker
 * Having some fun: bugs are strong! (and weird).
 * Lead inspiration image: the idea is that a crowd has gathered
 * to see how the user does with this challenge.
 * If the user does well, user will be adored by cool bugs!
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
     */
    private Stopwatch stopwatch;

    /**
     * Thread for the stopwatch
     */
    private Thread stopwatchThread;

    /**
     * Stopwatch startTime
     */
    private Instant startTime;

    /**
     * The images for the carousel.
     */
    HashMap<Integer, Inspiration> inspirations = new HashMap<Integer, Inspiration>() {
        {
            put(0, new Inspiration("You've drawn a crowd!", R.drawable.krzysztof_niewolny_1080021_unsplash));
            put(1, new Inspiration("You're on top!", R.drawable.sian_cooper_1248257_unsplash));
            put(2, new Inspiration("You're sooooo fly!", R.drawable.annelie_turner_230962_unsplash));
            put(3, new Inspiration("You're being noticed now!", R.drawable.matheus_queiroz_737500_unsplash));
            put(4, new Inspiration("You've done it!", R.drawable.michael_podger_26459_unsplash));
            put(5, new Inspiration("Strong as an ant!", R.drawable.mi_shots_410599_unsplash));
            put(6, new Inspiration("Whoa!", R.drawable.ruben_bagues_750188_unsplash));
        }
    };

    /**
     * The index for the currently displayed inspiration.
     */
    private int inspirationIndex = 0;

    /**
     * The stopwatch.
     * A thread implementation for the stopwatch feature.
     * API doc was mixed bag: https://developer.android.com/guide/components/processes-and-threads#WorkerThreads
     * https://stackoverflow.com/a/12937153
     */
    class Stopwatch implements Runnable {
        final TextView stopwatchDisplayTextView = findViewById(R.id.stopwatchDisplay);
        boolean running;

        @Override
        public void run() {
            running = true;
            if (startTime == null) {
                startTime = Instant.now();
            }
            while(running) {
                stopwatchDisplayTextView.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            stopwatchDisplayTextView.setText(getStopwatchFormattedTime());
                        }
                    }
                );
                try {
                    stopwatchThread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Helper method to generate the stopwatch timestamp.
         * Helpful: https://stackoverflow.com/a/10364430
         * @return String representation of elapsed time.
         */
        private String getStopwatchFormattedTime() {
            Duration duration = Duration.between(startTime, Instant.now());
            long millis = duration.toMillis() % 1000;

            //Construct the millisecond part of the stopwatch display, with leading zeroes when applicable
            String millisString;
            if (millis < 10) {
                millisString = "00" + millis;
            }
            else if(millis < 100) {
                millisString = "0" + millis;
            }
            else {
                millisString = String.valueOf(millis);
            }

            millis = duration.toMillis();

            //Construct the seconds part of the stopwatch display, with leading zero when applicable
            long seconds = (millis/1000)%60;
            String secondsString;
            if (seconds < 10) {
                secondsString = "0" + seconds;
            }
            else {
                secondsString = String.valueOf(seconds);
            }

            //Construct the minutes part of the stopwatch display, with leading zero when applicable
            long minutes = ((millis/1000)/60)%60;
            String minutesString;
            if (minutes < 10) {
                minutesString = "0" + minutes;
            }
            else {
                minutesString = String.valueOf(minutes);
            }

            //Construct the hours part of the stopwatch display
            String hours = String.valueOf(duration.toHours()%10);

            return hours + ':' + minutesString + ':' + secondsString + '.' + millisString;
        }
    }

    /**
     * Application entry point.
     * Impl note - not sure but I think this is analogous to a Java main method in a spring boot app.
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stopwatch = new Stopwatch();
        stopwatchThread = new Thread(stopwatch);
    }

    /**
     * Increments the image to the next one.
     * @param view The next button that triggered the event.
     */
    public void nextInspiration(View view) {
        inspirationIndex = (inspirationIndex == inspirations.size() - 1) ? (0) : (inspirationIndex + 1);
        setInspirationForIndex(inspirationIndex);
    }

    /**
     * Increments the image to the previous one.
     * @param view The next button that triggered the event.
     */
    public void previousInspiration(View view) {
        inspirationIndex = (inspirationIndex == 0) ? (inspirations.size() - 1) : (inspirationIndex - 1);
        setInspirationForIndex(inspirationIndex);
    }

    /**
     * Helper method to change the inspiration message and image to the specified index.
     * @param index The index in the map of inspirations to change to.
     */
    private void setInspirationForIndex(int index) {
        ImageView inspirationImage = findViewById(R.id.inspirationImage);
        inspirationImage.setImageResource(inspirations.get(inspirationIndex).getInspirationDrawable());
        TextView inspirationMessage = findViewById(R.id.inspireMessage);
        inspirationMessage.setText(inspirations.get(inspirationIndex).getInspirationMessage());
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

    // Issue - Stopwatch Implementation
    // Never could get the thread methods like wait/notify/interrupt to work, so ended up with this
    // terrible approach of destroying creating threads every time a stopwatch button is pushed.

    /**
     * Processes the onClick event for the Start/Pause stopwatch button. Alternates between starting (or resuming)
     * the stopwatch, and stopping it.
     * The format of the stopwatch is 0:00:00:000 (0H, 00M, 00S, 000MS).
     * Creates a Java Thread instance to for the stopwatch to run in.
     * @param view The view widget source of the event.
     */
    public void startOrPauseStopwatch(View view) throws InterruptedException {
        Button button = findViewById(R.id.stopwatchStartOrPause);
        StopwatchButtonState buttonState = StopwatchButtonState.valueOf(button.getText().toString());
        switch (buttonState) {
            case START:
                stopwatchThread.start();
                button.setText(StopwatchButtonState.PAUSE.toString());
                break;
            case PAUSE:
                stopwatch.running = false;
                stopwatch = new Stopwatch();
                stopwatchThread = new Thread(stopwatch);
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
                stopwatch = new Stopwatch();
                stopwatchThread = new Thread(stopwatch);
                startTime = null;
                break;
            case PAUSE:
                break;
            default:
                throw new IllegalStateException("Error - invalid button state.");
        }
    }
}
