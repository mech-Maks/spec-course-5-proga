package org.speckurs.parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileParser {
    public static Map<String, Map<String, List<String>>> parse(String fileLoc) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileLoc), StandardCharsets.UTF_8);

        Map<String, Map<String, List<String>>> programStructure = new HashMap<>();

        for (String line : lines) {
            if (line.isBlank() || line.charAt(0) == ';') {
                continue;
            }

            if (line.contains("algo:")) {
                programStructure.put(line, null);
                continue;
            }

            List<String> command = Arrays.asList(line.split(" "))
                    .stream()
                    .map(String::trim)
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.toList());

            programStructure.computeIfPresent(command.get(0), (k,v) -> {
                v.put(command.get(1), command.subList(2, 5));
                return v;
            });
            programStructure.computeIfAbsent(command.get(0), k -> {
                Map<String, List<String>> res = new HashMap<>();
                res.put(command.get(1), command.subList(2, 5));
                return res;
            });
        }

        return programStructure;
    }
}
