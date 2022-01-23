package pl.edu.agh.springjena.api.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Prefix {
    private String prefix;
    private String uri;
}
