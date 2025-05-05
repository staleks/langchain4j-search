package rs.in.staleks.ai.search.model.tavily;

import lombok.Data;

@Data
public class WebSearchResponse {

    private String text;
    private String url;
    private float score;

}
