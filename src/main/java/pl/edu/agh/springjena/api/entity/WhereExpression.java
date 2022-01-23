package pl.edu.agh.springjena.api.entity;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import static java.util.Objects.isNull;

@Data
@Builder
public class WhereExpression {
    private String subject;
    private String predicate;
    private String object;

    public boolean isEmpty(){
        return StringUtils.isBlank(subject) && StringUtils.isBlank(predicate) && StringUtils.isBlank(object);
    }
    public void fillEmptyValues(){
        if (isNull(this.subject) || this.subject.isBlank()) {
            this.subject = "?subject";
        }
        if (isNull(this.predicate) || this.predicate.isBlank()) {
            this.predicate = "?predicate";
        }
        if (isNull(this.object) || this.object.isBlank()) {
            this.object = "?object";
        }
    }
}
