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

        // courses sample data
        courses.insert(List.of(
                new Cell(12389),
                new Cell("Algorithms"),
                new Cell(4)));
        courses.insert(List.of(
                new Cell(56780),
                new Cell("Spanish I"),
                new Cell(3)));
        courses.insert(List.of(
                new Cell(34560),
                new Cell("Statistics"),
                new Cell(3)));
        courses.insert(List.of(
                new Cell(11203),
                new Cell("Calculus I"),
                new Cell(4)));
        courses.insert(List.of(
                new Cell(87654),
                new Cell("English"),
                new Cell(3)));
        courses.insert(List.of(
                new Cell(98763),
                new Cell("Math"),
                new Cell(2)));
        courses.insert(List.of(
                new Cell(23489),
                new Cell("Business"),
                new Cell(4)));

        // enrollment sample data
        enrollment.insert(List.of(
                new Cell(124972459),
                new Cell(1234),
                new Cell(12389),
                new Cell("C+")));
        enrollment.insert(List.of(
                new Cell(195234580),
                new Cell(1234),
                new Cell(56780),
                new Cell("A")));
        enrollment.insert(List.of(
                new Cell(198524751),
                new Cell(9876540),
                new Cell(11203),
                new Cell("F")));
        enrollment.insert(List.of(
                new Cell(112351464),
                new Cell(9876540),
                new Cell(12389),
                new Cell("C")));
        enrollment.insert(List.of(
                new Cell(234567890),
                new Cell(9876540),
                new Cell(87654),
                new Cell("B")));
        enrollment.insert(List.of(
                new Cell(123765098),
                new Cell(9876540),
                new Cell(23489),
                new Cell("A")));
        enrollment.insert(List.of(
                new Cell(345675432),
                new Cell(2345678),
                new Cell(87654),
                new Cell("B+")));

        // professors sample data
        professors.insert(List.of(
                new Cell(1246819),
                new Cell("Samantha"),
                new Cell("Miller"),
                new Cell("Computer Science")));
        professors.insert(List.of(
                new Cell(1235158),
                new Cell("Jonathon"),
                new Cell("Brown"),
                new Cell("Computer Science")));
        professors.insert(List.of(
                new Cell(1458189),
                new Cell("Bill"),
                new Cell("Davis"),
                new Cell("Mathematics")));
        professors.insert(List.of(
                new Cell(6882910),
                new Cell("Diana"),
                new Cell("Williams"),
                new Cell("Psychology")));
        professors.insert(List.of(
                new Cell(1236547),
                new Cell("Richard"),
                new Cell("Feynman"),
                new Cell("Physics")));
        professors.insert(List.of(
                new Cell(7653459),
                new Cell("Alexander"),
                new Cell("Fleming"),
                new Cell("Biology")));
        professors.insert(List.of(
                new Cell(2345436),
                new Cell("Alexander"),
                new Cell("Hamilton"),
                new Cell("Finance")));

        // teaches sample data
        teaches.insert(List.of(
                new Cell(100),
                new Cell(1458189),
                new Cell(11203)));
        teaches.insert(List.of(
                new Cell(101),
                new Cell(1246819),
                new Cell(12389)));
        teaches.insert(List.of(
                new Cell(200),
                new Cell(6882910),
                new Cell(34560)));
        teaches.insert(List.of(
                new Cell(201),
                new Cell(1235158),
                new Cell(34560)));
        teaches.insert(List.of(
                new Cell(765),
                new Cell(1236547),
                new Cell(98763)));
        teaches.insert(List.of(
                new Cell(436),
                new Cell(2345436),
                new Cell(98763)));

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
