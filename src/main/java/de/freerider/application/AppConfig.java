package de.freerider.application;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Application configuration class.
 * 
 * The @ComponentScan annotation specifies the package below which Spring
 * scans packages for annotations.
 * 
 * @author sgra64
 */
@ComponentScan(basePackages = {"de.freerider"})
@Configuration
public class AppConfig {

    /**
     * Autowired reference to the ApplicationContext bean, which represents the
     * Spring IoC Container.
     * 
     * Objects managed in the Spring IoC Container are called Spring beans.
     * 
     * Spring automatically injects the reference into the applicationContext
     * variable.
     */
    @Autowired
    private ApplicationContext applicationContext;


    /**
     * Interface of the BeanInfo bean, which is a Spring-managed object that is
     * created here by a @Bean-annotated getBeanInfoFactory() method.
     */
    public interface BeanInfo {

        /**
         * Return information about beans registered in the Spring Container.
         * 
         * @param filterPackages include only matching packages.
         * @return information about registered beans.
         */
        List<String> getBeanInfo(String... filterPackages);
    }


    /**
     * @Bean factory method through which Spring creates one(!) instance based on
     * what is returned by the method (here via an anonymous class).
     * 
     * @Bean methods should not be called directly and rather Spring mechanisms
     * such as dependency injection or @Autowired be used to obtain access to the
     * bean instance.
     * 
     * Alternatively, the bean could be created by a @Component-annotated class.
     * 
     * @return BeanInfo bean.
     */
    @Bean
    BeanInfo getBeanInfoFactory() {
        //
        return new BeanInfo() { // Use anonymous class to implement BeanInfo interface.
            //
            @Override
            public List<String> getBeanInfo(String... filterPackages) {
                //
                return Arrays.stream(applicationContext.getBeanDefinitionNames())
                    //
                    .map(beanName -> {  // map beanName to tuple: [beanName, bean]
                        Object bean = applicationContext.getBean(beanName);
                        return new Object[] {beanName, bean};
                    })
                    //
                    .filter(t -> {      // filter tuples for filterPackages args
                        String fullPackagePath = t[1].getClass().getName();
                        for(String ex: filterPackages){
                            if(fullPackagePath.startsWith(ex))
                                return true;
                        }
                        return false;
                    })
                    //                  // format tuple: [beanName, bean] as String
                    .map(t -> String.format(" - %-30s%s", t[0] + ",", t[1].toString()))
                    //                  // collect results
                    .collect(Collectors.toList());
            }
        };
    }
}
