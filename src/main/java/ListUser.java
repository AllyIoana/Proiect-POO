import java.util.ArrayList;

public class ListUser extends ComandaList {
    public void list(Streamer streamer, User user) {
        String temp = "[";
        ArrayList<Stream> streams = user.getStreams();
        for (int i = 0; i < streams.size(); i++) {
            temp += user.json(streams.get(i));
            if (i != streams.size() - 1) temp += ",";
        }
        temp += "]";
        System.out.println(temp);
    }
}
