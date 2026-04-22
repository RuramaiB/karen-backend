package zw.co.hushsoft.properbackend.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.co.hushsoft.properbackend.exam.ExamService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnomalyService {

    /**
     * Calculates Z-Score for completion times to find statistical outliers.
     * Z = (x - mean) / standard_deviation
     */
    public List<String> detectTimeOutliers(List<Double> completionTimes, double threshold) {
        if (completionTimes.isEmpty()) return List.of();

        double mean = completionTimes.stream().mapToDouble(d -> d).average().orElse(0.0);
        double variance = completionTimes.stream()
                .mapToDouble(d -> Math.pow(d - mean, 2))
                .average().orElse(0.0);
        double stdDev = Math.sqrt(variance);

        if (stdDev == 0) return List.of();

        return completionTimes.stream()
                .filter(time -> Math.abs((time - mean) / stdDev) > threshold)
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    /**
     * Simple Answer Similarity check (Jaccard Index or similar could be used here)
     */
    public double calculateSimilarity(String answer1, String answer2) {
        if (answer1 == null || answer2 == null) return 0.0;
        // Simplified token-based similarity
        List<String> tokens1 = List.of(answer1.toLowerCase().split("\\s+"));
        List<String> tokens2 = List.of(answer2.toLowerCase().split("\\s+"));
        
        long matches = tokens1.stream().filter(tokens2::contains).count();
        return (double) matches / Math.max(tokens1.size(), tokens2.size());
    }
}
