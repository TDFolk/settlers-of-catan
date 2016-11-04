package server.serverCommand;

/**The parent class for all commands to be executed on the server side
 * Created by bvanc on 11/4/2016.
 */
public abstract class Command {

    /**
     *
     * @return the entire model formatted as a JsonString
     */
    public abstract String execute();
    // The facade will create the command objects and call their execute methods: joinGameCommand.execute();

}
