package Day9;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day9 {

    public static List<String> getFileContent(final String fileName) {
        try(var bufferedReader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8)) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (final IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
        System.exit(1);
        return null;
    }

    private static void updateHeadPosition(final String direction, final Position head) {
        switch (direction) {
            case "U" -> head.setX(head.getX() + 1);
            case "D" -> head.setX(head.getX() - 1);
            case "L" -> head.setY(head.getY() - 1);
            case "R" -> head.setY(head.getY() + 1);
        }
    }

    private static void updateTailPosition(final Position head, final Position tail) {
        if (Objects.equals(head.getX(), tail.getX())) {
            if (head.getY() == tail.getY() + 2) {
                tail.setY(tail.getY() + 1);
                return;
            }
            if (head.getY() == tail.getY() - 2) {
                tail.setY(tail.getY() - 1);
                return;
            }
        }
        if (Objects.equals(head.getY(), tail.getY())) {
            if (head.getX() == tail.getX() + 2) {
                tail.setX(tail.getX() + 1);
                return;
            }
            if (head.getX() == tail.getX() - 2) {
                tail.setX(tail.getX() - 1);
                return;
            }
        }
        if (head.getX() > tail.getX()) {
            if (head.getY() > tail.getY()) {
                if (head.getX() == tail.getX() + 1 && head.getY() == tail.getY() + 1) {
                    return;
                }
                tail.setX(tail.getX() + 1);
                tail.setY(tail.getY() + 1);
                return;
            }
            if (head.getY() < tail.getY()) {
                if (head.getX() == tail.getX() + 1 && head.getY() == tail.getY() - 1) {
                    return;
                }
                tail.setX(tail.getX() + 1);
                tail.setY(tail.getY() - 1);
                return;
            }
        }
        if (head.getX() < tail.getX()) {
            if (head.getY() > tail.getY()) {
                if (head.getX() == tail.getX() - 1 && head.getY() == tail.getY() + 1) {
                    return;
                }
                tail.setX(tail.getX() - 1);
                tail.setY(tail.getY() + 1);
                return;
            }
            if (head.getY() < tail.getY()) {
                if (head.getX() == tail.getX() - 1 && head.getY() == tail.getY() - 1) {
                    return;
                }
                tail.setX(tail.getX() - 1);
                tail.setY(tail.getY() - 1);
            }
        }
    }

    public static Integer part1(final List<String> input) {
        var visitedPositions = new HashSet<ArrayList<Integer>>();
        var head = new Position(0, 0);
        var tail = new Position(0, 0);
        for (var move : input) {
            var direction = move.split(" ")[0];
            var actionsToTake = Integer.parseInt(move.split(" ")[1]);
            for (int i = 0; i < actionsToTake; i++) {
                visitedPositions.add(new ArrayList<>() {{
                    add(tail.getX());
                    add(tail.getY());
                }});
                updateHeadPosition(direction, head);
                updateTailPosition(head, tail);
            }
        }
        visitedPositions.add(new ArrayList<>() {{
            add(tail.getX());
            add(tail.getY());
        }});
        return visitedPositions.size();
    }

    public static Integer part2(final List<String> input) {
        var visitedPositions = new HashSet<ArrayList<Integer>>();
        var ropePositions = new ArrayList<Position>() {{
            add(new Position(0,0));
            add(new Position(0,0));
            add(new Position(0,0));
            add(new Position(0,0));
            add(new Position(0,0));
            add(new Position(0,0));
            add(new Position(0,0));
            add(new Position(0,0));
            add(new Position(0,0));
            add(new Position(0,0));
        }};
        for (var move : input) {
            var direction = move.split(" ")[0];
            var actionsToTake = Integer.parseInt(move.split(" ")[1]);
            for (int i = 0; i < actionsToTake; i++) {
                visitedPositions.add(new ArrayList<>() {{
                    add(ropePositions.get(9).getX());
                    add(ropePositions.get(9).getY());
                }});
                updateHeadPosition(direction, ropePositions.get(0));
                for (int j = 1; j < ropePositions.size(); j++) {
                    updateTailPosition(ropePositions.get(j - 1), ropePositions.get(j));
                }
            }
        }
        visitedPositions.add(new ArrayList<>() {{
            add(ropePositions.get(9).getX());
            add(ropePositions.get(9).getY());
        }});
        return visitedPositions.size();
    }

    public static void main(String... args) {
        var fileContent = getFileContent("java/src/day9/input.txt");
        System.out.println(part1(fileContent)); // 5902
        System.out.println(part2(fileContent)); // 2445
    }
}

