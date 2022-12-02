package Day2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (final IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    private static final Integer[][] part1Table = {
            {4, 8, 3},
            {1, 5, 9},
            {7, 2, 6}
    };

    private static final Integer[][] part2Table = {
            {3, 4, 8},
            {1, 5, 9},
            {2, 6, 7}
    };

    private static final HashMap<String, Integer> letterToIndexMapping = new HashMap<>() {{
        put("A", 0);
        put("B", 1);
        put("C", 2);
        put("X", 0);
        put("Y", 1);
        put("Z", 2);
    }};

    private static Integer part1StaticMapper(final String roundInput) {
        final var choices = roundInput.split(" ");
        return part1Table[letterToIndexMapping.get(choices[0])][letterToIndexMapping.get(choices[1])];
    }

    public static Integer part1(final List<String> input) {
        return input.stream().map(Day2::part1StaticMapper).reduce(0, Integer::sum);
    }

    private static Integer part2StaticMapper(final String roundInput) {
        final var choices = roundInput.split(" ");
        return part2Table[letterToIndexMapping.get(choices[0])][letterToIndexMapping.get(choices[1])];
    }

    public static Integer part2(final List<String> input) {
        return input.stream().map(Day2::part2StaticMapper).reduce(0, Integer::sum);
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/src/day2/input.txt");
        System.out.println(part1(fileContent)); // 13446
        System.out.println(part2(fileContent)); // 13509
    }
}

