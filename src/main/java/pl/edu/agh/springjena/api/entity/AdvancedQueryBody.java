package pl.edu.agh.springjena.api.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdvancedQueryBody {
    private final List<WhereExpression> whereExpressions;
    private final List<String> selectedValues;
    private final List<Prefix> prefixes;
    private final List<String> orderBy;
    private final List<String> filter;
    private final Integer limit;
}
