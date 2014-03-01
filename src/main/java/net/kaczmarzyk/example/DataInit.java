package net.kaczmarzyk.example;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.kaczmarzyk.example.domain.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


@Component
public class DataInit {

    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private PlatformTransactionManager txManager;
    
    
    @PostConstruct
    public void initializeData() {
        new TransactionTemplate(txManager).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus arg0) {
                em.persist(new Customer("Homer", "Simpson", date(2014, Calendar.JANUARY, 01)));
                em.persist(new Customer("Marge", "Simpson", date(2014, Calendar.JANUARY, 07)));
                em.persist(new Customer("Bart", "Simpson", date(2014, Calendar.JANUARY, 14)));
                em.persist(new Customer("Lisa", "Simpson", date(2014, Calendar.JANUARY, 21)));
                em.persist(new Customer("Barney", "Gumble", date(2014, Calendar.JANUARY, 22)));
                em.persist(new Customer("Moe", "Szyslak", date(2014, Calendar.JANUARY, 23)));
            }
        });
    }

    private Date date(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }
}
