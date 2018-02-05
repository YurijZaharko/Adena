package main.controller;

import main.service.implamentation.ChartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartsController {

    private ChartServiceImpl chartService;

    @GetMapping("/main/charts")
    public String showCharts(Model model) {
        model.addAttribute("chartData", chartService.prepareDataForChart());
        return "charts";
    }

    @Autowired
    public void setChartService(ChartServiceImpl chartService) {
        this.chartService = chartService;
    }
}
