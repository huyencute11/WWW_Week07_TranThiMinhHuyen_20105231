package iuh.vn.week07.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iuh.vn.week07.DTOs.Statistics.SaleReportModel;
import iuh.vn.week07.DTOs.Statistics.TopEmployeeModel;
import iuh.vn.week07.DTOs.Statistics.TopProductModel;
import iuh.vn.week07.Models.Employee;
import iuh.vn.week07.Models.Order;
import iuh.vn.week07.Models.OrderDetail;
import iuh.vn.week07.Reponsitories.OrderReponsitory;

@Service
public class StatisticsService {
    
    @Autowired
    private OrderReponsitory orderRepository;

    @Autowired
    private AuthService authService;

    public List<TopEmployeeModel> GetTopEmployees(LocalDate date, String token) {
        Employee admin = authService.CheckToken(token);
        if (admin != null && admin.isAdmin()) {
            LocalDate dateFrom = date.withDayOfMonth(1);
            LocalDate dateTo = date.withDayOfMonth(date.lengthOfMonth());
            List<Object[]> orders = orderRepository.findOrdersGroupedByEmployeeId(dateFrom, dateTo);

            Map<Long, List<Order>> ordersByEmployeeId = orders.stream()
                    .collect(Collectors.groupingBy(
                            result -> (Long) result[0], // employeeId
                            Collectors.mapping(result -> (Order) result[1], Collectors.toList())));

            List<TopEmployeeModel> results = new ArrayList<TopEmployeeModel>();
            ordersByEmployeeId.forEach((key,
                    value) -> results.add(new TopEmployeeModel(value.get(0).getEmployee().getFullName(),
                            value.stream()
                                    .map(Order::getOrderDetails)
                                    .flatMap(List::stream)
                                    .collect(Collectors.toList()))));

            return results.stream().sorted(Comparator.comparing(TopEmployeeModel::getTotalPrice).reversed()).limit(10)
                    .toList();
        }
        return null;
    }
    
    public List<SaleReportModel> GetSaleReport(LocalDate date, String token) {
            Employee admin = authService.CheckToken(token);
            if (admin != null && admin.isAdmin()) {
                    LocalDate dateFrom = LocalDate.of(date.getYear(), 1, 1);
                    LocalDate dateTo = date.withDayOfMonth(date.lengthOfMonth());
                    List<Object[]> orders = orderRepository.findOrdersGroupedByMonth(dateFrom, dateTo);

                    Map<Integer, List<Order>> ordersByMonth = orders.stream()
                                    .collect(Collectors.groupingBy(
                                                    result -> (Integer) result[0], // month
                                                    Collectors.mapping(result -> (Order) result[1],
                                                                    Collectors.toList())));

                    List<SaleReportModel> results = new ArrayList<SaleReportModel>();
                    ordersByMonth.forEach((key,
                                    value) -> results.add(new SaleReportModel(key,
                                                    value.stream()
                                                                    .map(Order::getOrderDetails)
                                                                    .flatMap(List::stream)
                                                                    .collect(Collectors.toList()))));

                    return results;
            }
            return null;
    }
    
    public List<TopProductModel> GetTopProduct(LocalDate date, String token) {
            Employee admin = authService.CheckToken(token);
            if (admin != null && admin.isAdmin()) {
                    LocalDate dateFrom = LocalDate.of(date.getYear(), 1, 1);
                    LocalDate dateTo = date.withDayOfMonth(date.lengthOfMonth());
                    List<OrderDetail> orderDetails = orderRepository.findOrdersInMonth(dateFrom, dateTo)
                                    .stream()
                                    .map(Order::getOrderDetails)
                                    .flatMap(List::stream)
                                    .collect(Collectors.toList());

                    Map<Long, List<OrderDetail>> ordersByMonth = orderDetails.stream()
                                    .collect(Collectors.groupingBy(
                                                    result -> result.getProduct().getProductID(), // month
                                                    Collectors.mapping(result -> result, Collectors.toList())));

                    List<TopProductModel> results = new ArrayList<TopProductModel>();
                    ordersByMonth.forEach((key,
                                    value) -> results.add(new TopProductModel(
                                                    value.get(0).getProduct(),
                                                    value.stream().mapToDouble(x -> x.getPrice() * x.getQuantity())
                                                                    .sum())));

                    return results.stream().sorted(Comparator.comparing(TopProductModel::getTotalPrice).reversed())
                                    .limit(10)
                                    .toList();
            }
            return null;
    }
    
