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

    public RelationImpl(String name, List<String> attrs, List<Type> types)
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
        if (attrs.isEmpty() || types.isEmpty())
            throw new IllegalArgumentException();
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
        if (attrs.isEmpty() || types.isEmpty())
            throw new IllegalArgumentException();
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
        System.out.println(name + ":");
        // check if array is empty
        if (attrs.isEmpty())
            return;

        // get the largest size of each element in each row
        int[] length = new int[attrs.size()];
        if (rows.isEmpty()) {
            for (int i = 0; i < attrs.size(); ++i)
                length[i] = attrs.get(i).length();
        } else {
            for (List<Cell> row : rows) {
                for (int i = 0; i < row.size(); ++i)
                    length[i] = Math.max(length[i], row.get(i).toString().length());
            }
        }

        // build the separator string
        StringBuilder separator = new StringBuilder().append("+--");
        for (int i = 0; i < length.length; ++i) {
            separator.append(String.format("%0" + (length[i] + 2) + "d", 0).replace("0", "-"))
                    .append((i + 1 != length.length) ? "+--" : "+");
        }

        // build the format string
        StringBuilder formatBuilder = new StringBuilder().append("|  ");
        for (int i = 0; i < attrs.size(); ++i) {
            formatBuilder.append("%-").append(length[i] + 2).append("s")
                    .append(i + 1 != length.length ? "|  " : "|");
        }
        String format = formatBuilder.toString();

        // print the table header
        System.out.println(separator);
        System.out.printf((format) + "%n", attrs.toArray());
        System.out.println(separator);

        // generate the table string
        StringBuilder table = new StringBuilder();
        if (rows.isEmpty()) {
            // only print the formatted empty set string
            int padding = (separator.length() - 11) / 2; // subtract length of "|Empty set|"
            String pad = String.format("%0" + padding + "d", 0).replace("0", " ");

            // odd num + even num = odd number, then need additional white space because of integer division
            System.out.println("|" + pad + "Empty set" + pad + (separator.length() % 2 == 0 ? " |" : "|") );
        } else {
            // append each row to the table string
            for (int i = 0; i < rows.size(); ++i) {
                table.append(String.format(format, rows.get(i).toArray()));
                if (i + 1 != rows.size())
                    table.append("\n");
            }

            // print the table body
            System.out.println(table);
        }

        // print the separator at the bottom of the table
        System.out.println(separator);
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
