package wit_connection;

/**
 * Created by bill on 11/16/17.
 */

public interface WitResponseMessage {

    void ErrorCommand(int msg);

    void Message(String search, String application, String conf);
}
