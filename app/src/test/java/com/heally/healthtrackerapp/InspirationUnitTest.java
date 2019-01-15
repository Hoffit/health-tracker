package com.heally.healthtrackerapp;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class InspirationUnitTest {

    @Test
    public void testInspirationClass() {
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

        assertTrue(inspirations.get(0).getInspirationMessage().equals("You've drawn a crowd!"));
        assertTrue(inspirations.get(0).getInspirationDrawable() == R.drawable.krzysztof_niewolny_1080021_unsplash);

        assertTrue(inspirations.get(6).getInspirationMessage().equals("Whoa!"));
        assertTrue(inspirations.get(6).getInspirationDrawable() == R.drawable.ruben_bagues_750188_unsplash);
    }

    @Test
    public void fingerExerciseClicks() {
//        onView(withId(R.id.name_field)).perform(typeText("Steve"));
//        onView(withId(R.id.greet_button)).perform(click());
//        onView(withText("Hello Steve!")).check(matches(isDisplayed()));
    }

}