package uga.cs4370.mydb.impl;

import uga.cs4370.mydb.Cell;
import uga.cs4370.mydb.Relation;
import uga.cs4370.mydb.Type;

import java.util.ArrayList;
import java.util.List;

public class RelationImpl implements Relation {
    private String name;
    private List<String> attrs;
    private List<Type> types;
    private List<List<Cell>> rows = new ArrayList<>();

    RelationImpl(String name, List<String> attrs, List<Type> types)
    {
        this.name = name;
        this.attrs = attrs;
        this.types = types;
    }

    @Override
    public String getName() {
        return String.valueOf(name);
    }

    @Override
    public int getSize() {
        return rows.size();
    }

    @Override
    public List<List<Cell>> getRows() {
        List<List<Cell>> rowsCopy = new ArrayList<>();
        for (List<Cell> row : rows)
            rowsCopy.add(new ArrayList<>(row));
        return rowsCopy;
    }

    @Override
    public List<Type> getTypes() {
        return new ArrayList<Type>(types);
    }

    @Override
    public List<String> getAttrs() {
        return new ArrayList<String>(attrs);
    }

    @Override
    public boolean hasAttr(String attr) {
        return attrs.contains(attr);
    }

    @Override
    public int getAttrIndex(String attr) throws IllegalArgumentException {
        if (!attrs.contains(attr)) throw new IllegalArgumentException();
        return attrs.indexOf(attr);
    }

    @Override
    public void insert(Cell... cells) throws IllegalArgumentException {
        List<Cell> row = new ArrayList<>();

        for (int i = 0; i < cells.length; i++) {
            if (cells[i].getType() != types.get(i))
                throw new IllegalArgumentException();
            row.add(cells[i]);
        }
        insertWithCheck(row);
    }

    @Override
    public void insert(List<Cell> cells) throws IllegalArgumentException {
        List<Cell> row = new ArrayList<>();

        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i).getType() != types.get(i))
                throw new IllegalArgumentException();
            row.add(cells.get(i));
        }
        insertWithCheck(row);
    }

    @Override
    public void print() {

    }

    /**
     * Inserts a row if it doesn't already exist.
     */
    private void insertWithCheck(List<Cell> row) throws IllegalArgumentException {
        for (List<Cell> existingRow : rows) {
            if (existingRow.equals(row))
                throw new IllegalArgumentException();
        }
        rows.add(row);
    }
}
