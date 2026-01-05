package m1jawa.performanceUtils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static m1jawa.performanceUtils.PerformanceUtils.plugin;

public class DiscordWebhook {

    public static void sendMessage(String message) {

        String webhookUrl = plugin.getConfig().getString("discord.webhook-url");
        if (webhookUrl == "" || webhookUrl == null) {
            System.out.println("Webhook url is invalid");
            return;
        }

        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String formattedMessage = message
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");

            String json = "{ \"content\": \"" + formattedMessage + "\" }";

            try (OutputStream os = connection.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 204) {
                System.out.println("Discord returned HTTP " + responseCode);
            }

            connection.getInputStream().close();
        } catch (Exception e) {
            System.out.println("Error sending webhook: " + e);
        }
    }
}
