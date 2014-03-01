package net.kaczmarzyk.spring.data.jpa.domain;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public abstract class PathSpecification<T> implements Specification<T> {
    private String path;
    protected String[] args;

    public PathSpecification(String path, String... args) {
        this.path = path;
        this.args = args;
    }
    
    @SuppressWarnings("unchecked")
    protected <F> Path<F> path(Root<T> root) {
        Path<?> expr = null;
        for (String field : path.split("\\.")) {
            if (expr == null) {
                expr = root.get(field);
            } else {
                expr = expr.get(field);
            }
        }
        return (Path<F>) expr;
    }
}
