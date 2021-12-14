package com.streeBrokers.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;

//    @Autowired
//    private HoldingRepository holdingRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;


    public Portfolio savePortfolio(Portfolio portfolio){

        log.info("Inside savePortfolio method in PortfolioService");
        return portfolioRepository.save(portfolio);
    }



    public Portfolio getPortfolioByClientId(Long clientId){
        Optional<Portfolio> portfolioByClientId = portfolioRepository.findByClientId(clientId);
        if(portfolioByClientId.isEmpty()){
            throw new IllegalStateException("Client Portfolio not found");
        }
        Portfolio portfolio = portfolioByClientId.get();
        List<Order> orders = getListOfFulfilledOrders(clientId);

        // grouping happens here right?
        // orders = orderMSFT_1, orderAAPPL_2, orderMSFT_3, orderIBM_4
        // orderMap = {"MSFT": [orderMSFT_1, orderMSFT_3], "AAPL": [orderAAPPL_2], "IBM": [orderIBM_4], }
        // newHolding = {
        Map<String, LinkedList<Order>> orderMap = new HashMap<>();
        Map<String, HoldingObj> newHolding = new HashMap<>();
        for (Order order: orders) {
            // if order side is BUY then add value else subtract
            // if side is Buy then we add quantities else we subtract
            if (orderMap.containsKey(order.getProduct())) {
                orderMap.get(order.getProduct()).add(order);
            }else {
                LinkedList list = new LinkedList();
                list.add(order);
                orderMap.put(order.getProduct(), list);
            }

            if (newHolding.containsKey(order.getProduct())) {
                HoldingObj holdingObj = newHolding.get(order.getProduct());

                if(order.getSide() == "BUY"){
                    newHolding.get(order.getProduct()).setQuantity(holdingObj.getQuantity() + order.getQuantity());
                    newHolding.get(order.getProduct()).setPurchaseValue(holdingObj.getPurchaseValue().add(new BigDecimal(order.getPrice())));
                    newHolding.get(order.getProduct()).setSellValue(holdingObj.getSellValue().add(new BigDecimal(order.getMarketPrice())));
                    newHolding.get(order.getProduct()).setCurrentValue(holdingObj.getCurrentValue().add(new BigDecimal(order.getValue())));
                } else {
                    newHolding.get(order.getProduct()).setQuantity(holdingObj.getQuantity() - order.getQuantity());
                    newHolding.get(order.getProduct()).setPurchaseValue(holdingObj.getPurchaseValue().subtract(new BigDecimal(order.getMarketPrice())));
                    newHolding.get(order.getProduct()).setSellValue(holdingObj.getSellValue().subtract(new BigDecimal(order.getPrice())));
                    newHolding.get(order.getProduct()).setCurrentValue(holdingObj.getCurrentValue().subtract(new BigDecimal(order.getValue())));
                }
            }else {
                HoldingObj holdingObj = new HoldingObj();

                if(order.getSide() == "BUY"){
                    holdingObj.setQuantity(order.getQuantity());
                    holdingObj.setPurchaseValue(holdingObj.getPurchaseValue().add(new BigDecimal(order.getPrice())));
                    holdingObj.setSellValue(holdingObj.getSellValue().add(new BigDecimal(order.getMarketPrice())));
                    holdingObj.setCurrentValue(holdingObj.getCurrentValue().add(new BigDecimal(order.getValue())));
                } else {
                    holdingObj.setQuantity(0 - order.getQuantity());
                    holdingObj.setPurchaseValue(new BigDecimal(0).subtract(new BigDecimal(order.getMarketPrice())));
                    holdingObj.setSellValue(new BigDecimal(0).subtract(new BigDecimal(order.getPrice())));
                    holdingObj.setCurrentValue(new BigDecimal(0).subtract(new BigDecimal(order.getValue())));
                }
                newHolding.put(order.getProduct(), holdingObj);
            }

        }
        Portfolio existingPortfolio = portfolio;
        for(String sym : newHolding.keySet()){

            BigDecimal totalValue = newHolding.get(sym).getCurrentValue();
            totalValue = totalValue.add(newHolding.get(sym).getCurrentValue());
            existingPortfolio.setCurrentTotalValue(totalValue);

            BigDecimal totalPurchaseValue = newHolding.get(sym).getPurchaseValue();
            totalPurchaseValue = totalPurchaseValue.add(newHolding.get(sym).getPurchaseValue());
            existingPortfolio.setPurchaseValue(totalPurchaseValue);

            BigDecimal totalSellValue = newHolding.get(sym).getSellValue();
            totalSellValue= totalSellValue.add(newHolding.get(sym).getSellValue());
            existingPortfolio.setSellValue(totalSellValue);
        }
        Portfolio updatedPortfolio = portfolioRepository.save(existingPortfolio);
        log.info("ORDER MAP {}", orderMap);
        log.info("HOLDING MAP {}", newHolding);
        log.info("Inside getPortfolioByClientId method inside PortfolioService");
        return new Portfolio(updatedPortfolio.getPortfolioId(),updatedPortfolio.getClientId(),updatedPortfolio.getName(),updatedPortfolio.getCurrentTotalValue(),
                updatedPortfolio.getPurchaseValue(),updatedPortfolio.getSellValue());
        /**
         *
         * I would like you to help me return the newHoldings
         */
    }

    public Optional<Portfolio> getPortfolioById(Long portfolioId){
        Optional<Portfolio> portfolioById = portfolioRepository.findByPortfolioId(portfolioId);
        if(portfolioById.isEmpty()){
            throw new IllegalStateException("Portfolio is not found");
        }
        log.info("Inside getPortfolioById method inside PortfolioService");
        return portfolioById;
    }

    public void collectFulfilledOrders(Order order){
        log.info("Inside collectFulfilledOrders method in PortfolioService");
        orderRepository.save(order);
    }

    public List<Order> getListOfFulfilledOrders(Long clientId){
        /**
         *
         * I will query my own db to be able to get a list of orders
         */
        List<Order> orderList = orderRepository.viewListOfFulfilledOrders(clientId);
        log.info("Inside getListOfFulfilledOrders method in PortfolioService");
        if(orderList == null){
            System.out.println("Order is empty"+orderList);
        }

        return orderList;
    }

