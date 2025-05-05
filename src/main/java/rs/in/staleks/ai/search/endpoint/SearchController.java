package rs.in.staleks.ai.search.endpoint;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rs.in.staleks.ai.search.model.tavily.WebSearchRequest;
import rs.in.staleks.ai.search.model.tavily.WebSearchResponse;
import rs.in.staleks.ai.search.service.SearchService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final ContentRetriever contentRetriever;
    private final SearchService perplexitySearchService;

    @PostMapping("/tavily/search")
    public ResponseEntity<List<WebSearchResponse>> tavilySearch(@RequestBody WebSearchRequest request) {
        log.debug("Search endpoint called");
        List<WebSearchResponse> response = contentRetriever.retrieve(Query.from(request.getQuery()))
                .stream()
                .map(content -> {
                    WebSearchResponse webSearchResponse = new WebSearchResponse();
                    webSearchResponse.setText(content.textSegment().text());
                    if (content.textSegment() != null && content.textSegment().metadata() != null) {
                        String url = content.textSegment().metadata().getString("url");
                        String decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8);
                        if (decodedUrl != null) {
                            webSearchResponse.setUrl(decodedUrl);
                        }
                    }
                    if (content.textSegment() != null && content.textSegment().metadata() != null) {
                        Float score = content.textSegment().metadata().getFloat("score");
                        if (score != null) {
                            webSearchResponse.setScore(score);
                        }
                    }
                    return webSearchResponse;
                })
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/perplexity/search")
    public ResponseEntity<String> perplexitySearch(@RequestBody WebSearchRequest request) {
        log.debug("Perplexity Search endpoint called");
        return ResponseEntity.ok(perplexitySearchService.search(request.getQuery()));
    }

}
