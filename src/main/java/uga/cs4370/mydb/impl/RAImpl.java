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

    @Override
    public Relation union(Relation rel1, Relation rel2) {
        return null;
    }

    @Override
    public Relation diff(Relation rel1, Relation rel2) {
        return null;
    }

    @Override
    public Relation rename(Relation rel, List<String> origAttr, List<String> renamedAttr) {
        return null;
    }

    @Override
    public Relation cartesianProduct(Relation rel1, Relation rel2) {
        return null;
    }

    @Override
    public Relation join(Relation rel1, Relation rel2) {
        return null;
    }

    @Override
    public Relation join(Relation rel1, Relation rel2, Predicate p) {
        return null;
    }
}
