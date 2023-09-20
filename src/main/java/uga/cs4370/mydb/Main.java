package uga.cs4370.mydb;

import uga.cs4370.mydb.impl.RAImpl;
import uga.cs4370.mydb.impl.RelationImpl;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Relation empty = new RelationImpl("EmptySet", new ArrayList<String>(), new ArrayList<Type>());
        empty.print();

        Relation test = new RelationImpl("Test",
                new ArrayList<String>(List.of("ID", "Name")),
                new ArrayList<Type>(List.of(Type.INTEGER, Type.STRING)));
        test.print();

        test.insert(new Cell(1234), new Cell("Jane Doe"));
        test.insert(new Cell(2234), new Cell("John Doe"));
        test.print();

        Relation test1 = new RelationImpl("Test",
                new ArrayList<String>(List.of("ID", "Name", "Dept")),
                new ArrayList<Type>(List.of(Type.INTEGER, Type.STRING, Type.STRING)));
        test1.print();

        RA ra = new RAImpl();
        ra.select(test, (List<Cell> row) -> row.contains(new Cell("Jane Doe"))).print();
        ra.select(test, (List<Cell> row) -> row.get(test.getAttrIndex("Name")).getAsString().equals("Jane Doe")).print();
    }
}
