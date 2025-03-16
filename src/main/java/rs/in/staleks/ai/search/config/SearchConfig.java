package rs.in.staleks.ai.search.config;

import dev.langchain4j.rag.content.retriever.ContentRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rs.in.staleks.ai.search.endpoint.SearchController;

@Import({
        Langchain4JConfig.class
})
@Configuration
public class SearchConfig {

    @Bean
    SearchController searchController(final ContentRetriever contentRetriever) {
        return new SearchController(contentRetriever);
    }

}
