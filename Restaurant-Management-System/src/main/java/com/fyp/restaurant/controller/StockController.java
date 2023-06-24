package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.Stock;
import com.fyp.restaurant.model.StockRequestModel;
import com.fyp.restaurant.repository.StockRepository;
import com.fyp.restaurant.service.SystemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/stock")
@CrossOrigin("http://localhost:3000")
public class StockController {

    private final StockRepository stockRepository;

    public StockController(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    // get single stock details with stock ID
    @GetMapping("/getSingleStock")
    public Stock getSingleStock(@RequestParam(name="stockId") UUID stockId)
    {
        return this.stockRepository.findByStockId(stockId);
    }

    // return all stock records
    @GetMapping("/getAllStocks")
    public List<Stock> getAllStock()
    {
        return this.stockRepository.findAll();
    }

    // add new stock record
    @PostMapping("/recordStock")
    public ResponseEntity<?> createStock(@Valid @RequestBody StockRequestModel stock)
    {
        Stock value = new SystemService().createStock(stock);

        if (stockRepository.existsByName(value.getName())){
            return new ResponseEntity<>("Name already exist! Please update stock or create new record with different name!",
                    HttpStatus.BAD_REQUEST);
        }

        stockRepository.save(value);

        return new ResponseEntity<Stock>(value, HttpStatus.OK);
    }

    // update record
    @PutMapping("/updateStock")
    public ResponseEntity<?> updateStock(@Valid @RequestParam(value="stockId") UUID stockId,
                                         @RequestBody StockRequestModel stockDetails)
    {
        Stock updateStock = stockRepository.findByStockId(stockId);

        if (!(stockRepository.findByStockId(stockId).getName().equals(stockDetails.getName()))
            && stockRepository.existsByName(stockDetails.getName()))
        {
            return new ResponseEntity<>("Name already exist! Please update stock or choose another name!", HttpStatus.BAD_REQUEST);
        }

        updateStock.setName(stockDetails.getName());
        updateStock.setStockType(stockDetails.getStockType());
        updateStock.setStockQuantity(stockDetails.getStockQuantity());
        updateStock.setStockWeight(stockDetails.getStockWeight());
        updateStock.setUnitOfMeasurement(stockDetails.getUnitOfMeasurement());
        updateStock.setLowStockAlertAt(stockDetails.getLowStockAlertAt());
        updateStock.setUpdatedAt(new Date());

        stockRepository.save(updateStock);

        return new ResponseEntity<Stock>(updateStock, HttpStatus.OK);

    }

    // delete record
    @DeleteMapping("/deleteStock")
    public ResponseEntity<?> deleteStock(@RequestParam(value="stockId") UUID stockId)
    {
        stockRepository.deleteById(stockId);
        return new ResponseEntity<>("Stock record deleted successfully!", HttpStatus.OK);
    }
}
