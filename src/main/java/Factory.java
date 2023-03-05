public class Factory {
    public Stream createStream(int streamType, int id, int streamGenre, Long noOfStreams, int streamerId, Long length, Long dateAdded, String name) {
        switch (streamType) {
            case 1:
                return new Song(id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
            case 2:
                return new Podcast(id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
            case 3:
                return new Audiobook(id, streamGenre, noOfStreams, streamerId, length, dateAdded, name);
            default:
                return null;
        }
    }
}
