package Day8;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (final IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static Integer part1(final List<Integer> input) {

        return 0;
    }

    public static Integer part2(final List<Integer> input) {

        return 0;
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/src/day1/input.txt");
        var intInput = fileContent.stream().map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(part1(intInput));
        System.out.println(part2(intInput));
    }
}