    public List<TopEmployeeModel> GetEmployeesInRange(LocalDate startDate, LocalDate endDate, String token, Long[] employeeIds) {
        Employee admin = authService.CheckToken(token);
        if (admin != null && admin.isAdmin()) {
            List<Object[]> orders = orderRepository.findOrdersByEmployeeIds(startDate, endDate, Arrays.asList(employeeIds));

            Map<Long, List<Order>> ordersByEmployeeId = orders.stream()
                    .collect(Collectors.groupingBy(
                            result -> (Long) result[0], // employeeId
                            Collectors.mapping(result -> (Order) result[1], Collectors.toList())));

            List<TopEmployeeModel> results = new ArrayList<TopEmployeeModel>();
            ordersByEmployeeId.forEach((key,
                    value) -> results.add(new TopEmployeeModel(value.get(0).getEmployee().getFullName(),
                            value.stream()
                                    .map(Order::getOrderDetails)
                                    .flatMap(List::stream)
                                    .collect(Collectors.toList()))));

            return results.stream().sorted(Comparator.comparing(TopEmployeeModel::getTotalPrice).reversed()).limit(10)
                    .toList();
        }
        return null;
    }
    
    public List<SaleReportModel> GetSaleReportInRange(LocalDate startDate, LocalDate endDate, String token) {
            Employee admin = authService.CheckToken(token);
            if (admin != null && admin.isAdmin()) {
                    List<Object[]> orders = orderRepository.findOrdersGroupedByMonth(startDate, endDate);

                    Map<Integer, List<Order>> ordersByMonth = orders.stream()
                                    .collect(Collectors.groupingBy(
                                                    result -> (Integer) result[0], // month
                                                    Collectors.mapping(result -> (Order) result[1],
                                                                    Collectors.toList())));

                    List<SaleReportModel> results = new ArrayList<SaleReportModel>();
                    ordersByMonth.forEach((key,
                                    value) -> results.add(new SaleReportModel(key,
                                                    value.stream()
                                                                    .map(Order::getOrderDetails)
                                                                    .flatMap(List::stream)
                                                                    .collect(Collectors.toList()))));

                    return results;
            }
            return null;
    }
    
    public List<TopProductModel> GetTopProductInRange(LocalDate startDate, LocalDate endDate, String token) {
        Employee admin = authService.CheckToken(token);
        if (admin != null && admin.isAdmin()) {
            List<OrderDetail> orderDetails = orderRepository.findOrdersInMonth(startDate, endDate)
                        .stream()
                        .map(Order::getOrderDetails)
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
            
            Map<Long, List<OrderDetail>> ordersByMonth = orderDetails.stream()
                    .collect(Collectors.groupingBy(
                            result ->  result.getProduct().getProductID(), // month
                            Collectors.mapping(result -> result, Collectors.toList())));

            List<TopProductModel> results = new ArrayList<TopProductModel>();
            ordersByMonth.forEach((key,
                    value) -> results.add(new TopProductModel(
                                value.get(0).getProduct(),
                            value.stream().mapToDouble(x -> x.getPrice() * x.getQuantity()).sum())
                            ));

            return results.stream().sorted(Comparator.comparing(TopProductModel::getTotalPrice).reversed())
                        .limit(10)
                        .toList();
        }
        return null;
    }
}
