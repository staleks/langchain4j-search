package rs.in.staleks.ai.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import rs.in.staleks.ai.search.model.perplexity.Message;
import rs.in.staleks.ai.search.model.perplexity.PerplexityRequest;
import rs.in.staleks.ai.search.model.perplexity.PerplexityResponse;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class PerplexitySearchService implements SearchService {
    private final String API_URL = "https://api.perplexity.ai/chat/completions";

    @Value("${perplexity.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public String search(final String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(apiKey);

        PerplexityRequest request = new PerplexityRequest();
        request.setModel("sonar");

        List<Message> messages = new ArrayList<>();

        Message systemMessage = new Message();
        systemMessage.setRole("system");
        systemMessage.setContent("You are a dress recommendation expert. For each recommendation, please include the following:\n" +
                "1. Product name and description\n" +
                "2. Reason for recommendation\n" +
                "3. Full URL where actual purchase is possible (e.g. https://www.zara.com/rs/)\n" +
                "If there is no link, please indicate 'Available for purchase on the official website'.");
        messages.add(systemMessage);

        Message userMessage = new Message();
        userMessage.setRole("user");
        userMessage.setContent(query);
        messages.add(userMessage);

        request.setMessages(messages);

        HttpEntity<PerplexityRequest> entity = new HttpEntity<>(request, headers);

        try {
            PerplexityResponse response = restTemplate.postForObject(
                    API_URL,
                    entity,
                    PerplexityResponse.class
            );
            if (response != null && !response.getChoices().isEmpty()) {
                String responseText = response.getChoices().get(0).getMessage().getContent();
                responseText = responseText.replaceAll(
                        "https://[^\\s\n]+",
                        "<a href='$0' target='_blank' class='text-blue-500 hover:underline'>Go to product page</a>"
                );
                return responseText;
            }
            return "Sorry, there was a problem generating your response.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Sorry, there was a problem generating your response.";
        }
    }

}
