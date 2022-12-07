package Day7;

import java.util.ArrayList;

public class Node {

    private final String name;
    private final Node parent;
    private final ArrayList<Node> children;
    private Integer size;

    public Node(final String name, final Node parent) {
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.size = 0;
    }

    public String getName() {
        return this.name;
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }

    public Node getChildNodeByName(final String name) {
        for (var child : this.children) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    public Integer getSize() {
        return this.size;
    }

    public void addDirectory(final String dirLine) {
        this.children.add(new Node(dirLine.split(" ")[1], this));
    }

    public void addFile(final String fileLine) {
        this.size += Integer.parseInt(fileLine.split(" ")[0]);
    }

    public void setSize(final Integer size) {
        this.size = size;
    }
}
