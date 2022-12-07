package Day7;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {

    public static final Integer TOTAL_DISK_SIZE = 70000000;
    public static final Integer FREE_SPACE_NEEDED = 30000000;

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (final IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    public static void getPart1Solution(Node currentNode, Result result) {
        if (currentNode.getSize() < 100000) {
            result.addSize(currentNode.getSize());
        }
        for (var childNode : currentNode.getChildren()) {
            getPart1Solution(childNode, result);
        }
    }

    public static List<Integer> getFoldersLargeEnoughForDeletion(Node currentNode, Integer toClear, List<Integer> folderList) {
        if (currentNode.getSize() >= toClear) {
            folderList.add(currentNode.getSize());
        }
        for (var childNode : currentNode.getChildren()) {
            getFoldersLargeEnoughForDeletion(childNode, toClear, folderList);
        }
        return folderList;
    }

    public static Integer part1(final List<String> input) {
        Node rootNode = null;
        Node currentNode = null;
        boolean rootCreated  = false;
        for(var line : input) {
            if (!rootCreated) {
                rootNode = new Node("/", null);
                currentNode = rootNode;
                rootCreated = true;
                continue;
            }
            if (line.startsWith("$ ls")) {
                continue;
            }
            if (line.startsWith("$ cd ..")) {
                var temp_size = currentNode.getSize();
                currentNode = currentNode.getParent();
                currentNode.setSize(currentNode.getSize() + temp_size);
                continue;
            }
            if (line.startsWith("$ cd ")) {
                currentNode = currentNode.getChildNodeByName(line.split(" ")[2]);
                continue;
            }
            if (line.startsWith("dir ")) {
                currentNode.addDirectory(line);
                continue;
            }
            currentNode.addFile(line);
        }
        while (!currentNode.getName().equals("/")) {
            var tempSize = currentNode.getSize();
            currentNode = currentNode.getParent();
            currentNode.setSize(currentNode.getSize() + tempSize);
        }
        var result = new Result(0);
        getPart1Solution(rootNode, result);
        return result.getSize();
    }

    public static Integer part2(final List<String> input) {
        Node rootNode = null;
        Node currentNode = null;
        boolean rootCreated  = false;
        for(var line : input) {
            if (!rootCreated) {
                rootNode = new Node("/", null);
                currentNode = rootNode;
                rootCreated = true;
                continue;
            }
            if (line.startsWith("$ ls")) {
                continue;
            }
            if (line.startsWith("$ cd ..")) {
                var temp_size = currentNode.getSize();
                currentNode = currentNode.getParent();
                currentNode.setSize(currentNode.getSize() + temp_size);
                continue;
            }
            if (line.startsWith("$ cd ")) {
                currentNode = currentNode.getChildNodeByName(line.split(" ")[2]);
                continue;
            }
            if (line.startsWith("dir ")) {
                currentNode.addDirectory(line);
                continue;
            }
            currentNode.addFile(line);
        }
        while (!currentNode.getName().equals("/")) {
            var tempSize = currentNode.getSize();
            currentNode = currentNode.getParent();
            currentNode.setSize(currentNode.getSize() + tempSize);
        }
        Integer currentFreeSpace = TOTAL_DISK_SIZE - rootNode.getSize();
        Integer toClear = FREE_SPACE_NEEDED - currentFreeSpace;
        List<Integer> folder_list = getFoldersLargeEnoughForDeletion(rootNode, toClear, new ArrayList<>());
        return folder_list.stream().sorted().toList().get(0);
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/src/day7/input.txt");
        System.out.println(part1(fileContent)); // 1086293
        System.out.println(part2(fileContent)); // 366028
    }
}

