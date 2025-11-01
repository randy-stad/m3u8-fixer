package us.stad;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Parse an EXTINF line from Uzzu. This is ugly but it gets the job done. */

public class EXTINF {

    private String title;
    private Integer duration;
    private Map<String, String> tags = new HashMap<>();

    public EXTINF(String line) throws Exception {
        super();
        this.parse(line);
    }

    private void parse(String line) throws Exception {

        if (!line.startsWith("#EXTINF")) {
            throw new Exception("invalid #EXTINF format, not an #EXTINF entry");
        }

        // first split on the comma to separate the track title, this will break if
        // there are commas in any tags

        String[] comma = line.split(",");
        if (comma.length != 2) {
            throw new Exception("invalid #EXTINF format, no comma found");
        }
        this.title = comma[1];

        // split on the first space after the #EXTINF

        String[] parts = comma[0].split(" ", 2);
        if (parts[0].startsWith("#EXTINF")) {
            String[] value = parts[0].split(":");
            duration = Integer.valueOf(value[1]);
        }

        // split the tags using a regex

        Pattern regex = Pattern.compile("(.*?)=\"(.*?)\"");
        Matcher matcher = regex.matcher(parts[1]);
        while (matcher.find()) {
            tags.put(matcher.group(1).trim(), matcher.group(2));
        }
    }

    /**
     * This assumes we are coming from an Uzzu feed and adding Channels specific tags.
     */
    public void addChannelsUzzuTags() {
        if (tags.containsKey("group-title")) {
            final String title = tags.get("group-title");
            final String name = (tags.containsKey("tvg-name") ? tags.get("tvg-name") : "");
            addTag("tvc-guide-genres", "Uzzu");
            if (title.contains("NHL") || name.contains("NHL") || name.contains("Altitude")) {
                addTag("tvc-guide-genres", "Hockey");
                addTag("tvc-guide-tags", "Live");
            }
            if (title.contains("NFL") || name.contains("Red Zone"))  {
                addTag("tvc-guide-genres", "Football");
                addTag("tvc-guide-tags", "Live");
            }
            if (title.contains("NBA")) {
                addTag("tvc-guide-genres", "Basketball");
                addTag("tvc-guide-tags", "Live");
            }
            if (title.contains("MLB") || name.contains("MLB")) {
                addTag("tvc-guide-genres", "Baseball");
                addTag("tvc-guide-tags", "Live");
            }
            if (title.contains("Live")) {
                addTag("tvc-guide-genres", "Live");
                addTag("tvc-guide-tags", "Live");
            }
        }
    }

    private void addTag(String key, String value) {
        if (tags.containsKey(key)) {
            String newValue = tags.get(key);
            newValue += "," + value;
            tags.put(key, newValue);
        } else {
            tags.put(key, value);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("#EXTINF:");
        builder.append(this.duration.toString());
        for (String key : this.tags.keySet()) {
            builder.append(" ");
            builder.append(key);
            builder.append("=\"");
            builder.append(this.tags.get(key));
            builder.append("\"");
        }
        builder.append(",");
        builder.append(this.title);
        return builder.toString();
    }

}
