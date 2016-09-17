package server;

/**
 * Created by jihoon on 9/16/2016.
 */
public interface IServer {
    public Boolean userLogin();

    public Boolean userRegister();

    public Boolean gameList();

    public Boolean gameCreate();

    public Boolean gameJoin();

    public Boolean gameSave();

    public Boolean gameLoad();

    public Boolean gameModelVersion();

    public Boolean gameReset();

    public Boolean gameCommandGet();

    public Boolean gameCommandPost();

    public Boolean gameListAI();

    public Boolean gameAddAI();

    public Boolean utilChangeLogLevel();
}
