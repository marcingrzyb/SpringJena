package pl.edu.agh.springjena.api.controller;

import org.apache.jena.sparql.lang.sparql_11.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.springjena.api.entity.AdvancedQueryBody;
import pl.edu.agh.springjena.api.entity.WhereExpression;
import pl.edu.agh.springjena.jena.service.QueryService;

import java.util.List;
import java.util.Map;

@RestController
public class DbpediaSparqlController {
    @Autowired
    private QueryService queryService;
    @Value("${endpoint.dbpedia}")
    private String endpointDbpedia;

    @PostMapping("/dbpedia/advanced")
    public List<Map<String, Object>> queryDbpedia(@RequestBody AdvancedQueryBody advancedQueryBody) throws ParseException {
        return queryService.createAndRunSimpleQuery(endpointDbpedia,
                advancedQueryBody.getSelectedValues(),
                advancedQueryBody.getWhereExpressions(),
                advancedQueryBody.getPrefixes(),
                advancedQueryBody.getOrderBy(),
                advancedQueryBody.getFilter(),
                advancedQueryBody.getLimit());
    }

    @PostMapping("/dbpedia/simple")
    public List<Map<String, Object>> simpleQueryDbpedia(@RequestBody WhereExpression whereExpression) throws ParseException {
        whereExpression.fillEmptyValues();
        return queryService.createAndRunSimpleQuery(endpointDbpedia,
                null,
                List.of(whereExpression),
                null,
                null,
                null,
                null);
    }
}
