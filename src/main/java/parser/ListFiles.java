/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author HoangTheSon_Computer
 */
public class ListFiles {
    String allFiles;

    public ListFiles() {
            allFiles = "";
    }

    public String listAllFiles(String path) {
            try (Stream<Path> paths = Files.walk(Paths.get(path))) {
                    paths.forEach(filePath -> {
                            if (Files.isRegularFile(filePath)) {
                                    try {
                                            allFiles = allFiles.concat(readContent(filePath));
                                            allFiles = allFiles.concat("\n");
                                    } catch (Exception e) {
                                            e.printStackTrace();
                                    }
                            }
                    });
            } catch (IOException e) {
                    e.printStackTrace();
            }
            return allFiles;
    }

    public final String readContent(Path filePath) throws IOException {
            List<String> fileList = Files.readAllLines(filePath);
            List<String> list = fileList.stream().filter(s -> !s.contains("package")).collect(Collectors.toList());
            String content = String.join("\n", list);
            return content;
    }
}
