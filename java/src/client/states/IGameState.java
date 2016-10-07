package client.states;

/**
 * Created by jihoon on 10/7/2016.
 */
public interface IGameState {

    public enum States{
        FIRST_ROUND,
        SECOND_ROUND,
        DISCARDING,
        ROBBING,
        ROLLING,
        NOT_MY_TURN;
    }

    public String getState();

    public void setState();


}
