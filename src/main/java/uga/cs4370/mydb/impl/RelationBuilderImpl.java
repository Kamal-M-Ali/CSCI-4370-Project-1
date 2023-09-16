package uga.cs4370.mydb.impl;

import uga.cs4370.mydb.RelationBuilder;
import uga.cs4370.mydb.Type;

import java.util.List;

public class RelationBuilderImpl implements RelationBuilder {
    @Override
    public RelationImpl newRelation(String name, List<String> attrs, List<Type> types) {
        return new RelationImpl(name, attrs, types);
    }
}
