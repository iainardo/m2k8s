package com.manning.liveproject.m2k8s.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ResourceReaderUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceReaderUtil.class);

    //CHECKSTYLE.OFF: ConstantName: Permit contstant with pattern which doesn't match alphanumberic characters
    private static final Path resourceDirectory = Paths.get("src", "test", "resources");
    //CHECKSTYLE.ON

    private ResourceReaderUtil() {
        // default ctor - forced to add to remove Sonar smell
    }

    public static String getFileContent(String filename) {
        File file = new File(resourceDirectory.toFile().getAbsolutePath() + File.separator + filename);
        return readCompleteFileContents(file.getAbsolutePath());
    }

    public static File getFile(String filename) {
        File file = new File(resourceDirectory.toFile().getAbsolutePath() + File.separator + filename);
        return file;
    }

    //Read file content into string with - Files.lines(Path path, Charset cs)
    private static String readCompleteFileContents(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s.trim()));
        } catch (IOException e) {
            LOGGER.error(String.format("Error attempting to read file resource %s", filePath), e);
        }

        return contentBuilder.toString();
    }

}
