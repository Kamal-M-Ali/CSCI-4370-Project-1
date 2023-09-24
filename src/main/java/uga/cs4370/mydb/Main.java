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

        Relation test1 = new RelationImpl("Test1",
                new ArrayList<String>(List.of("ID", "Name", "Dept")),
                new ArrayList<Type>(List.of(Type.INTEGER, Type.STRING, Type.STRING)));
        test1.print();

        Relation test3 = new RelationImpl("Test3",
                new ArrayList<String>(List.of("ID", "Name")),
                new ArrayList<Type>(List.of(Type.INTEGER, Type.STRING)));
        test3.print();

        test3.insert(new Cell(1234), new Cell("Jane Doe"));
        test3.insert(new Cell(3001), new Cell("Ada Doe"));
        test3.insert(new Cell(3002), new Cell("Anna Doe"));
        test3.print();

        RA ra = new RAImpl();
        ra.select(test, (List<Cell> row) -> row.contains(new Cell("Jane Doe"))).print();
        ra.select(test, (List<Cell> row) -> row.get(test.getAttrIndex("Name")).getAsString().equals("Jane Doe")).print();

        RA unionRA = new RAImpl();
        unionRA.union(test, test3).print();
        unionRA.union(test, test1).print(); // not compatible

        RA diffRA = new RAImpl();
        diffRA.diff(test, test3).print();
        diffRA.diff(test, test1).print(); // not compatible
    }
}
