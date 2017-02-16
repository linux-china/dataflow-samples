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
                {"http://repo.spring.io/libs-release-local/org/springframework/cloud/task/app/spring-cloud-task-app-descriptor/Addison.RELEASE/spring-cloud-task-app-descriptor-Addison.RELEASE.task-apps-maven",
                        "http://repo.spring.io/libs-release-local/org/springframework/cloud/stream/app/spring-cloud-stream-app-descriptor/Avogadro.SR1/spring-cloud-stream-app-descriptor-Avogadro.SR1.stream-apps-kafka-10-maven"};
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
        System.out.println("<dependency>\n" +
                "            <groupId>" + parts[0] + "</groupId>\n" +
                "            <artifactId>" + parts[1] + "</artifactId>\n" +
                "            <version>" + parts[2] + "</version>\n" +
                "        </dependency>");
    }
}
