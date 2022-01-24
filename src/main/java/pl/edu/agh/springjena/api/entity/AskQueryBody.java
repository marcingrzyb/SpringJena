package pl.edu.agh.springjena.api.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AskQueryBody {
    private final List<WhereExpression> whereExpressions;
    private final List<Prefix> prefixes;
}
