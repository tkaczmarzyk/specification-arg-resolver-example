package net.kaczmarzyk.spring.data.jpa.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class DateBefore<T> extends PathSpecification<T> {

    private Date date;

    public DateBefore(String path, String[] args, String[] config) throws ParseException {
        super(path, args);
        if (args == null || args.length != 1 || config == null || config.length != 1) {
            throw new IllegalArgumentException();
        }
        String pattern = config[0];
        String dateStr = args[0];
        this.date = new SimpleDateFormat(pattern).parse(dateStr);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.lessThan(this.<Date>path(root), date);
    }

}
