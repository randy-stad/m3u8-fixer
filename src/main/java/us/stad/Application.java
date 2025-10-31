package us.stad;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "m3u8-fixer", mixinStandardHelpOptions = true, version = "1.0", description = "Fixes my Uzzu m3u8 file for use with Channels.")
class Application implements Callable<Integer> {

    @Parameters(index = "0", description = "The input m3u8 URL to fix.")
    private URL input;

    @Option(names = { "-o", "--output" }, description = "Output file, defaults to stdout.")
    private File output;

    @Override
    public Integer call() throws Exception {
        PrintStream writer;
        if (output != null) {
            writer = new PrintStream(output);
        } else {
            writer = System.out;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input.openStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                // we only process EXTINF lines, the rest are passed through
                if (line.startsWith("#EXTINF")) {
                    EXTINF extinf = new EXTINF(line);
                    // do the magic here for Uzzu, will add others later
                    extinf.addChannelsUzzuTags();
                    writer.println(extinf.toString());
                } else {
                    writer.println(line);
                }
            }
        }

        if (output != null) {
            writer.close();
        }

        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }
}
