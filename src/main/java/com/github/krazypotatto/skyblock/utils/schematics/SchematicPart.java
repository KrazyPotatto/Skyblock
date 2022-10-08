package com.github.krazypotatto.skyblock.utils.schematics;

public class SchematicPart {

    String type, block, tree;
    int x, y, z;

    public SchematicPart(String type, String block, String tree, int x, int y, int z) {
        this.type = type;
        this.block = block;
        this.tree = tree;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "SchematicPart{" +
                "type='" + type + '\'' +
                ", block='" + block + '\'' +
                ", tree='" + tree + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
