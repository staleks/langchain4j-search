package rs.in.staleks.ai.search.model.tavily;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WebSearchRequest {

    @NotBlank(message = "Query cannot be blank")
    private String query;

}
