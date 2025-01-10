package BETAPP;

// Използване на HttpClient за достъп до API
import java.net.http.*;
import java.net.URI;

public class FootballAPI {
    public static String getMatches() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.football-data.org/v4/matches"))
                .header("X-Auth-Token", "f2d43914b5334904b68ead4072d277e5")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static void main(String[] args) throws Exception {
        String response = getMatches();
        String[] elements = response.split("\\{"); // Example split by comma
        for (String element : elements) {
            System.out.println(element.trim()); // Print each element on a new line
        }
    }
}

