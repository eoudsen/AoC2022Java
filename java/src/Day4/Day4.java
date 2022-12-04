package Day4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 {

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
        return input.stream().map(line -> {
            String[] elfes = line.split(",");
            String[] elf1 = elfes[0].split("-");
            String[] elf2 = elfes[1].split("-");
            if ((Integer.parseInt(elf1[0]) <= Integer.parseInt(elf2[0]) && Integer.parseInt(elf1[1]) >= Integer.parseInt(elf2[1])) || (Integer.parseInt(elf1[0]) >= Integer.parseInt(elf2[0]) && Integer.parseInt(elf1[1]) <= Integer.parseInt(elf2[1]))) {
                return 1;
            }
            return 0;
        }).reduce(0, Integer::sum);
    }

    public static Integer part2(final List<String> input) {
        return input.stream().map(line -> {
            String[] elfes = line.split(",");
            String[] elf1 = elfes[0].split("-");
            String[] elf2 = elfes[1].split("-");
            if ((Integer.parseInt(elf1[0]) <= Integer.parseInt(elf2[0]) && Integer.parseInt(elf1[1]) >= Integer.parseInt(elf2[1])) || (Integer.parseInt(elf1[0]) >= Integer.parseInt(elf2[0]) && Integer.parseInt(elf1[1]) <= Integer.parseInt(elf2[1]))) {
                return 1;
            }
            else if (Integer.parseInt(elf2[0]) <= Integer.parseInt(elf1[0]) && Integer.parseInt(elf1[0]) <= Integer.parseInt(elf2[1])) {
                return 1;
            }
            else if (Integer.parseInt(elf1[0]) <= Integer.parseInt(elf2[0]) && Integer.parseInt(elf2[0]) <= Integer.parseInt(elf1[1])) {
                return 1;
            }
            return 0;
        }).reduce(0, Integer::sum);
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/src/day4/input.txt");
        System.out.println(part1(fileContent)); // 571
        System.out.println(part2(fileContent)); // 917
    }
}

