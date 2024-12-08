package co.edu.uptc.model.entities;

public class Node {

    private String id;
    private String name;
    private String type;
    private String color;

    public Node(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Node{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", type='" + type + '\'' + '}';
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}

