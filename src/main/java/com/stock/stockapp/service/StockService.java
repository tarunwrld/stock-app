package com.stock.stockapp.service;

import com.stock.stockapp.Repository.StockRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stock.stockapp.entity.StockInfo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    private final AtomicReference<StockInfo> latestStockInfo = new AtomicReference<>();
    private String currentSymbol;

    public void setCurrentSymbol(String symbol) {
        this.currentSymbol = symbol;
    }

    public void updateStockInfo() {
        if (currentSymbol == null || currentSymbol.isEmpty()) {
            throw new IllegalArgumentException("Symbol must be set before updating stock info");
        }

        try {
            String url = "https://www.google.com/finance/quote/" + currentSymbol + ":NSE";
            String response = getStockInfo(url);
            Document doc = Jsoup.parse(response);

            Element stockPriceElement = doc.selectFirst(".YMlKec.fxKbKc");
            Element rateElement = doc.selectFirst(".JwB6zf");
            Element previousCloseElement = doc.selectFirst(".P6K39c");
            Elements marketCapElements = doc.select(".P6K39c");
            Elements dayrangeElement = doc.select(".P6K39c");

            String name = currentSymbol;
            String marketCap = "Not found";
            String dayrange = "Not found";
            if (!marketCapElements.isEmpty() && !dayrangeElement.isEmpty()) {
                if (marketCapElements.size() > 3 && dayrangeElement.size() > 2) {
                    Element fifthelement = marketCapElements.get(3);
                    marketCap = fifthelement.text();

                    Element sixthelement = dayrangeElement.get(2);
                    dayrange = sixthelement.text();
                    String[] parts = dayrange.split("-");
                    if (parts.length == 2) {
                        String low = parts[0];
                        String high = parts[1];
                        dayrange = " low: " + low + " high: " + high;
                    }
                }
            }

            String stockPrice = stockPriceElement != null ? stockPriceElement.text() : "Not found";
            String rate = rateElement != null ? rateElement.text() : "Not found";
            String previousClose = previousCloseElement != null ? previousCloseElement.text() : "Not found";

            LocalDateTime localTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = localTime.format(formatter);

            StockInfo stockInfo = new StockInfo(name, stockPrice, rate, previousClose, marketCap, dayrange, formattedDate);
            stockRepository.save(stockInfo); // Save the stock info to the repository
            latestStockInfo.set(stockInfo); // Update the latest stock info

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception (e.g., log the error or rethrow it)
        }
    }

    public StockInfo getLatestStockInfo() {
        return latestStockInfo.get();
    }

    private String getStockInfo(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }

    public List<StockInfo> getAllStockInfo() {
        return stockRepository.findAll();
    }
}
