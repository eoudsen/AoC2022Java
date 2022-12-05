package Day5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day5 {

    private static List<Stack<Character>> stacks = new ArrayList<>();

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (final IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    private static void setupStacks(final List<String> stacksInput) {
        for (var line : stacksInput) {
            List<Character> characters = line.chars().mapToObj(e -> (char) e).toList();
            if (!characters.contains('[')) {
                break;
            }
            var maxNumberOfBlocksToAdd = characters.size() / 4 + 1;
            var i = 0;
            while (i < maxNumberOfBlocksToAdd) {
                var value = characters.get(i * 4 + 1);
                if (!value.equals(' ')) {
                    while (stacks.size() < maxNumberOfBlocksToAdd) {
                        stacks.add(new Stack<>());
                    }
                    stacks.get(i).push(value);
                }
                i++;
            }
        }
        final List<Stack<Character>> tempList = new ArrayList<>();
        for (var stack : stacks) {
            var tempStack = new Stack<Character>();
            while (!stack.isEmpty()) {
                tempStack.push(stack.pop());
            }
            tempList.add(tempStack);
        }
        stacks = tempList;
    }

    private static String getResult() {
        final var stringBuilder = new StringBuilder();
        for (var stack : stacks) {
            stringBuilder.append(stack.pop());
        }
        return stringBuilder.toString();
    }


    public static String part1(final List<String> input) {
        setupStacks(input.subList(0, input.indexOf("")));
        for (var line : input.subList(10, input.size())) {
            var numToMove = Integer.parseInt(line.split(" ")[1]);
            var moveFrom = Integer.parseInt(line.split(" ")[3]);
            var moveTo = Integer.parseInt(line.split(" ")[5]);
            var i = 0;
            while (i < numToMove) {
                stacks.get(moveTo - 1).push(stacks.get(moveFrom - 1).pop());
                i++;
            }
        }
        return getResult();

    }

    public static String part2(final List<String> input) {
        setupStacks(input.subList(0, input.indexOf("")));
        for (var line : input.subList(10, input.size())) {
            var numToMove = Integer.parseInt(line.split(" ")[1]);
            var moveFrom = Integer.parseInt(line.split(" ")[3]);
            var moveTo = Integer.parseInt(line.split(" ")[5]);
            var i = 0;
            var tempStack = new Stack<Character>();
            while (i < numToMove) {
                tempStack.push(stacks.get(moveFrom - 1).pop());
                i++;
            }
            while (!tempStack.empty()) {
                stacks.get(moveTo - 1).push(tempStack.pop());
            }
        }
        return getResult();
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/src/day5/input.txt");
        System.out.println(part1(fileContent)); // HBTMTBSDC
        stacks = new ArrayList<>();
        System.out.println(part2(fileContent)); // PQTJRSHWS
    }
}

