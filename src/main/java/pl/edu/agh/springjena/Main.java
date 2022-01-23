package pl.edu.agh.springjena;

import com.google.gson.Gson;
import pl.edu.agh.springjena.api.entity.WhereExpression;

public class Main {
    public static void main(String[] args) {
        WhereExpression whereExpression = WhereExpression.builder()
                .predicate("<http://xmlns.com/foaf/0.1/name>")
                .object("'Cristiano Ronaldo'@en")
                .build();
        Gson gson = new Gson();
        String js = gson.toJson(whereExpression);
        System.out.println(js);
    }
}
