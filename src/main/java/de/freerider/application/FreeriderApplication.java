package de.freerider.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.freerider.application.AppConfig.BeanInfo;


/**
 * Application class with main() method. Starts the Spring Container.
 * 
 * Run this class with:
 * <pre>
 *  - mvn compile
 *  - mvn package               # package and run final artefact
 *  - java -jar target/se2-freerider-0.0.1-SNAPSHOT.jar
 * 
 *  - mvn spring-boot:run       # Spring's own runner
 * 
 *  - .run.sh                   # run compiled classes only (source .env.sh)
 * </pre>
 * 
 * @author sgra64
 *
 */
@SpringBootApplication
public class FreeriderApplication {

    /**
     * Local logger instance registered under class name.
     */
    private static final Logger logger =
            LoggerFactory.getLogger(FreeriderApplication.class);


    /**
     * Non-public constructor, required by javadoc.
     */
    FreeriderApplication() {
        logger.info("\n(1.) FreeriderApplication instance created.");
    }


    /**
     * main() method that starts the Spring Container.
     * 
     * @param args arguments passed from command line.
     */
    public static void main(String[] args) {
        logger.info("\n(0.) Spring Container starting.");
        //
        // start Spring Container, wait until ready.
        SpringApplication.run(FreeriderApplication.class, args);
        //
        logger.info("\n(3.) Spring Container exited.");
    }


    /**
     * Method is called by Spring Container after container is ready.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterSpringStartup() {
        //
        logger.info("\n(2.) Spring Container ready.");
        //
        var helloMessage = String.format("Hello %s!",
                FreeriderApplication.class.getSimpleName());
        //
        System.out.println(helloMessage);
        //
        if(propertyPrintBeanInfo) {     // set value in application.yaml properties file
            printBeanInfo();
        }
    }


    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * Autowired reference to BeanInfo bean, which is a Spring-managed object that
     * returns information about registered beans with the getBeanInfo() method,
     * see BeanInfo interface in AppConfig.java.
     * 
     * Spring automatically injects the reference to the bean into the beanInfo
     * variable.
     */
    @Autowired
    BeanInfo beanInfo;

    /**
     * @Value-annotated Variable is initialized by Spring with the value obtained from
     * the application.yaml properties file using the specified path. 
     */
    @Value("${application.print_bean_info}")
    private boolean propertyPrintBeanInfo;


    /**
     * Print beans (Spring-managed objects) registered in the Spring Container.
     * 
     */
    void printBeanInfo() {
        System.out.println("\nSpring Container registered Beans, filtered for \"de.freerider\" packages:");
        System.out.println("(bean name, bean obj)");
        System.out.println("-".repeat(80));
        //
        // use @Autowired reference to invoke getBeanInfo()
        // beanInfo.getBeanInfo("org.", "spring.")
        beanInfo.getBeanInfo("de.freerider")
            .forEach(System.out::println);
        //
        System.out.println("-".repeat(80));
    }
}
