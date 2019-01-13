package com.heally.healthtrackerapp;

/**
 * Inspiration for the user to keep exercising.
 */
public class Inspiration {

    /**
     * Inspirational message for the image.
     */
    private String inspirationMessage;

    /**
     * Inspirational image for the message.
     */
    private int inspirationDrawable;

    /**
     * Constructor.
     * @param inspirationMessage Inspirational message.
     * @param inspirationDrawable Inspirational image (drawable).
     */
    public Inspiration(String inspirationMessage, int inspirationDrawable) {
        this.inspirationMessage = inspirationMessage;
        this.inspirationDrawable = inspirationDrawable;
    }

    /**
     * Getter.
     * @return The message.
     */
    public String getInspirationMessage() {
        return inspirationMessage;
    }

    /**
     * Getter.
     * @return The drawable (image).
     */
    public int getInspirationDrawable() {
        return inspirationDrawable;
    }
}
