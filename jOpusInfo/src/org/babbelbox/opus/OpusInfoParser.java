package org.babbelbox.opus;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpusInfoParser {

	private String executable;
    OpusInfoParser(String executable) {
    	this.executable=executable;
    }

    private static String[] user_comment_fields = { "artist", "title", "album", "date",
            "copyright", "license", "organization" };

    private static String[][] stream_info_fields = {
            { "Playback gain", ".*Playback gain: (\\d+ \\w+).*" },
            { "Pre-skip", ".*Pre-skip: (\\d+?).*" }, { "Channels", "Channels: (\\d+)" },
            { "Original sample rate", "Original sample rate: (\\d\\w)" },
            { "Packet duration", "Packet duration: (.*?)" },
            { "Page duration", "Page duration: (.*?)" },
            { "Total data length", "Total data length: (\\d+? \\w+?) .*?" },
            { "Average bitrate", "Average bitrate: (:*? [/,s]+)" },
            { "overhead", "overhead: (:*? [/,s]+)" },
            { "Playback length", "Playback length: (.*?)s" }, };

    public OpusInfo parseOpusFile(File opusFile) throws IOException, InterruptedException {
        // TODO Auto-generated method stub

    	System.out.println("Executing: "+this.executable+" \""+opusFile.getAbsoluteFile().getAbsolutePath()+"\"");
        Process p =
                Runtime.getRuntime()
                        .exec(this.executable+" "+opusFile.getAbsoluteFile().getAbsolutePath()+"");
        p.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        Map<String, String> metaData = new HashMap<>();
        Map<String, Map<String, String>> streams = new HashMap<String, Map<String, String>>();

        String line = "";
        String streamName = null;
        Map<String, String> streamInfo = null;
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
                streamInfo = new HashMap<String, String>();
                streams.put(streamName, streamInfo);
            }

            for (String[] streamInfoField : OpusInfoParser.stream_info_fields) {
                Pattern pattern = Pattern.compile(".*" + streamInfoField[1] + ".*");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    streamInfo.put(streamInfoField[0], matcher.group(1));
                    System.out.printf("Found %s Group: %s\n", streamInfoField[0], matcher.group(1));
                }
            }

        }
        System.out.println("KEKS");
        return new OpusInfo(metaData, streams);
    }

}
