package Day6;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (final IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static List<List<Character>> slidingWindow(final List<Character> inputList, final Integer windowSize) {
        return IntStream.range(0, inputList.size() - windowSize + 1)
                .mapToObj(start -> inputList.subList(start, start + windowSize))
                .toList();
    }

    public static Integer part1(final List<String> input) {
        List<Character> inputList = input.get(0).chars().mapToObj(var -> (char) var).toList();
        List<List<Character>> windows = slidingWindow(inputList, 4);
        List<Integer> setSizes = windows.stream().map(HashSet::new).map(Set::size).toList();
        return setSizes.indexOf(4) + 4;
    }

    public static Integer part2(final List<String> input) {
        List<Character> inputList = input.get(0).chars().mapToObj(var -> (char) var).toList();
        List<List<Character>> windows = slidingWindow(inputList, 14);
        List<Integer> setSizes = windows.stream().map(HashSet::new).map(Set::size).toList();
        return setSizes.indexOf(14) + 14;
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/src/day6/input.txt");
        System.out.println(part1(fileContent)); // 1816
        System.out.println(part2(fileContent)); // 2625
    }
}
