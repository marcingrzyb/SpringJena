package pl.edu.agh.springjena;

import com.google.gson.Gson;
import pl.edu.agh.springjena.api.entity.AdvancedQueryBody;
import pl.edu.agh.springjena.api.entity.Prefix;
import pl.edu.agh.springjena.api.entity.WhereExpression;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Prefix prefix = Prefix.builder().uri("http://www.w3.org/2000/01/rdf-schema#").prefix("rdfs").build();
        Prefix prefix1 = Prefix.builder().uri("http://dbpedia.org/ontology/").prefix("dbo").build();
        WhereExpression whereExpression = WhereExpression.builder()
                .object("?country")
                .predicate("rdfs:label")
                .object("'Polska'@pl")
                .build();
        AdvancedQueryBody advancedQueryBody = AdvancedQueryBody.builder()
                .selectedValues(List.of("?country"))
                .prefixes(List.of(prefix,prefix1))
                .whereExpressions(List.of(whereExpression))
                .build();
        Gson gson = new Gson();
        String js = gson.toJson(advancedQueryBody);
        System.out.println(js);
    }
}
