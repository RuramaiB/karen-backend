package zw.co.hushsoft.properbackend.stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreamResponse {
    private Stream stream;
    private String msg;
}
