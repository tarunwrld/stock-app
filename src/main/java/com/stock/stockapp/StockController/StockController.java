package com.stock.stockapp.StockController;

import com.stock.stockapp.entity.StockInfo;
import com.stock.stockapp.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/api/test")
    public String testEndpoint() {
        return "Test endpoint is working!";
    }

    @GetMapping("/api/stock-info/{symbol}")
    public StockInfo getStockInfo(@PathVariable String symbol) {
        stockService.setCurrentSymbol(symbol); // Set the symbol in the service
        stockService.updateStockInfo(); // Update stock info for the new symbol
        return stockService.getLatestStockInfo(); // Return the updated stock info
    }
    @GetMapping("/api/stockfindall")
    public String getStockInfo(Model model) {
        model.addAttribute("stockInfoList", stockService.getAllStockInfo());
        return "index"; // Thymeleaf template name (stock-info.html)
    }
}
