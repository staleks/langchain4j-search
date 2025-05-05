package rs.in.staleks.ai.search.model.perplexity;

import lombok.Data;

@Data
public class Choice {
    private int index;
    private String finishReason;
    private Message message;
    private Message delta;
}
