package nsu.fit.ezaitseva.model.entity.fixes;

import java.util.Map;
import groovy.lang.Closure;
import lombok.Data;
import nsu.fit.ezaitseva.model.dsl.Delegator;

/**
 * The type Fix config.
 */
@Data
public class FixConfig {
    private Map<String, StudentInformation> informationMap;

    /**
     * Instantiates a new Fix config.
     *
     * @param informationMap the information map
     */
    public FixConfig(Map<String, StudentInformation> informationMap) {
        this.informationMap = informationMap;

    }

    /**
     * For student.
     *
     * @param gitName the git name
     * @param closure the closure
     */
    public void forStudent(String gitName, Closure<?> closure) {
        if (!informationMap.containsKey(gitName)) {
            System.err.println("There is no " + gitName);
            throw new RuntimeException("There is no user with gitName: " + gitName);
        }
        StudentInformation information = informationMap.get(gitName);
        PersonFix personFix = new PersonFix(information);
        Delegator.groovyDelegate(personFix, closure);
    }

}