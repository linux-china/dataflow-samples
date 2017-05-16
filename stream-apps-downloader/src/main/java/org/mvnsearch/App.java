package org.mvnsearch;

import org.apache.commons.io.IOUtils;

import java.io.StringReader;
import java.net.URI;
import java.util.List;

/**
 * pint all task&stream cloud app
 *
 * @author linux_china
 */
public class App {
    public static void main(String[] args) throws Exception {
        String[] importUris = new String[]
                {"http://bit.ly/Belmont-GA-task-applications-maven",
                        "http://bit.ly/Bacon-RELEASE-stream-applications-kafka-10-maven"};
        for (String importUri : importUris) {
            printImportUriDependency(importUri);
        }
    }

    private static void printImportUriDependency(String importUri) throws Exception {
        String content = IOUtils.toString(new URI(importUri), "utf-8");
        List<String> tasks = IOUtils.readLines(new StringReader(content));
        for (String task : tasks) {
            printMavenDependency(task);
        }
    }

    private static void printMavenDependency(String line) {
        String gav = line.substring(line.indexOf("maven://") + 8);
        String[] parts = gav.split(":");
        if (!parts[2].equals("jar")) {
            System.out.println("<dependency>\n" +
                    "            <groupId>" + parts[0] + "</groupId>\n" +
                    "            <artifactId>" + parts[1] + "</artifactId>\n" +
                    "            <version>" + parts[2] + "</version>\n" +
                    "        </dependency>");
        }
    }
}
