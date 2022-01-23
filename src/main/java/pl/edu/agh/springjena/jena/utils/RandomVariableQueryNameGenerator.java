package pl.edu.agh.springjena.jena.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashSet;
import java.util.Set;

public class RandomVariableQueryNameGenerator {
    private Set<String> generatedSet;

    public RandomVariableQueryNameGenerator() {
        generatedSet = new HashSet<>();
    }

    public String generateNewVariableQueryName(){
        return "?" + generateString();
    }

    private String generateString() {
        String alphabetic = RandomStringUtils.randomAlphabetic(4);
        if (generatedSet.contains(alphabetic)){
            return generateString();
        } else return alphabetic;
    }
}
