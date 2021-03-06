package decoder.JsonModels;

/**
 * Created by bvanc on 10/3/2016.
 */
public class JsonTurnTracker {

    private String status;
    private int currentTurn;
    private int longestRoad;
    private int largestArmy;

    public JsonTurnTracker()
    {
        this.status = "FirstRound";
        this.currentTurn = 0;
        this.longestRoad = -1;
        this.largestArmy = -1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public int getLongestRoad() {
        return longestRoad;
    }

    public void setLongestRoad(int longestRoad) {
        this.longestRoad = longestRoad;


    }

    public int getLargestArmy() {
        return largestArmy;
    }

    public void setLargestArmy(int largestArmy) {
        this.largestArmy = largestArmy;
    }

    public void incrementTurn()
    {
        this.currentTurn++;
        this.currentTurn = this.currentTurn % 4;
    }

    public void decrementTurn()
    {
        this.currentTurn--;
    }

    public void beginSecondRound()
    {
        this.status = "SecondRound";
    }

    public void beginRollingState()
    {
        this.status = "Rolling";
    }

    public void beginRobbingState()
    {
        this.status = "Robbing";
    }

    public void beginPlayingState()
    {
        this.status = "Playing";
    }

    public void beginDiscardingState()
    {
        this.status = "Discarding";
    }

}
