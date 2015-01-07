package org.babbelbox.opus;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class OpusInfoParser {

    public OpusInfoParser() {
        // TODO Auto-generated constructor stub
    }

    private static String[] user_comment_fields = { "artist", "title", "album", "date",
            "copyright", "license", "organization" };

    private static String[][] stream_info_fields = {
            { "Playback gain", "Playback gain: (\\d+ \\w+)" }, { "Pre-skip", "Pre-skip: (\\d+)" },
            { "Channels", "Channels: (\\d+)" },
            { "Original sample rate", "Original sample rate: (\\d\\w)" } };

    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO Auto-generated method stub

        Process p =
                Runtime.getRuntime()
                        .exec("C:\\Users\\fox0h3j\\Downloads\\opus-tools-0.1.9-win32\\opus-tools-0.1.9-win32\\opusinfo.exe d:\\ehren-paper_lights-64.opus.oga");
        p.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        Map<String, String> metaData = new HashMap<>();

        String line = "";
        String streamName = null;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            System.out.println(line);
            /**
             * Processing Metadata...
             */
            for (String user_comment_field : OpusInfoParser.user_comment_fields) {
                if (line.startsWith(user_comment_field)) {
                    String[] tmp = line.split("=");
                    if (tmp.length >= 2) {
                        metaData.put(tmp[0].trim(), tmp[1].trim());
                    }
                }
            }
            /**
             * Set Stream Name
             */
            if (line.startsWith("Opus stream")) {
                streamName = line;
            }

        }
        System.out.println("KEKS");

    }

}
