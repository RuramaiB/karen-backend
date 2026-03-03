package zw.co.hushsoft.properbackend.dataset;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatasetResponse {
    private Dataset dataset;
    private String msg;
}
