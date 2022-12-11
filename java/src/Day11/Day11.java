package Day11;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (final IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static void parseInput(final List<String> input,
                                  final List<ArrayList<Long>> monkeyItems,
                                  final List<Integer> divisibleBy,
                                  final List<String> monkeyOperation,
                                  final List<Integer> trueMonkey,
                                  final List<Integer> falseMonkey) {
        var currentMonkeyIndex = 0;
        for (var line : input) {
            if (line.contains("Monkey")) {
                currentMonkeyIndex = Integer.parseInt(line.strip().split(" ")[1].substring(0, line.strip().split(" ")[1].length() - 1));
                monkeyItems.add(new ArrayList<>());
            }
            else if (line.contains("Starting")) {
                var split = line.strip().split(" ");
                var values = Arrays.copyOfRange(split, 2, split.length);
                for (var value : values) {
                    if (value.contains(",")) {
                        monkeyItems.get(currentMonkeyIndex).add(Long.valueOf(value.substring(0, value.length() - 1)));
                    }
                    else {
                        monkeyItems.get(currentMonkeyIndex).add(Long.valueOf(value));
                    }
                }
            }
            else if (line.contains("Operation")) {
                var equation = line.strip().split("=")[1].strip();
                monkeyOperation.add(equation);
            }
            else if (line.contains("Test")) {
                divisibleBy.add(Integer.valueOf(line.strip().split(" ")[line.strip().split(" ").length - 1]));
            }
            else if (line.contains("If true")) {
                trueMonkey.add(Integer.valueOf(line.strip().split(" ")[line.strip().split(" ").length - 1]));
            }
            else if (line.contains("If false")) {
                falseMonkey.add(Integer.valueOf(line.strip().split(" ")[line.strip().split(" ").length - 1]));
            }
        }
    }


    public static Long executeOperation(final String operation, final Long value) {
        var replaced = operation.strip().replace("old", String.valueOf(value));
        if (replaced.contains("+")) {
            return Long.parseLong(replaced.split(" ")[0]) + Long.parseLong(replaced.split(" ")[2]);
        }
        return Long.parseLong(replaced.split(" ")[0]) * Long.parseLong(replaced.split(" ")[2]);
    }

    public static Long part1(final List<String> input) {
        var monkeyItems = new ArrayList<ArrayList<Long>>();
        var divisibleBy = new ArrayList<Integer>();
        var monkeyOperation = new ArrayList<String>();
        var trueMonkey = new ArrayList<Integer>();
        var falseMonkey = new ArrayList<Integer>();
        parseInput(input, monkeyItems, divisibleBy, monkeyOperation, trueMonkey, falseMonkey);
        var inspected = new ArrayList<Long>() {{
            add(0L);
            add(0L);
            add(0L);
            add(0L);
            add(0L);
            add(0L);
            add(0L);
            add(0L);
        }};
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < monkeyItems.size(); j++) {
                for (var item : monkeyItems.get(j)) {
                    inspected.set(j, inspected.get(j) + 1L);
                    var newValue = executeOperation(monkeyOperation.get(j), item);
                    newValue = (long) Math.floor(newValue / 3.0);
                    var monkeyToPlace = newValue % divisibleBy.get(j) == 0 ? trueMonkey.get(j) : falseMonkey.get(j);
                    monkeyItems.get(monkeyToPlace).add(newValue);
                }
                monkeyItems.set(j, new ArrayList<>());
            }
        }
        List<Long> sortedInspected = inspected.stream().sorted().toList();
        return sortedInspected.get(sortedInspected.size() - 1) * sortedInspected.get(sortedInspected.size() - 2);
    }

    public static Long part2(final List<String> input) {
        var monkeyItems = new ArrayList<ArrayList<Long>>();
        var divisibleBy = new ArrayList<Integer>();
        var monkeyOperation = new ArrayList<String>();
        var trueMonkey = new ArrayList<Integer>();
        var falseMonkey = new ArrayList<Integer>();
        parseInput(input, monkeyItems, divisibleBy, monkeyOperation, trueMonkey, falseMonkey);
        var inspected = new ArrayList<Long>() {{
            add(0L);
            add(0L);
            add(0L);
            add(0L);
            add(0L);
            add(0L);
            add(0L);
            add(0L);
        }};
        var totalModulo = 1;
        for (var x : divisibleBy) {
            totalModulo *= x;
        }
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < monkeyItems.size(); j++) {
                for (var item : monkeyItems.get(j)) {
                    inspected.set(j, inspected.get(j) + 1L);
                    var newValue = executeOperation(monkeyOperation.get(j), item);
                    var monkeyToPlace = newValue % divisibleBy.get(j) == 0 ? trueMonkey.get(j) : falseMonkey.get(j);
                    monkeyItems.get(monkeyToPlace).add(newValue % totalModulo);
                }
                monkeyItems.set(j, new ArrayList<>());
            }
        }
        List<Long> sortedInspected = inspected.stream().sorted().toList();
        return sortedInspected.get(sortedInspected.size() - 1) * sortedInspected.get(sortedInspected.size() - 2);
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/src/day11/input.txt");
        System.out.println(part1(fileContent)); // 151312
        System.out.println(part2(fileContent)); // 51382025916
    }
}

