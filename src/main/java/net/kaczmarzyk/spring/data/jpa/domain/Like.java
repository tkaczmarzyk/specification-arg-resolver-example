package net.kaczmarzyk.spring.data.jpa.domain;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class Like<T> extends PathSpecification<T> {

    public Like(String path, String... args) {
        super(path, args);
        if (args == null || args.length != 1) {
            throw new IllegalArgumentException("Expected exactly one argument (the fragment to match against), but got: " + Arrays.toString(args));
        }
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.like(this.<String>path(root), "%" + args[0] + "%");
    }
}
