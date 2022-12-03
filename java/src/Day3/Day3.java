package Day3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3 {

    private static final HashMap<String, Integer> priorityMapping = new HashMap<>();

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (final IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static Integer part1(final List<String> input) {
        return input.stream().map(ruckSacks -> {
            List<Character> characters = ruckSacks.chars().mapToObj(var -> (char) var).toList();
            Set<Character> intersection = new java.util.HashSet<>(Set.copyOf(characters.subList(0, characters.size() / 2)));
            intersection.retainAll(Set.copyOf(characters.subList(characters.size() / 2, characters.size())));
            return intersection.stream().map(character -> priorityMapping.get(String.valueOf(character))).findFirst().get();
        }).reduce(0, Integer::sum);
    }

    public static Integer part2(final List<String> input) {
        var total_priority = 0;
        var index = 0;
        do {
            Set<Character> elf1 = input.get(index).chars().mapToObj(var -> (char) var).collect(Collectors.toSet());
            Set<Character> elf2 = input.get(index+1).chars().mapToObj(var -> (char) var).collect(Collectors.toSet());
            Set<Character> elf3 = input.get(index+2).chars().mapToObj(var -> (char) var).collect(Collectors.toSet());
            elf1.retainAll(elf2);
            elf1.retainAll(elf3);
            total_priority += elf1.stream().map(character -> priorityMapping.get(String.valueOf(character))).findFirst().get();
            index += 3;
        } while (index < input.size());
        return total_priority;
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/src/day3/input.txt");
        var index = 1;
        List<Character> lowerCaseCharacters = IntStream.rangeClosed('a', 'z').mapToObj(var -> (char) var).toList();
        for (var coll : lowerCaseCharacters) {
            priorityMapping.put(String.valueOf(coll), index);
            index++;
        }
        List<Character> upperCaseCharacters = IntStream.rangeClosed('A', 'Z').mapToObj(var -> (char) var).toList();
        for (var coll : upperCaseCharacters) {
            priorityMapping.put(String.valueOf(coll), index);
            index++;
        }

        System.out.println(part1(fileContent)); // 8039
        System.out.println(part2(fileContent)); // 2510
    }
}

