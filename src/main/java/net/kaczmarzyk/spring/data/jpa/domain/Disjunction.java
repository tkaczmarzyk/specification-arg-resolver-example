package net.kaczmarzyk.spring.data.jpa.domain;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;


public class Disjunction<T> implements Specification<T> {

    public Specifications<T> combinedSpecs;
    
    public Disjunction(Collection<Specification<T>> innerSpecs) {
        for (Specification<T> spec : innerSpecs) {
            if (combinedSpecs == null) {
                combinedSpecs = Specifications.where(spec);
            } else {
                combinedSpecs = combinedSpecs.or(spec);
            }
        }
    }
    
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return combinedSpecs.toPredicate(root, query, cb);
    }

}
