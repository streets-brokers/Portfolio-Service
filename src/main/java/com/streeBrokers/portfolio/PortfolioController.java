package com.streeBrokers.portfolio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/portfolioservice/portfolio")
@Slf4j
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

//    private Holding holding;

    @PostMapping("/create")
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio){
        log.info("Inside createPortfolio method in PortfolioController");
        return portfolioService.savePortfolio(portfolio);
    }

    @GetMapping("clientId/{clientId}")
    public Portfolio getPortfolioByClientId(@PathVariable("clientId") Long clientId){
        log.info("Inside getPortfolioByClientId method i PortfolioController");
        return portfolioService.getPortfolioByClientId(clientId);
    }

    @GetMapping("portfolioId/{portfolioId}")
    public Portfolio getPortfolioById(@PathVariable("portfolioId") Long portfolioId){
        log.info("Inside getPortfolioById method in PortfolioController");
        return portfolioService.getPortfolioById(portfolioId);
    }

//    @GetMapping("holding/")
//    public List<Order> getOrders(){
//        log.info("Inside getOrders method in PortfolioController");
//        return portfolioService.addOrderToHoldingOrderSet();
//    }

    @PostMapping("addFulfilledOrder/{clientId}")
    public void addFulfilledOrder(@RequestBody Order order){
        log.info("Inside addFulfilledOrder method in PortfolioController");
        portfolioService.collectFulfilledOrders(order);
    }

    @GetMapping("fulfilledOrders/{clientId}")
    public List<Order> listOfFulfilledOrder(@PathVariable("clientId")Long clientId){
        log.info("Inside listOfFulfilledOrder method in PortfolioController");
        return portfolioService.getListOfFulfilledOrders(clientId);
    }

//    @PostMapping("addClientOrderToPortfolio/{clientId}")
//    public void addClientOrderToPortfolio(@PathVariable("clientId")Long clientId){
//        log.info("Inside addClientOrderToPortfolio method in PortfolioController");
//        portfolioService.addClientOrder(clientId);
//    }
}
