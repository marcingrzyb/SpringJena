package pl.edu.agh.springjena.api.controller;

import org.apache.jena.sparql.lang.sparql_11.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.springjena.api.entity.AdvancedQueryBody;
import pl.edu.agh.springjena.api.entity.AskQueryBody;
import pl.edu.agh.springjena.api.entity.WhereExpression;
import pl.edu.agh.springjena.jena.service.QueryService;

import java.util.List;
import java.util.Map;

@RestController
public class WikidataSparqlController {
    @Autowired
    private QueryService queryService;
    @Value("${endpoint.dbpedia}")
    private String endpointWikidata;


    @PostMapping("/wikidata/advanced")
    public List<Map<String, Object>> queryWikidata(@RequestBody AdvancedQueryBody advancedQueryBody) throws ParseException {
        return queryService.createAndRunSimpleQuery(endpointWikidata,
                advancedQueryBody.getSelectedValues(),
                advancedQueryBody.getWhereExpressions(),
                advancedQueryBody.getPrefixes(),
                advancedQueryBody.getOrderBy(),
                advancedQueryBody.getFilter(),
                advancedQueryBody.getLimit());
    }

    @PostMapping("/wikidata/simple")
    public List<Map<String, Object>> simpleQueryWikidata(@RequestBody WhereExpression whereExpression) throws ParseException {
        whereExpression.fillEmptyValues();
        return queryService.createAndRunSimpleQuery(endpointWikidata,
                null,
                List.of(whereExpression),
                null,
                null,
                null,
                null);
    }

    @PostMapping("/wikidata/ask")
    public boolean askDbpedia(@RequestBody AskQueryBody askQueryBody){
        return queryService.createAndRunAskQuery(endpointWikidata,
                askQueryBody.getWhereExpressions(),
                askQueryBody.getPrefixes());
    }
}
