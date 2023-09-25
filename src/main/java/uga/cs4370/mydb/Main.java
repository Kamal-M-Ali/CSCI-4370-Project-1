package uga.cs4370.mydb;

import uga.cs4370.mydb.impl.RAImpl;
import uga.cs4370.mydb.impl.RelationImpl;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        /* SCHEMA DEFINITION FROM ASSIGNMENT 1
        Students(StudentID<pk>, FName, LName, DoB, Major)
        Courses(CourseID<pk>, CName, Credits)
        Enrollment(EnrollmentID<pk>, StudentID<fk>, CourseID<fk>, grade)
        Professors(ProfessorID<pk>, FName, LName, department)
        Teaches(TeachID<pk>, ProfessorID<fk>, CourseID<fk>
         */

        Relation students = new RelationImpl("Students",
                List.of("StudentID", "FName", "LName", "DoB", "Major"),
                List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.STRING, Type.STRING));

        Relation courses = new RelationImpl("Courses",
                List.of("CourseID", "CName", "Credits"),
                List.of(Type.INTEGER, Type.STRING, Type.INTEGER));

        Relation enrollment = new RelationImpl("Enrollment",
                List.of("EnrollmentID", "StudentID", "CourseID", "grade"),
                List.of(Type.INTEGER, Type.INTEGER, Type.INTEGER, Type.STRING));

        Relation professors = new RelationImpl("Professors",
                List.of("ProfessorID", "FName", "LName", "department"),
                List.of(Type.INTEGER, Type.STRING, Type.STRING, Type.STRING));

        Relation teaches = new RelationImpl("Teaches",
                List.of("TeachID", "ProfessorID", "CourseID"),
                List.of(Type.INTEGER, Type.STRING, Type.STRING));

        // student sample data
        students.insert(List.of(
                new Cell(1234567),
                new Cell("Jim"),
                new Cell("Bob"),
                new Cell("2010-02-23"),
                new Cell("Biology")));
        students.insert(List.of(
                new Cell(1234),
                new Cell("Jane"),
                new Cell("Doe"),
                new Cell("2005-10-02"),
                new Cell("Finance")));
        students.insert(List.of(
                new Cell(8351245),
                new Cell("John"),
                new Cell("Smith"),
                new Cell("2002-04-29"),
                new Cell("Physics")));
        students.insert(List.of(
                new Cell(8123985),
                new Cell("Sara"),
                new Cell("Jones"),
                new Cell("2004-01-01"),
                new Cell("Computer Science")));
        students.insert(List.of(
                new Cell(1729285),
                new Cell("Jorge"),
                new Cell("Garcia"),
                new Cell("2003-03-14"),
                new Cell("Computer Science")));
        students.insert(List.of(
                new Cell(9876540),
                new Cell("Abe"),
                new Cell("Link"),
                new Cell("1998-12-18"),
                new Cell("Computer Science")));
        students.insert(List.of(
                new Cell(2345678),
                new Cell("Wash"),
                new Cell("Cherry"),
                new Cell("1987-07-06"),
                new Cell("'Psychology'")));


        INSERT INTO Courses (CourseId, CName, Credits)
        VALUES
                ('12389','Algorithms','4'),
                ('56780','Spanish I','3'),
        ('34560','Statistics','3'),
        ('11203','Calculus I','4'),
        ('87654','English','3'),
        ('98763','Math','2'),
        ('23489','Business','4');

        INSERT INTO Enrollment (EnrollmentID, StudentID, CourseID, grade)
        VALUES
                ('124972459','1234','12389', 'C+'),
                ('195234580','1234','56780', 'A'),
        ('198524751','0987654','11203', 'F'),
        ('112351464','0987654','12389', 'C'),
        ('234567890','0987654','87654', 'B'),
        ('123765098','0987654','23489','A'),
        ('345675432','2345678','87654','B+');

        INSERT INTO Professors(ProfessorID,FName,LName,department)
        VALUES
                ('1246819','Samantha','Miller', 'Computer Science'),
                ('1235158','Jonathon','Brown', 'Computer Science'),
        ('1458189','Bill','Davis', 'Mathematics'),
        ('6882910','Diana','Williams', 'Psychology'),
        ('1236547','Richard','Feynman', 'Physics'),
        ('7653459','Alexander','Fleming','Biology'),
        ('2345436','Alexander','Hamilton','Finance');

        INSERT INTO Teaches(TeachID,ProfessorID,CourseID)
        VALUES
                ('100','1458189','11203'),
                ('101','1246819','12389'),
        ('200','6882910','34560'),
        ('201','1235158','34560'),
        ('765','1236547','98763'),
        ('345','7653459',NULL),
        ('436','2345436','98763');



        Relation empty = new RelationImpl("EmptySet", new ArrayList<String>(), new ArrayList<Type>());
        empty.print();






        Relation test = new RelationImpl("Test",
                List.of("ID", "Name"),
                List.of(Type.INTEGER, Type.STRING));
        test.print();

        test.insert(new Cell(1234), new Cell("Jane Doe"));
        test.insert(new Cell(2234), new Cell("John Doe"));
        test.print();

        Relation test1 = new RelationImpl("Test1",
                List.of("ID", "Name", "Dept"),
                List.of(Type.INTEGER, Type.STRING, Type.STRING));
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
        try {
            unionRA.union(test, test1).print(); // not compatible
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        RA diffRA = new RAImpl();
        diffRA.diff(test, test3).print();
        try {
            diffRA.diff(test, test1).print(); // not compatible
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        ra.cartesianProduct(test, test3).print();
        ra.project(test3, List.of("Name")).print();
        ra.rename(test3, List.of("Name"), List.of("Full Name")).print();
        ra.join(test, test).print();
    }
}
