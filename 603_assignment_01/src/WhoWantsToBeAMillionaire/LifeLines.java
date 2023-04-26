package WhoWantsToBeAMillionaire;

public abstract class LifeLines {

    private boolean used = false;

    //Returns the state of the lifeline
    public boolean isUsed() {
        return this.used;
    }

    //Sets the used instance variable to true when the lifeline is used
    public void lifeLineUsed() {
        this.used = true;
    }
}
