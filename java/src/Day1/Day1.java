package Day1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {

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
        var max_calories = 0;
        var current_elf_calories = 0;
        for (var calories : input) {
            if (calories.strip().length() == 0) {
                if (current_elf_calories > max_calories) {
                    max_calories = current_elf_calories;
                }
                current_elf_calories = 0;
                continue;
            }
            current_elf_calories += Integer.parseInt(calories.strip());
        }
        if (current_elf_calories > max_calories) {
            max_calories = current_elf_calories;
        }
        return max_calories;
    }

    public static Integer part2(final List<String> input) {
        var max_calories = new ArrayList<Integer>();
        var current_elf_calories = 0;
        for (var calories : input) {
            if (calories.strip().length() == 0) {
                max_calories.add(current_elf_calories);
                current_elf_calories = 0;
                continue;
            }
            current_elf_calories += Integer.parseInt(calories.strip());
        }
        max_calories.add(current_elf_calories);
        return max_calories.stream().sorted(Comparator.reverseOrder()).limit(3).reduce(0, Integer::sum);
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/src/day1/input.txt");
        System.out.println(part1(fileContent)); // 69528
        System.out.println(part2(fileContent)); // 206152
    }
}
