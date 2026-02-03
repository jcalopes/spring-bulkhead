# Bulkhead pattern
Demonstration of bulkhead pattern using Spring and Resilience4j lib.

### Reference Documentation
For load tests run (Ubuntu):

sudo apt install hey 
hey -n 5 -c 5 -o csv http://localhost:8080/orders
