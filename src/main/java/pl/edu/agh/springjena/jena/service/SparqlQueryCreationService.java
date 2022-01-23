package pl.edu.agh.springjena.jena.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.sparql.lang.sparql_11.ParseException;
import org.springframework.stereotype.Service;
import pl.edu.agh.springjena.api.entity.Prefix;
import pl.edu.agh.springjena.api.entity.WhereExpression;
import pl.edu.agh.springjena.jena.utils.RandomVariableQueryNameGenerator;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class SparqlQueryCreationService {
    public SelectBuilder resolveWhereExpression(RandomVariableQueryNameGenerator randomVariableQueryNameGenerator, SelectBuilder selectBuilder, List<WhereExpression> whereExpression) {
        if (!isNull(whereExpression) && !whereExpression.isEmpty()) {
            for (WhereExpression we : whereExpression) {
                if (!we.isEmpty()) {
                    String subject = StringUtils.isBlank(we.getSubject()) ? randomVariableQueryNameGenerator.generateNewVariableQueryName() : we.getSubject();
                    String predicate = StringUtils.isBlank(we.getPredicate()) ? randomVariableQueryNameGenerator.generateNewVariableQueryName() : we.getPredicate();
                    String object = StringUtils.isBlank(we.getObject()) ? randomVariableQueryNameGenerator.generateNewVariableQueryName() : we.getObject();
                    selectBuilder.addWhere(subject, predicate, object);
                }
            }
        }
        return selectBuilder;
    }

    public SelectBuilder resolveSelectedValues(SelectBuilder selectBuilder, List<String> selectedValues) {
        if (isNull(selectedValues) || selectedValues.isEmpty()) {
            selectBuilder.addVar("*");
        } else {
            for (String selectedValue : selectedValues) {
                selectBuilder.addVar(selectedValue);
            }
        }
        return selectBuilder;
    }

    public SelectBuilder resolvePrefix(SelectBuilder selectBuilder, List<Prefix> prefixList) {
        if (!isNull(prefixList) && !prefixList.isEmpty()) {
            for (Prefix prefix : prefixList) {
                selectBuilder.addPrefix(prefix.getPrefix(), prefix.getUri());

            }
        }
        return selectBuilder;
    }

    public SelectBuilder resolveOrderBy(SelectBuilder selectBuilder, List<String> orderBy) {
        if (!isNull(orderBy) && !orderBy.isEmpty()) {
            for (String order : orderBy) {
                selectBuilder.addOrderBy(order);
            }
        }
        return selectBuilder;
    }

    public SelectBuilder resolveFilters(SelectBuilder selectBuilder, List<String> filters) throws ParseException {
        if (!isNull(filters) && !filters.isEmpty()) {
            for (String filter : filters) {

                selectBuilder.addFilter(filter);
            }
        }
        return selectBuilder;
    }
}
