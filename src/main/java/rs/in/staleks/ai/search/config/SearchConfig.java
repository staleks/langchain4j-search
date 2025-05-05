package rs.in.staleks.ai.search.config;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import rs.in.staleks.ai.search.endpoint.SearchController;
import rs.in.staleks.ai.search.service.PerplexitySearchService;
import rs.in.staleks.ai.search.service.SearchService;

@Import({
        Langchain4JConfig.class
})
@Configuration
public class SearchConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    SearchService searchService(final RestTemplate restTemplate) {
        return new PerplexitySearchService(restTemplate);
    }

    @Bean
    SearchController searchController(final ContentRetriever contentRetriever,
                                      final SearchService searchService) {
        return new SearchController(contentRetriever, searchService);
    }

}
