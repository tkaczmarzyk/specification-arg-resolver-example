package net.kaczmarzyk.example.web;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;


@Or({
    @Spec(params="name", path="firstName", spec=LikeIgnoreCase.class),
    @Spec(params="name", path="lastName", spec=LikeIgnoreCase.class)
})
public interface CustomerByNameSpecification extends NotDeletedCustomerSpecification {

}
