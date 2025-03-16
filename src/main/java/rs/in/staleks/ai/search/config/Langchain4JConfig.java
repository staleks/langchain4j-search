package rs.in.staleks.ai.search.config;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.WebSearchContentRetriever;
import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.tavily.TavilyWebSearchEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Langchain4JConfig {

    @Bean
    WebSearchEngine webSearchEngine() {
        return TavilyWebSearchEngine.builder()
                .apiKey(System.getenv("TAVILY_API_KEY"))
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
