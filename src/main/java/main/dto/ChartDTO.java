package main.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString(exclude = {"dataMap"})
public class ChartDTO {
    private List<String> characterNames;
    private Map<Long, List<Long>> dataMap;
}
