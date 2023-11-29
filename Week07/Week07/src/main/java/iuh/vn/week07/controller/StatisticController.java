package iuh.vn.week07.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iuh.vn.week07.DTOs.Statistics.SaleReportModel;
import iuh.vn.week07.DTOs.Statistics.TopEmployeeModel;
import iuh.vn.week07.DTOs.Statistics.TopProductModel;
import iuh.vn.week07.services.StatisticsService;

@RestController
@RequestMapping("statistics/")
@CrossOrigin("http://localhost:3000")
public class StatisticController {

    @Autowired
    private StatisticsService statisticsService;

    //https://www.react-google-charts.com/examples/bar
    //Top 10 employees total price order of month of parameter "date"
    @GetMapping("/topEmployees")
    public ResponseEntity<List<TopEmployeeModel>> GetTopEmployees(@RequestParam(required = true) LocalDate date,
            @RequestParam(required = true) String token) {
        List<TopEmployeeModel> topEmployees = statisticsService.GetTopEmployees(date, token);
        return topEmployees != null ? ResponseEntity.ok(topEmployees)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    
    //https://www.react-google-charts.com/examples/line
    //Sales reporter year of parameter "date"
    @GetMapping("/saleReport")
    public ResponseEntity<List<SaleReportModel>> GetSaleReport(@RequestParam(required = true) LocalDate date,
            @RequestParam(required = true) String token) {
        List<SaleReportModel> reports = statisticsService.GetSaleReport(date, token);
        return reports != null ? ResponseEntity.ok(reports)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping("/topProducts")
    public ResponseEntity<List<TopProductModel>> GetTopProduct(@RequestParam(required = true) LocalDate date,
                    @RequestParam(required = true) String token) {
            List<TopProductModel> topProducts = statisticsService.GetTopProduct(date, token);
            return topProducts != null ? ResponseEntity.ok(topProducts)
                            : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    //https://www.react-google-charts.com/examples/bar
    //Top 10 employees total price order of month of parameter "date"
    @GetMapping("/EmployeesInRange")
    public ResponseEntity<List<TopEmployeeModel>> GetTopEmployeesInRange(@RequestParam(required = true) LocalDate startDate,
                        @RequestParam(required = true) LocalDate endDate,
            @RequestParam(required = true) String token,
            @RequestParam(required = true) Long[] employeeIds) {
        List<TopEmployeeModel> topEmployees = statisticsService.GetEmployeesInRange(startDate, endDate, token, employeeIds);
        return topEmployees != null ? ResponseEntity.ok(topEmployees)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
    
    //https://www.react-google-charts.com/examples/line
    //Sales reporter year of parameter "date"
    @GetMapping("/saleReportInRange")
    public ResponseEntity<List<SaleReportModel>> GetSaleReportInRange(
                    @RequestParam(required = true) LocalDate startDate,
                        @RequestParam(required = true) LocalDate endDate,
            @RequestParam(required = true) String token) {
        List<SaleReportModel> reports = statisticsService.GetSaleReportInRange(startDate, endDate, token);
        return reports != null ? ResponseEntity.ok(reports)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping("/topProductsInRange")
    public ResponseEntity<List<TopProductModel>> GetTopProductInRange(@RequestParam(required = true) LocalDate startDate,
                        @RequestParam(required = true) LocalDate endDate,
            @RequestParam(required = true) String token) {
        List<TopProductModel> topProducts = statisticsService.GetTopProductInRange(startDate, endDate, token);
        return topProducts != null ? ResponseEntity.ok(topProducts)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
