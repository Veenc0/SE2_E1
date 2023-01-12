package de.freerider.application;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


/**
 * JUit 5 tests.
 * 
 * @author sgra64
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class FreeriderApplicationTest {


    /*
     * Test cases 100: test something here.
     */
    @Test @Order(100)
    void test_100() {
        System.out.println("performing test: test_100()");
    }


    /*
     * Test cases 100: test something here.
     */
    @Test @Order(200)
    void test_200() {
        System.out.println("performing test: test_200()");
    }


    /*
     * Test cases 100: test something here.
     */
    @Test @Order(300)
    void test_300() {
        System.out.println("performing test: test_300()");
    }

}
