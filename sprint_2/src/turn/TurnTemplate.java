package turn;

public abstract class TurnTemplate {

    public final void playTurn() {
        if (!hasAvailableActions()) {
            onLose();
            return;
        }

        selectWorker();

        if (!move()) {
            onLose();
            return;
        }

        if (checkWin()) {
            onWin();
            return;
        }

        build();
        endTurn();
    }

    // Abstract steps to be implemented in subclasses
    protected abstract void selectWorker();
    protected abstract boolean move();
    protected abstract boolean build();
    protected abstract boolean checkWin();
    protected abstract boolean hasAvailableActions();

    // Optionally overridable outcomes
    protected void onWin() {
        System.out.println("Player wins!");
    }

    protected void onLose() {
        System.out.println("Player loses (no valid moves/builds).");
    }

    protected void endTurn() {
        System.out.println("Turn ends. Next player's turn.");
    }
}
