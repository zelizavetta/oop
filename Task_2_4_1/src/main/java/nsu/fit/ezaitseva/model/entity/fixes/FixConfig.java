package nsu.fit.ezaitseva.model.entity.fixes;

import groovy.lang.Closure;
import lombok.Data;
import nsu.fit.ezaitseva.model.dsl.Delegator;

import java.util.Map;

@Data
public class FixConfig {
    private Map<String, StudentInformation> informationMap;

    public FixConfig(Map<String, StudentInformation> informationMap) {
        this.informationMap = informationMap;

    }

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