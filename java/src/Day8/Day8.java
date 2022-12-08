package Day8;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
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

    public static Integer part1(final List<String> input) {
        var actualGrid = new ArrayList<List<Integer>>();
        for (var line : input) {
            actualGrid.add(
                    line.chars()
                            .mapToObj(var ->
                                    Integer.parseInt(String.valueOf(var))
                            ).toList()
            );
        }
        var countGrid = new ArrayList<List<Integer>>();
        for (int i = 0; i < actualGrid.size(); i++) {
            countGrid.add(
                    new ArrayList<>(
                            Collections.nCopies(
                                    actualGrid.get(0).size(), 0)
                    )
            );
        }
        for (int x = 0; x < actualGrid.size(); x++) {
            for (int y = 0; y < actualGrid.get(0).size(); y++) {
                var currentValue = actualGrid.get(x).get(y);
                var index = 1;
                var visible = false;
                while (true) {
                    if (y - index < 0) {
                        visible = true;
                        break;
                    }
                    if (actualGrid.get(x).get(y - index) >= currentValue) {
                        break;
                    }
                    index += 1;
                }
                index = 1;
                while (true) {
                    if (y + index > actualGrid.get(x).size() - 1) {
                        visible = true;
                        break;
                    }
                    if (actualGrid.get(x).get(y + index) >= currentValue) {
                        break;
                    }
                    index += 1;
                }
                index = 1;
                while (true) {
                    if (x - index < 0) {
                        visible = true;
                        break;
                    }
                    if (actualGrid.get(x - index).get(y) >= currentValue) {
                        break;
                    }
                    index += 1;
                }
                index = 1;
                while (true) {
                    if (x + index > actualGrid.size() - 1) {
                        visible = true;
                        break;
                    }
                    if (actualGrid.get(x + index).get(y) >= currentValue) {
                        break;
                    }
                    index += 1;
                }
                if (visible) {
                    countGrid.get(x).set(y, 1);
                }
            }
        }
        return countGrid.stream().map(list -> list.stream().reduce(0, Integer::sum)).reduce(0, Integer::sum);
    }

    public static Long part2(final List<String> input) {
        var actualGrid = new ArrayList<List<Integer>>();
        for (var line : input) {
            actualGrid.add(
                    line.chars()
                            .mapToObj(var ->
                                    Integer.parseInt(String.valueOf(var))
                            ).toList()
            );
        }
        var countGrid = new ArrayList<List<Long>>();
        for (int i = 0; i < actualGrid.size(); i++) {
            countGrid.add(
                    new ArrayList<>(
                            Collections.nCopies(
                                    actualGrid.get(0).size(), 1L)
                    )
            );
        }
        for (int x = 0; x < actualGrid.size(); x++) {
            for (int y = 0; y < actualGrid.get(0).size(); y++) {
                if (x == 0 || y == 0 || x == actualGrid.size() - 1 || y == actualGrid.get(x).size() - 1) {
                    continue;
                }
                var currentValue = actualGrid.get(x).get(y);
                var index = 1;
                while (true) {
                    if (y - index <= 0) {
                        countGrid.get(x).set(y, countGrid.get(x).get(y) * index);
                        break;
                    }
                    if (actualGrid.get(x).get(y - index) >= currentValue) {
                        countGrid.get(x).set(y, countGrid.get(x).get(y) * index);
                        break;
                    }
                    index += 1;
                }
                index = 1;
                while (true) {
                    if (y + index >= actualGrid.get(x).size() - 1) {
                        countGrid.get(x).set(y, countGrid.get(x).get(y) * index);
                        break;
                    }
                    if (actualGrid.get(x).get(y + index) >= currentValue) {
                        countGrid.get(x).set(y, countGrid.get(x).get(y) * index);
                        break;
                    }
                    index += 1;
                }
                index = 1;
                while (true) {
                    if (x - index <= 0) {
                        countGrid.get(x).set(y, countGrid.get(x).get(y) * index);
                        break;
                    }
                    if (actualGrid.get(x - index).get(y) >= currentValue) {
                        countGrid.get(x).set(y, countGrid.get(x).get(y) * index);
                        break;
                    }
                    index += 1;
                }
                index = 1;
                while (true) {
                    if (x + index >= actualGrid.size() - 1) {
                        countGrid.get(x).set(y, countGrid.get(x).get(y) * index);
                        break;
                    }
                    if (actualGrid.get(x + index).get(y) >= currentValue) {
                        countGrid.get(x).set(y, countGrid.get(x).get(y) * index);
                        break;
                    }
                    index += 1;
                }
            }
        }
        return Collections.max(countGrid.stream().map(Collections::max).toList());
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/src/day8/input.txt");
        System.out.println(part1(fileContent)); // 1763
        System.out.println(part2(fileContent)); // 671160
    }
}

