import java.util.ArrayList;

public class ListStreamer extends ComandaList {
    public void list(Streamer streamer, User user) {
        String temp = "[";
        ArrayList<Stream> streams = streamer.getStreams();
        for (int i = 0; i < streams.size(); i++) {
            temp += streamer.json(streams.get(i));
            if (i != streams.size() - 1) temp += ",";
        }
        temp += "]";
        System.out.println(temp);
    }
}