//    public void addOrder(Order order, Long clientId){
//
//        Set<String> product = new HashSet<>();
//        Holding holding = null;
//
//        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByClientId(clientId).get(0);
//        if (optionalPortfolio.isPresent()) {
//            Portfolio portfolio = optionalPortfolio.get();
//            holding = portfolio.getHoldings().get(order.getProduct());
//            if(holding == null){
//                holding = new Holding();
//                holding.setSymbol(order.getProduct());
//                portfolio.addHolding(holding);
//                product.add(order.product);
//            }
//
//        }
//
//    }

//    public void addOrder(Order order){
//        log.info("Inside addOrder method in PortfolioService");
//        Holding holding = new Holding();
//        if (holding.getOrders().contains(order)) {
//            throw new IllegalStateException("Order already exists");
//        } else {
//            holding.getOrders().add(order);
//            System.out.println(holding.getOrders());
//            // update stats
//            if (order.getSide().equals("BUY")) {
//                holding.setQuantity(holding.getQuantity() + order.getQuantity());
//                holding.setPurchaseValue(holding.getPurchaseValue().add(new BigDecimal(order.getPrice()).multiply(new BigDecimal(order.getQuantity()))));
//            } else if (order.getSide().equals("SELL")) {
//                holding.setQuantity(holding.getQuantity() - order.getQuantity());
//                holding.setSellValue(holding.getSellValue().add(new BigDecimal(order.getPrice()).multiply(new BigDecimal(order.getQuantity()))));
//            }
//        }
//    }

//    public void addClientOrder(Long clientId){
//        log.info("Inside addClientOrder method in PortfolioService");
//        Set<String> product = new HashSet<>();
//        Holding holding = null;
//        List<Order> orders = getListOfFulfilledOrders(clientId);
//
//        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByClientId(clientId).get(0);
//        if (optionalPortfolio.isPresent()) {
//            Portfolio portfolio = optionalPortfolio.get();
//            for(Order order: orders){
//                holding = portfolio.getHoldings().get(order.getProduct());
////                System.out.println("found holding "+ holding);
//                if(holding == null){
//                    holding = new Holding();
//                    holding.setSymbol(order.getProduct());
//                    holding.setCurrentValue(new BigDecimal(order.value));
//                    portfolio.addHolding(holding);
//                    System.out.println(portfolio+"portotptptptp");
////                    holdingRepository.save(holding);
////                    System.out.println(portfolio.getHoldings()+" listings");
//                    product.add(order.product);
//                }else{
//                    addOrder(order);
//                    System.out.println(holding +"  things holding");
//                }
//            }
//            for(String sym: product){
////                System.out.println(sym);
//                String url = "http://localhost:8082/api/v1/marketservice/products/" + sym;
//                MarketData[] data =  restTemplate.getForObject(url,MarketData[].class);
//                double lastTradedPrice = Arrays.stream(data).max(Comparator.comparing(marketData -> marketData.lastTradedPrice))
//                        .map(marketData -> marketData.lastTradedPrice).orElse(0.0);
////                System.out.println(lastTradedPrice);
//                portfolio.getHolding(sym).setCurrentValue(new BigDecimal(lastTradedPrice));
////                holdingRepository.save(holding);
////                System.out.println(holding.getCurrentValue());
////                portfolio.addHolding(collectHoldingByProduct(sym)).setCurrentValue(new BigDecimal(lastTradedPrice));
//            }
//            portfolio.refreshTotalValue();
//        }
//
//    }



    /**
     * one portfolio to one user
     * but one portfolio has a map of holdings(categories or collections of different stocks)
     *
     * Next things to do is to have an add order method
     * You then check if the order exists in the orders set,
     * if it does, you throw an error
     * if it does not, you check
     * whether orders(either buy-side or sell-side)
     * if its is a buy-side,
     * if it is,
     * 1. You recalculate and set the Quantity of that trade order type
     * 2. You recalculate and set the purchase value of that trade order type
     *
     * if its is a sell-side,
     * if it is,
     * 1. You recalculate and set the Quantity of that trade order type
     * 2. You recalculate and set the purchase value of that trade order type
     *
     *
     * Create exceptions and HTTP status codes
     */
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public Set<Order> addOrderToHoldingOrderSet(){
//        String url = "http://localhost:8081/api/v1/orderservice/users/1";
//        OrderSet result =  restTemplate.getForObject(url,OrderSet.class);
//        System.out.println(result.getOrders());
//        return result.getOrders();
//    }
}
