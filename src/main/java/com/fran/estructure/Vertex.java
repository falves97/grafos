package com.fran.estructure;

import java.util.Objects;

public class Vertex {

    private String title;

    private Vertex father;

    private VertexColor color;

    private Integer distance;

    private Integer initTime;

    private Integer endTime;

    public Vertex(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Vertex getFather() {
        return father;
    }

    public void setFather(Vertex father) {
        this.father = father;
    }

    public VertexColor getColor() {
        return color;
    }

    public void setColor(VertexColor color) {
        this.color = color;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getInitTime() {
        return initTime;
    }

    public void setInitTime(Integer initTime) {
        this.initTime = initTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex vertex)) return false;
        return Objects.equals(getTitle(), vertex.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }

    @Override
    public String toString() {
        return "Vertex{" + "\n" +
                "title='" + title + "'\n" +
                ", father=" + (father != null ? father.getTitle() : "") + "'\n" +
                ", color=" + color + "\n" +
                ", distance=" + distance + "\n" +
                '}';
    }
}
