import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class User {
    private int id;
    private String name;
    private ArrayList<Stream> streams;

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        streams = new ArrayList<>();
    }

    public void addStream(Stream stream) {
        streams.add(stream);
    }

    public void deleteStream(int streamId) {
        for (int i = 0; i < streams.size(); i++)
            if (streams.get(i).getId() == streamId) {
                streams.remove(i);
                break;
            }
    }

    public String json(Stream stream) {
        String temp = "{\"id\":\"" + stream.getId() + "\",\"name\":\"" + stream.getName() + "\",\"streamerName\":\"" + stream.getStreamer() +
                "\",\"noOfListenings\":\"" + stream.getNoOfStreams() + "\",\"length\":\"";
        Duration duration = Duration.ofSeconds(stream.getLength());
        Long hours = duration.toHours();
        Long minutes = duration.toMinutes() - 60 * hours;
        Long seconds = duration.toSeconds() - 60 * minutes - 3600 * hours;
        if (hours > 0)
            temp += String.format("%02d:%02d:%02d", hours, minutes, seconds);
        else
            temp += String.format("%02d:%02d", minutes, seconds);
        temp += "\",\"dateAdded\":\"";
        SimpleDateFormat dateAdded = new SimpleDateFormat("dd-MM-yyyy");
        dateAdded.setTimeZone(TimeZone.getTimeZone("GMT"));
        temp += dateAdded.format(new Date(stream.getDateAdded() * 1000)) + "\"}";
        return temp;
    }

    public boolean listen(int streamId) {

        for (int i = 0; i < streams.size(); i++) {
            if (streams.get(i).getId() == streamId) {
                streams.get(i).setNoOfStreams(streams.get(i).getNoOfStreams() + 1);
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Stream> getStreams() {
        return streams;
    }
}
