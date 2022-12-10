package Day10;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (final IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    private static boolean checkSignalStrength(final int cycle) {
        if ((cycle == 20 || (cycle - 20) % 40 == 0) && cycle < 221) {
            return true;
        }
        return false;
    }

    public static Integer part1(final List<String> input) {
        var signalStrengths = new ArrayList<Integer>();
        var cycle = 0;
        var xValue = 1;
        for (var op : input) {
            cycle += 1;
            if (op.startsWith("noop")) {
            }
            if (op.startsWith("addx")) {
                var valueToAdd = op.split(" ")[1];
                var toSave = checkSignalStrength(cycle);
                if (toSave) {
                    signalStrengths.add(xValue * cycle);
                }
                cycle += 1;
                toSave = checkSignalStrength(cycle);
                if (toSave) {
                    signalStrengths.add(xValue * cycle);
                }
                xValue += Integer.parseInt(valueToAdd);
                continue;
            }
            var toSave = checkSignalStrength(cycle);
            if (toSave) {
                signalStrengths.add(xValue * cycle);
            }
        }
        return signalStrengths.stream().reduce(0, Integer::sum);
    }

    public static void draw(int cycle, int xValue) {
        cycle -= 1;
        if (cycle % 40 == 0) {
            System.out.println();
        }
        if (cycle >= 40) {
            cycle = cycle % 40;
        }
        if (xValue - 1 <= cycle && cycle <= xValue + 1) {
            System.out.print("#");
        }
        else {
            System.out.print(".");
        }
    }

    public static Integer part2(final List<String> input) {
        int cycle = 0;
        int xValue = 1;
        for (var op : input) {
            cycle += 1;
            if (op.startsWith("noop")) {
                draw(cycle, xValue);
            }
            if (op.startsWith("addx")) {
                var valueToAdd = op.split(" ")[1];
                draw(cycle, xValue);
                cycle += 1;
                draw(cycle, xValue);
                xValue += Integer.parseInt(valueToAdd);
            }
        }
        return 0;
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/src/day10/input.txt");
        System.out.println(part1(fileContent)); // 15360
        System.out.println(part2(fileContent)); // PHLHJGZA
    }
}

