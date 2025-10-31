package us.stad;

import org.junit.jupiter.api.Test;

public class EXTINFTest {
    
    @Test
    void testSingleParse() throws Exception {
        String line = "#EXTINF:0 tvg-type=\"movie\" tvg-id=\"Vegas @ Seattle-A\" group-title=\"NHL Games\",Vegas @ Seattle-A";
        EXTINF extinf = new EXTINF(line);
        System.out.println(line);
        System.out.println(extinf.toString());
    }
}
