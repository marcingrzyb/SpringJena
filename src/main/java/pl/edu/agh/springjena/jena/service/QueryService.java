package pl.edu.agh.springjena.jena.service;

import org.apache.jena.arq.querybuilder.AskBuilder;
import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.query.*;
import org.apache.jena.sparql.lang.sparql_11.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.springjena.api.entity.Prefix;
import pl.edu.agh.springjena.api.entity.WhereExpression;
import pl.edu.agh.springjena.jena.utils.RandomVariableQueryNameGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@Service
public class QueryService {
    @Autowired
    private SparqlQueryCreationService sparqlQueryCreationService;

    public List<Map<String, Object>> createAndRunSimpleQuery(String sparqlEndpointAddress,
                                                             List<String> selectedValues,
                                                             List<WhereExpression> whereExpression,
                                                             List<Prefix> prefixList,
                                                             List<String> orderBy,
                                                             List<String> filters,
                                                             Integer limit) throws ParseException {
        RandomVariableQueryNameGenerator randomVariableQueryNameGenerator = new RandomVariableQueryNameGenerator();
        SelectBuilder selectBuilder = new SelectBuilder();
        if (nonNull(limit)) selectBuilder.setLimit(limit);
        sparqlQueryCreationService.resolvePrefix(selectBuilder, prefixList);
        sparqlQueryCreationService.resolveSelectedValues(selectBuilder, selectedValues);
        sparqlQueryCreationService.resolveWhereExpression(randomVariableQueryNameGenerator, selectBuilder, whereExpression);
        sparqlQueryCreationService.resolveFilters(selectBuilder, filters);
        sparqlQueryCreationService.resolveOrderBy(selectBuilder, orderBy);
        List<Map<String, Object>> returnValue = executeSparqlSelectQuery(sparqlEndpointAddress, selectBuilder.build());
        return returnValue;
    }

    public boolean createAndRunAskQuery(String sparqlEndpointAddress,
                                        List<WhereExpression> whereExpression,
                                        List<Prefix> prefixList) {
        RandomVariableQueryNameGenerator randomVariableQueryNameGenerator = new RandomVariableQueryNameGenerator();
        AskBuilder askBuilder = new AskBuilder();
        sparqlQueryCreationService.resolvePrefix(askBuilder, prefixList);
        sparqlQueryCreationService.resolveWhereAskExpression(randomVariableQueryNameGenerator, askBuilder, whereExpression);
        return executeSparqlAskQuery(sparqlEndpointAddress, askBuilder.build());
    }

    private boolean executeSparqlAskQuery(String sparqlEndpointAddress, Query query) {
        QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlEndpointAddress, query);
        boolean askResult = qexec.execAsk();
        qexec.close();
        return askResult;
    }

    private List<Map<String, Object>> executeSparqlSelectQuery(String sparqlEndpointAddress, Query query) {
        QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlEndpointAddress, query);
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                Map<String, Object> map = new HashMap<>();
                results.toString();
                QuerySolution querySolution = results.nextSolution();
                List<String> resultVars = results.getResultVars();
                for (String resultVar : resultVars) {
                    map.put(resultVar, querySolution.get(resultVar));
                }
                resultList.add(map);
            }
        } finally {
            qexec.close();
        }
        return resultList;
    }

}
