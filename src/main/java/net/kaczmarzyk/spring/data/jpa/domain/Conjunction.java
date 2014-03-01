package net.kaczmarzyk.spring.data.jpa.domain;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;


public class Conjunction<T> implements Specification<T> {

    private Specifications<T> joinedSpecs;

    public Conjunction(Collection<Specification<T>> innerSpecs) {
        for (Specification<T> spec : innerSpecs) {
            if (joinedSpecs == null) {
                joinedSpecs = Specifications.where(innerSpecs.iterator().next());
            } else {
                joinedSpecs = joinedSpecs.and(spec);
            }
        }
    }
    
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return joinedSpecs.toPredicate(root, query, cb);
    }

}
