import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class Streamer {
    private final int streamerType;
    private final int id;
    private final String name;
    private ArrayList<Stream> streams;
    private int noStreams = 0;

    public Streamer(int streamerType, int id, String name) {
        this.streamerType = streamerType;
        this.id = id;
        this.name = name;
        this.streams = new ArrayList<>();
    }

    public void addStream(Stream stream) {
        streams.add(stream);
        setNoStreams(getNoStreams() + 1);
    }

    public void deleteStream(int streamId) {
        for (int i = 0; i < streams.size(); i++)
            if (streams.get(i).getId() == streamId) {
                streams.remove(i);
                break;
            }
    }

    public String json(Stream stream) {
        String temp = "{\"id\":\"" + stream.getId() + "\",\"name\":\"" + stream.getName() + "\",\"streamerName\":\"" + stream.getStreamer() + "\",\"noOfListenings\":\"" + stream.getNoOfStreams() + "\",\"length\":\"";
        Duration duration = Duration.ofSeconds(stream.getLength());
        Long hours = duration.toHours();
        Long minutes = duration.toMinutes() - 60 * hours;
        Long seconds = duration.toSeconds() - 60 * minutes - 3600 * hours;
        if (hours > 0) temp += String.format("%02d:%02d:%02d", hours, minutes, seconds);
        else temp += String.format("%02d:%02d", minutes, seconds);
        temp += "\",\"dateAdded\":\"";
        SimpleDateFormat dateAdded = new SimpleDateFormat("dd-MM-yyyy");
        dateAdded.setTimeZone(TimeZone.getTimeZone("GMT"));
        temp += dateAdded.format(new Date(stream.getDateAdded() * 1000)) + "\"}";
        return temp;
    }

    public int getNoStreams() {
        return noStreams;
    }

    public void setNoStreams(int noStreams) {
        this.noStreams = noStreams;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Stream> getStreams() {
        return streams;
    }

    public String getName() {
        return name;
    }
}
