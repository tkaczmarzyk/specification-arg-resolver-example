package net.kaczmarzyk.spring.data.jpa.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class DateBetween<T> extends PathSpecification<T> {

    private Date after;
    private Date before;

    public DateBetween(String path, String[] args, String[] config) throws ParseException {
        super(path, args);
        if (args == null || args.length != 2 || config == null || config.length != 1) {
            throw new IllegalArgumentException();
        }
        String pattern = config[0];
        String afterDateStr = args[0];
        String beforeDateStr = args[1];
        this.after = new SimpleDateFormat(pattern).parse(afterDateStr);
        this.before = new SimpleDateFormat(pattern).parse(beforeDateStr);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.between(this.<Date>path(root), after, before);
    }

}
