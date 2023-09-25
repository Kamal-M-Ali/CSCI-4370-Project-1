package uga.cs4370.mydb.impl;

import uga.cs4370.mydb.Cell;
import uga.cs4370.mydb.Predicate;
import uga.cs4370.mydb.RA;
import uga.cs4370.mydb.Relation;

import java.util.ArrayList;
import java.util.List;

public class RAImpl implements RA {
    @Override
    public Relation select(Relation rel, Predicate p) {
        Relation res = new RelationImpl(rel.getName(), rel.getAttrs(), rel.getTypes());
        for (List<Cell> row : rel.getRows()) {
            if (p.check(row))
                res.insert(row);
        }
        return res;
    }

    @Override
    public Relation project(Relation rel, List<String> attrs) {
        return null;
    }

    /**
     *
     * @param rel1
     * @param rel2
     * @return The resulting relation after applying the union operation.
     * @throws IllegalArgumentException //If rel1 and rel2 are not compatible.
     */
    @Override
    public Relation union(Relation rel1, Relation rel2) throws IllegalArgumentException{
        Relation res = new RelationImpl("Union", rel1.getAttrs(), rel1.getTypes());
        // Check compatible
        if(rel1.getAttrs().size() == rel2.getAttrs().size()) {
            // insert rows in Relation 1
            for (List<Cell> row1 : rel1.getRows()) {
                res.insert(row1);
            }
            // find the unrepeated rows
            for (List<Cell> row2 : rel2.getRows()) {
                boolean isRepeat = false;
                for (List<Cell> row1 : rel1.getRows()) {
                    if(row2.equals(row1)) {
                        isRepeat = true;
                    }
                }
                // insert the unrepeated rows in Relation 2
                if(isRepeat == false) {
                    res.insert(row2);
                }
            }
        } else {
            System.out.println("Sorry, two relation are not compatible.");
        }
        return res;
    }

    /**
     *
     * @param rel1
     * @param rel2
     * @return The resulting relation after applying the diff operation.
     * @throws IllegalArgumentException //If rel1 and rel2 are not compatible.
     */
    @Override
    public Relation diff(Relation rel1, Relation rel2) throws IllegalArgumentException{
        Relation repeat = new RelationImpl("Repeat", rel1.getAttrs(), rel1.getTypes());
        Relation res = new RelationImpl("Diff", rel1.getAttrs(), rel1.getTypes());
        // Check compatible
        if(rel1.getAttrs().size() == rel2.getAttrs().size()) {
            // insert the unique rows in Relation 1
            for (List<Cell> row1 : rel1.getRows()) {
                boolean isRepeat = false;
                for (List<Cell> row2 : rel2.getRows()) {
                    // find the repeated part
                    if (row2.equals(row1)) {
                        isRepeat = true;
                        repeat.insert(row2);
                    }
                }
                if (isRepeat == false) {
                    res.insert(row1);
                }
            }
            // insert the unique rows in Relation 2
            for (List<Cell> row2 : rel2.getRows()) {
                boolean isRepeat = false;
                for (List<Cell> row3 : repeat.getRows()) {
                    if (row2.equals(row3)) {
                        isRepeat = true;
                    }
                }
                if (isRepeat == false) {
                    res.insert(row2);
                }
            }
        } else {
            System.out.println("Sorry, two relation are not compatible.");
        }
        return res;
    }

    @Override
    public Relation rename(Relation rel, List<String> origAttr, List<String> renamedAttr) {
        return null;
    }

    @Override
    public Relation cartesianProduct(Relation rel1, Relation rel2) {
        List<String> resultAttrs = new ArrayList<>(rel1.getAttrs());
        resultAttrs.addAll(rel2.getAttrs());

        List<Type> resultTypes = new ArrayList<>(rel1.getTypes());
        resultTypes.addAll(rel2.getTypes());

        Relation result = new RelationImpl("CartesianProduct", resultAttrs, resultTypes);

        if (!rel1.getAttrs().isEmpty() && !rel2.getAttrs().isEmpty()) {
            for (List<Cell> row1 : rel1.getRows()) {
                for (List<Cell> row2 : rel2.getRows()) {
                    List<Cell> productRow = new ArrayList<>(row1);
                    productRow.addAll(row2);
                    result.insert(productRow);
                }
            }
        }
        return result;
    }

    @Override
    public Relation join(Relation rel1, Relation rel2) {
        List<String> resultAttrs = new ArrayList<>(rel1.getAttrs());
        resultAttrs.addAll(rel2.getAttrs());

        List<Type> resultTypes = new ArrayList<>(rel1.getTypes());
        resultTypes.addAll(rel2.getTypes());

        Relation result = new RelationImpl("JoinedRelation", resultAttrs, resultTypes);

        for (List<Cell> row1 : rel1.getRows()) {
            for (List<Cell> row2 : rel2.getRows()) {
                boolean isMatch = true;

                for (String attr : rel1.getAttrs()) {
                    int index1 = rel1.getAttrs().indexOf(attr);
                    int index2 = rel2.getAttrs().indexOf(attr);
                    if (!row1.get(index1).getValue().equals(row2.get(index2).getValue())) {
                        isMatch = false;
                        break;
                    }
                }

                if (isMatch) {
                    List<Cell> joinedRow = new ArrayList<>(row1);
                    joinedRow.addAll(row2);
                    result.insert(joinedRow);
                }
            }
        }

        return result;

    }

    @Override
    public Relation join(Relation rel1, Relation rel2, Predicate p) {
        return null;
    }
}
