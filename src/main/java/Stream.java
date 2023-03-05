abstract class Stream {
    private final int streamType;
    private final int id;
    private final int streamGenre;
    private final int streamerId;
    private final Long length;
    private final Long dateAdded;
    private final String name;
    private Long noOfStreams;
    private String streamerName;

    public Stream(int streamType, int id, int streamGenre, Long noOfStreams, int streamerId, Long length, Long dateAdded, String name) {
        this.streamType = streamType;
        this.id = id;
        this.streamGenre = streamGenre;
        this.noOfStreams = noOfStreams;
        this.streamerId = streamerId;
        this.length = length;
        this.dateAdded = dateAdded;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getNoOfStreams() {
        return noOfStreams;
    }

    public void setNoOfStreams(Long noOfStreams) {
        this.noOfStreams = noOfStreams;
    }

    public Long getLength() {
        return length;
    }

    public Long getDateAdded() {
        return dateAdded;
    }

    public String getStreamer() {
        return streamerName;
    }

    public void setStreamer(String streamerName) {
        this.streamerName = streamerName;
    }

    public int getStreamerId() {
        return streamerId;
    }
}
