some examples for SpecificationArgumentResolver
===============================================

This is a simple Spring Boot project which presents [Specification Argument Resolver library](https://github.com/tkaczmarzyk/specification-arg-resolver). 

It is an executable jar with embedded H2 db, so just build it with Maven and explore the API. You will find some samples below:

1. Get all customers:

    curl http://localhost:8080/customers

2. Delete a customer

    curl -X DELETE http://localhost:8080/customers/3

    it will perform a soft delete, which you can verify by getting all customers again -- the deleted customer will be still there, but with `deleted = true` flag. As you will see in subsequent points, specification-based query methods will filter it out.

3. Filter customers (include only not deleted ones) by first name and gender (optionally):

    curl http://localhost:8080/customers?firstName=ar
    curl 'http://localhost:8080/customers?firstName=ar&gender=MALE'

4. Filter by name (either first or last, case insensitive) with paging:

    curl 'http://localhost:8080/customers?name=l&page=0&size=2&sort=id'
