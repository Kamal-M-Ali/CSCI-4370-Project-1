package uga.cs4370.mydb.impl;

import uga.cs4370.mydb.*;

import java.util.*;

public class RAImpl implements RA {
    @Override
    public Relation select(Relation rel, Predicate p)
    {
        Relation res = new RelationImpl("Select", rel.getAttrs(), rel.getTypes());
        for (List<Cell> row : rel.getRows()) {
            if (p.check(row))
                res.insert(row);
        }
        return res;
    }

    @Override
    public Relation project(Relation rel, List<String> attrs) throws IllegalArgumentException
    {
        List<Type> types = new ArrayList<>();
        for (String attr : attrs) {
            // using .getAttrIndex() will throw automatically if attr doesnt exist
            types.add(rel.getTypes().get(rel.getAttrIndex(attr)));
        }

        Relation res = new RelationImpl("Project", attrs, types);
        for (List<Cell> row : rel.getRows()) {
            List<Cell> newRow = new ArrayList<>();
            for (String attr : attrs)
                newRow.add(row.get(rel.getAttrIndex(attr)));
            res.insert(newRow);
        }

        return res;
    }

    @Override
    public Relation union(Relation rel1, Relation rel2) throws IllegalArgumentException
    {
        Relation res = new RelationImpl("Union", rel1.getAttrs(), rel1.getTypes());
        // Check compatible
        if (rel1.getAttrs().size() != rel2.getAttrs().size())
            throw new IllegalArgumentException("Incompatible relations to union.");

        // insert rows in Relation 1
        for (List<Cell> row1 : rel1.getRows()) {
            res.insert(row1);
        }
        // find the unrepeated rows
        for (List<Cell> row2 : rel2.getRows()) {
            boolean isRepeat = false;
            for (List<Cell> row1 : rel1.getRows()) {
                if (row2.equals(row1)) {
                    isRepeat = true;
                    break;
                }
            }
            // insert the unrepeated rows in Relation 2
            if(!isRepeat) {
                res.insert(row2);
            }
        }
        return res;
    }

    @Override
    public Relation diff(Relation rel1, Relation rel2) throws IllegalArgumentException
    {
        Relation repeat = new RelationImpl("Repeat", rel1.getAttrs(), rel1.getTypes());
        Relation res = new RelationImpl("Diff", rel1.getAttrs(), rel1.getTypes());
        // Check compatible
        if(rel1.getAttrs().size() != rel2.getAttrs().size())
            throw new IllegalArgumentException("Incompatible relations to  diff.");

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
            if (!isRepeat) {
                res.insert(row1);
            }
        }
        // insert the unique rows in Relation 2
        for (List<Cell> row2 : rel2.getRows()) {
            boolean isRepeat = false;
            for (List<Cell> row3 : repeat.getRows()) {
                if (row2.equals(row3)) {
                    isRepeat = true;
                    break;
                }
            }
            if (!isRepeat) {
                res.insert(row2);
            }
        }

        return res;
    }

    @Override
    public Relation rename(Relation rel, List<String> origAttr, List<String> renamedAttr) throws IllegalArgumentException
    {
        if (origAttr.size() != renamedAttr.size())
            throw new IllegalArgumentException("Original attributes and renamed attribute size mismatch.");

        List<String> attrs = new ArrayList<>();
        int renamed = 0;

        for (String attr : rel.getAttrs()) {
            int replaced = origAttr.indexOf(attr);
            if (replaced != -1) {
                attrs.add(renamedAttr.get(replaced));
                ++renamed;
            } else {
                attrs.add(attr);
            }
        }

        if (origAttr.size() != renamed)
            throw new IllegalArgumentException("Attributes to rename not found in original relation.");

        Relation res = new RelationImpl("Renamed", attrs, rel.getTypes());
        for (List<Cell> row : rel.getRows())
            res.insert(row);

        return res;
    }

    @Override
    public Relation cartesianProduct(Relation rel1, Relation rel2) throws IllegalArgumentException
    {
        for (String attr : rel1.getAttrs()) {
            if (rel2.hasAttr(attr))
                throw new IllegalArgumentException("Cartesian product cannot contain common attributes.");
        }

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
        // get attributes outside of array since it has to copy everytime
        List<String> rel1Attrs = rel1.getAttrs();
        List<String> rel2Attrs = rel2.getAttrs();
        List<Type> rel2Types = rel2.getTypes();

        // build attribute and type table
        List<String> attrs = new ArrayList<>(rel1Attrs);
        List<Type> types = new ArrayList<>(rel1.getTypes());

        // also need a list of common attributes and their indices in the respective relations
        List<String> common = new ArrayList<>();
        HashMap<String, Integer> rel1AttrMap = new HashMap<>();
        HashMap<String, Integer> rel2AttrMap = new HashMap<>();

        for (int i = 0; i != rel2Attrs.size(); ++i) {
            String attr = rel2Attrs.get(i);
            if (!attrs.contains(attr)) {
                attrs.add(attr);
                types.add(rel2Types.get(i));
            } else {
                common.add(attr);
                rel1AttrMap.put(attr, rel1.getAttrIndex(attr));
                rel2AttrMap.put(attr, i);
            }
        }

        Relation res = new RelationImpl("NaturalJoin",
                new ArrayList<>(attrs),
                new ArrayList<>(types));

        if (common.isEmpty())
            return res;

        for (List<Cell> row1 : rel1.getRows()) {
            for (List<Cell> row2 : rel2.getRows()) {
                boolean isMatch = true;

                for (String attr : common) {
                    Integer i = rel1AttrMap.get(attr);
                    Integer j = rel2AttrMap.get(attr);

                    if (!row1.get(i).equals(row2.get(j))) {
                        isMatch = false;
                        break;
                    }
                }

                if (isMatch) {
                    List<Cell> row = new ArrayList<>();
                    for (String attr : attrs) {
                        int i = rel1Attrs.indexOf(attr);
                        if (i != -1) {
                            row.add(row1.get(i));
                        } else {
                            // must be in relation 2
                            row.add(row2.get(rel2Attrs.indexOf(attr)));
                        }
                    }

                    // insert the new row
                    res.insert(row);
                }
            }
        }

        return res;
    }

    @Override
    public Relation join(Relation rel1, Relation rel2, Predicate p)
    {
        return select(cartesianProduct(rel1, rel2), p);
    }
}
