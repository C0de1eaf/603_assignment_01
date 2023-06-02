// This code defines an abstract class called `LifeLines`.
// It provides a blueprint for implementing the lifelines in the "Who Wants to Be a Millionaire" game.
package LifeLine;

public abstract class LifeLines {

    // This instance variable indicates whether or not the lifeline has been used.
    private boolean used = false;

    // This method returns the state of the `used` variable.
    public boolean isUsed() {
        return this.used;
    }

    // This method sets the `used` variable to `true` when the lifeline is used.
    public void lifeLineUsed() {
        this.used = true;
    }
    
    public void resetLifeLine(){
        this.used = false;
    }
}
