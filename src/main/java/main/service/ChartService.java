package main.service;

import main.dto.ChartDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ChartService {

    ChartDTO prepareDataForChart();
}
