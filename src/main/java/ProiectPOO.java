import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ProiectPOO {
    private static ProiectPOO instantaUnica;

    private ProiectPOO() {
    }

    public static ProiectPOO Instanta() {
        if (instantaUnica == null)
            instantaUnica = new ProiectPOO();
        return instantaUnica;
    }

    public static void main(String[] args) {
        if (args == null) {
            System.out.println("Nothing to read here");
            return;
        }
        ProiectPOO proiect = Instanta();
        Factory factory = new Factory();
        ArrayList<Streamer> streamers = new ArrayList<>();
        ArrayList<Stream> streams = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        // citire din streamers.csv
        try {
            FileReader input = new FileReader("src/main/resources/" + args[0]);
            BufferedReader buffer = new BufferedReader(input);
            String line = buffer.readLine();
            line = buffer.readLine();
            while (line != null) {
                String[] temp = line.split("\",\"");
                Streamer tempstreamer = new Streamer(Integer.parseInt(temp[0].replace("\"", "")), Integer.parseInt(temp[1]), temp[2].replace("\"", ""));
                streamers.add(tempstreamer);
                line = buffer.readLine();
            }
            buffer.close();
            input.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        // citire din streams.csv si adaugare stream in lista streamer-ului potrivit
        try {
            FileReader input = new FileReader("src/main/resources/" + args[1]);
            BufferedReader buffer = new BufferedReader(input);
            String line = buffer.readLine();
            line = buffer.readLine();
            while (line != null) {
                String[] temp = line.split("\",\"");
                Stream tempstream = factory.createStream(Integer.parseInt(temp[0].replace("\"", "")), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), Long.parseLong(temp[3]), Integer.parseInt(temp[4]), Long.parseLong(temp[5]), Long.parseLong(temp[6]), temp[7].replace("\"", ""));
                streams.add(tempstream);
                for (int i = 0; i < streamers.size(); i++)
                    if (streamers.get(i).getId() == Integer.parseInt(temp[4])) {
                        tempstream.setStreamer(streamers.get(i).getName());
                        streamers.get(i).addStream(tempstream);
                        break;
                    }
                line = buffer.readLine();
            }
            buffer.close();
            input.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        // citire din users.csv si adaugare user cu stream-uri
        try {
            FileReader input = new FileReader("src/main/resources/" + args[2]);
            BufferedReader buffer = new BufferedReader(input);
            String line = buffer.readLine();
            line = buffer.readLine();
            while (line != null) {
                String[] temp = line.split("\",\"");
                User tempUser = new User(Integer.parseInt(temp[0].replace("\"", "")), temp[1]);
                String[] temp2 = (temp[2].replace("\"", "")).split(" ");
                for (int i = 0; i < temp2.length; i++)           //stream id
                    for (int j = 0; j < streams.size(); j++)    //streams
                        if (Integer.parseInt(temp2[i]) == streams.get(j).getId()) {
                            tempUser.addStream(streams.get(j));
                            break;
                        }
                users.add(tempUser);
                line = buffer.readLine();
            }
            buffer.close();
            input.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        // citire din comenzi.csv si rezolvare cerinte
        try {
            FileReader input = new FileReader("src/main/resources/" + args[3]);
            BufferedReader buffer = new BufferedReader(input);
            String line = buffer.readLine();
            while (line != null) {
                String[] temp = line.split(" ");
                for (int i = 0; i < temp.length; i++)
                    temp[i] = temp[i].replace("\"", "");
                User user;
                String output;
                switch (temp[1]) {

                    /* ---Adauga Stream--- */
                    case "ADD":
                        Stream tempstream = factory.createStream(Integer.parseInt(temp[2]), Integer.parseInt(temp[3]), Integer.parseInt(temp[4]), 0L, Integer.parseInt(temp[0]), Long.parseLong(temp[5]), System.currentTimeMillis(), temp[6]);
                        streams.add(tempstream);
                        for (int i = 0; i < streamers.size(); i++)
                            if (streamers.get(i).getId() == Integer.parseInt(temp[0])) {
                                tempstream.setStreamer(streamers.get(i).getName());
                                streamers.get(i).addStream(tempstream);
                                break;
                            }
                        break;
                    case "LIST":
                        ListStreamer listStreamer = new ListStreamer();
                        ListUser listUser = new ListUser();

                        /* ---Listeaza stream-urile unui streamer--- */
                        for (int i = 0; i < streamers.size(); i++)
                            if (streamers.get(i).getId() == Integer.parseInt(temp[0])) {
                                listStreamer.list(streamers.get(i), null);
                                break;
                            }

                        /* ---Listeaza istoria de ascultare a utilizatorului--- */
                        for (int i = 0; i < users.size(); i++)
                            if (users.get(i).getId() == Integer.parseInt(temp[0])) {
                                listUser.list(null, users.get(i));
                                break;
                            }
                        break;

                    /* ---Sterge Stream--- */
                    case "DELETE":
                        for (int i = 0; i < streamers.size(); i++)
                            if (streamers.get(i).getId() == Integer.parseInt(temp[0])) {
                                streamers.get(i).deleteStream(Integer.parseInt(temp[2]));
                                break;
                            }
                        for (int i = 0; i < users.size(); i++)
                            users.get(i).deleteStream(Integer.parseInt(temp[2]));
                        break;

                    /* ---Asculta un stream--- */
                    case "LISTEN":
                        // cautam user-ul dat
                        User tempuser = new User();
                        for (int i = 0; i < users.size(); i++)
                            if (users.get(i).getId() == Integer.parseInt(temp[0].replace("\"", ""))) {
                                tempuser = users.get(i);
                                break;
                            }
                        // verificam daca stream-ul a fost deja ascultat si crestem numarul de ascultari
                        boolean listened = tempuser.listen(Integer.parseInt(temp[2].replace("\"", "")));
                        // daca nu a mai fost ascultat pana acum, adaugam stream-ul in lista user-ului
                        if (!listened) {
                            for (int i = 0; i < streams.size(); i++)
                                if (streams.get(i).getId() == Integer.parseInt(temp[2].replace("\"", ""))) {
                                    tempuser.addStream(streams.get(i));
                                    streams.get(i).setNoOfStreams(streams.get(i).getNoOfStreams() + 1);
                                    break;
                                }
                        }
                        break;

                    /* ---Recomanda 5 stream-uri dupa preferinte--- */
                    case "RECOMMEND":
                        // cautam user-ul dat
                        user = new User();
                        for (int i = 0; i < users.size(); i++)
                            if (users.get(i).getId() == Integer.parseInt(temp[0].replace("\"", ""))) {
                                user = users.get(i);
                                break;
                            }
                        ArrayList<Stream> recommended = new ArrayList<>();
                        for (int i = 0; i < streamers.size(); i++) {
                            // cautam streamer ascultat de user
                            for (int j = 0; j < user.getStreams().size(); j++)
                                if (streamers.get(i).getId() == user.getStreams().get(j).getStreamerId()) {
                                    // streamer-ul este ascultat de user: cautam ceva neascultat
                                    for (int k = 0; k < streamers.get(i).getStreams().size(); k++)
                                        if (!user.getStreams().contains(streamers.get(i).getStreams().get(k))) {
                                            // stream neascultat de la streamer ascultat: verificare tip
                                            switch (temp[2].replace("\"", "")) {

                                                case "SONG":
                                                    if (streamers.get(i).getStreams().get(k) instanceof Song)
                                                        if (recommended.size() < 5)
                                                            recommended.add(streamers.get(i).getStreams().get(k));
                                                        else {
                                                            Long minim = recommended.get(0).getNoOfStreams();
                                                            int indiceMinim = 0;
                                                            for (int m = 1; m < 5; m++)
                                                                if (recommended.get(m).getNoOfStreams() < minim) {
                                                                    minim = recommended.get(m).getNoOfStreams();
                                                                    indiceMinim = m;
                                                                }
                                                            Stream tempStream = streamers.get(i).getStreams().get(k);
                                                            recommended.set(indiceMinim, tempStream);
                                                        }
                                                    break;

                                                case "PODCAST":
                                                    if (streamers.get(i).getStreams().get(k) instanceof Podcast)
                                                        if (recommended.size() < 5)
                                                            recommended.add(streamers.get(i).getStreams().get(k));
                                                        else {
                                                            Long minim = recommended.get(0).getNoOfStreams();
                                                            int indiceMinim = 0;
                                                            for (int m = 1; m < 5; m++)
                                                                if (recommended.get(m).getNoOfStreams() < minim) {
                                                                    minim = recommended.get(m).getNoOfStreams();
                                                                    indiceMinim = m;
                                                                }
                                                            Stream tempStream = streamers.get(i).getStreams().get(k);
                                                            recommended.set(indiceMinim, tempStream);
                                                        }
                                                    break;

                                                case "AUDIOBOOK":
                                                    if (streamers.get(i).getStreams().get(k) instanceof Audiobook)
                                                        if (recommended.size() < 5)
                                                            recommended.add(streamers.get(i).getStreams().get(k));
                                                        else {
                                                            Long minim = recommended.get(0).getNoOfStreams();
                                                            int indiceMinim = 0;
                                                            for (int m = 1; m < 5; m++)
                                                                if (recommended.get(m).getNoOfStreams() < minim) {
                                                                    minim = recommended.get(m).getNoOfStreams();
                                                                    indiceMinim = m;
                                                                }
                                                            Stream tempStream = streamers.get(i).getStreams().get(k);
                                                            recommended.set(indiceMinim, tempStream);
                                                        }
                                                    break;
                                            }
                                        }
                                    break;
                                }
                        }
                        output = "[";
                        for (int j = 0; j < recommended.size(); j++) {
                            output += user.json(recommended.get(j));
                            if (j != recommended.size() - 1) output += ",";
                        }
                        output += "]";
                        System.out.println(output);
                        break;

                    /* ---Recomanda 3 stream-uri surpriza--- */
                    case "SURPRISE":
                        // cautam user-ul dat
                        user = new User();
                        for (int i = 0; i < users.size(); i++)
                            if (users.get(i).getId() == Integer.parseInt(temp[0].replace("\"", ""))) {
                                user = users.get(i);
                                break;
                            }
                        ArrayList<Stream> surprise = new ArrayList<>();
                        for (int i = 0; i < streamers.size(); i++) {
                            // cautam streamer care nu a fost ascultat de user
                            boolean ok = true;
                            for (int j = 0; j < user.getStreams().size(); j++)
                                // streamer-ul a fost ascultat de user => skip
                                if (streamers.get(i).getId() == user.getStreams().get(j).getStreamerId()) {
                                    ok = false;
                                    break;
                                }
                            // streamer-ul nu a fost ascultat de user
                            if (ok) {
                                for (int k = 0; k < streamers.get(i).getStreams().size(); k++)
                                    // verificare tip si cautam ce e mai recent
                                    switch (temp[2].replace("\"", "")) {

                                        case "SONG":
                                            if (streamers.get(i).getStreams().get(k) instanceof Song)
                                                if (surprise.size() < 3)
                                                    surprise.add(streamers.get(i).getStreams().get(k));
                                                else {
                                                    Long day = 60 * 60 * 24 * 1L;
                                                    Long minim = surprise.get(0).getDateAdded() / day;
                                                    int indiceMinim = 0;
                                                    for (int m = 1; m < 3; m++)
                                                        if (surprise.get(m).getDateAdded() / day < minim) {
                                                            minim = surprise.get(m).getDateAdded() / day;
                                                            indiceMinim = m;
                                                        }
                                                    if (minim == streamers.get(i).getStreams().get(k).getDateAdded() / day) {
                                                        if (streamers.get(i).getStreams().get(k).getNoOfStreams() > surprise.get(indiceMinim).getNoOfStreams())
                                                            surprise.set(indiceMinim, streamers.get(i).getStreams().get(k));
                                                    } else
                                                        surprise.set(indiceMinim, streamers.get(i).getStreams().get(k));
                                                }
                                            break;

                                        case "PODCAST":
                                            if (streamers.get(i).getStreams().get(k) instanceof Podcast)
                                                if (surprise.size() < 3)
                                                    surprise.add(streamers.get(i).getStreams().get(k));
                                                else {
                                                    Long day = 60 * 60 * 24 * 1L;
                                                    Long minim = surprise.get(0).getDateAdded() / day;
                                                    int indiceMinim = 0;
                                                    for (int m = 1; m < 3; m++)
                                                        if (surprise.get(m).getDateAdded() / day < minim) {
                                                            minim = surprise.get(m).getDateAdded() / day;
                                                            indiceMinim = m;
                                                        }
                                                    if (minim == streamers.get(i).getStreams().get(k).getDateAdded() / day) {
                                                        if (streamers.get(i).getStreams().get(k).getNoOfStreams() > surprise.get(indiceMinim).getNoOfStreams())
                                                            surprise.set(indiceMinim, streamers.get(i).getStreams().get(k));
                                                    } else
                                                        surprise.set(indiceMinim, streamers.get(i).getStreams().get(k));
                                                }
                                            break;

                                        case "AUDIOBOOK":
                                            if (streamers.get(i).getStreams().get(k) instanceof Audiobook)
                                                if (surprise.size() < 3)
                                                    surprise.add(streamers.get(i).getStreams().get(k));
                                                else {
                                                    Long day = 60 * 60 * 24 * 1L;
                                                    Long minim = surprise.get(0).getDateAdded() / day;
                                                    int indiceMinim = 0;
                                                    for (int m = 1; m < 3; m++)
                                                        if (surprise.get(m).getDateAdded() / day < minim) {
                                                            minim = surprise.get(m).getDateAdded() / day;
                                                            indiceMinim = m;
                                                        }
                                                    if (minim == streamers.get(i).getStreams().get(k).getDateAdded() / day) {
                                                        if (streamers.get(i).getStreams().get(k).getNoOfStreams() > surprise.get(indiceMinim).getNoOfStreams())
                                                            surprise.set(indiceMinim, streamers.get(i).getStreams().get(k));
                                                    } else
                                                        surprise.set(indiceMinim, streamers.get(i).getStreams().get(k));
                                                }
                                            break;
                                    }
                            }
                        }
                        Long day = 60 * 60 * 24 * 1L;
                        output = "[";
                        for (int j = 0; j < surprise.size(); j++)
                            for (int k = j + 1; k < surprise.size(); k++)
                                if (surprise.get(k).getDateAdded() / day > surprise.get(j).getDateAdded() / day || (surprise.get(k).getDateAdded() / day == surprise.get(j).getDateAdded() / day && surprise.get(k).getNoOfStreams() > surprise.get(j).getNoOfStreams())) {
                                    Stream t = surprise.get(k);
                                    surprise.set(k, surprise.get(j));
                                    surprise.set(j, t);
                                }
                        for (int j = 0; j < surprise.size(); j++) {
                            output += user.json(surprise.get(j));
                            if (j != surprise.size() - 1) output += ",";
                        }
                        output += "]";
                        System.out.println(output);
                        break;
                }
                line = buffer.readLine();
            }
            buffer.close();
            input.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
