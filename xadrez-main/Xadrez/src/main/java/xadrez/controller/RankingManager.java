package xadrez.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RankingManager {

    private static final String RANKING_FILE = "ranking.json";

    // Lê o ranking do arquivo JSON
    private static Map<String, Integer> readRanking() {
        Map<String, Integer> ranking = new HashMap<>();
        File file = new File(RANKING_FILE);

        if (!file.exists()) {
            return ranking; // Retorna um mapa vazio se o arquivo não existir
        }

        try {
            String content = new String(Files.readAllBytes(Paths.get(RANKING_FILE)));
            if (content.trim().isEmpty() || content.equals("{}")) {
                return ranking;
            }
            // Análise manual do JSON
            content = content.replace("{", "").replace("}", "").trim();
            String[] pairs = content.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                String player = keyValue[0].replace("\"", "").trim();
                Integer wins = Integer.parseInt(keyValue[1].trim());
                ranking.put(player, wins);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o ranking: " + e.getMessage());
        }
        return ranking;
    }

    // Escreve o ranking no arquivo JSON
    private static void writeRanking(Map<String, Integer> ranking) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        int count = 0;
        for (Map.Entry<String, Integer> entry : ranking.entrySet()) {
            jsonBuilder.append("  \"").append(entry.getKey()).append("\": ").append(entry.getValue());
            if (++count < ranking.size()) {
                jsonBuilder.append(",\n");
            } else {
                jsonBuilder.append("\n");
            }
        }
        jsonBuilder.append("}\n");

        try (FileWriter writer = new FileWriter(RANKING_FILE)) {
            writer.write(jsonBuilder.toString());
        } catch (IOException e) {
            System.err.println("Erro ao escrever o ranking: " + e.getMessage());
        }
    }

    // Atualiza o ranking com o vencedor
    public static void updateRanking(String winner) {
        Map<String, Integer> ranking = readRanking();
        ranking.put(winner, ranking.getOrDefault(winner, 0) + 1);
        writeRanking(ranking);
        System.out.println("Ranking atualizado. Vencedor: " + winner);
    }
}