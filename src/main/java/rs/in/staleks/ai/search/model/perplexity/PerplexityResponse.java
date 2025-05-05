package rs.in.staleks.ai.search.model.perplexity;

import lombok.Data;

import java.util.List;

@Data
public class PerplexityResponse {
    private String id;
    private String model;
    private long created;
    private Usage usage;
    private String object;
    private List<Choice> choices;
}
