package rs.in.staleks.ai.search.config;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.WebSearchContentRetriever;
import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.tavily.TavilyWebSearchEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Langchain4JConfig {
    
    @Value("${tavily.apiKey}")
    private String tavilyApiKey;

    @Bean
    WebSearchEngine webSearchEngine() {
        return TavilyWebSearchEngine.builder()
                .apiKey(tavilyApiKey)
                .includeRawContent(true)
                .includeAnswer(true)
                .build();
    }

    @Bean
    ContentRetriever contentRetriever(final WebSearchEngine webSearchEngine) {
        return WebSearchContentRetriever.builder()
                .maxResults(3)
                .webSearchEngine(webSearchEngine)
                .build();
    }

}
