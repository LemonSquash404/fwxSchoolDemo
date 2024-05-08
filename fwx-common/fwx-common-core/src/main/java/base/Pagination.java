package base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Pagination {

    private long pageSize=20;
    private long currentPage=1;

    @JsonIgnore
    private long total;
}
