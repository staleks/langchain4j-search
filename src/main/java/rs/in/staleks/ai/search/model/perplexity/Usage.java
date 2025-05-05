package rs.in.staleks.ai.search.model.perplexity;

import lombok.Data;

@Data
public class Usage {
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;
}
