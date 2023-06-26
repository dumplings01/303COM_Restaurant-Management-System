package com.fyp.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.restaurant.model.Stock;
import com.fyp.restaurant.model.StockRequestModel;
import com.fyp.restaurant.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
class StockControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockRepository stockRepository;

    Stock stockOne;
    Stock stockTwo;


    @BeforeEach
    void setUp() {
        stockOne = new Stock(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"), "Test stock", "Test type",
                100, 50.5, "kg", 40, new Date());
        stockTwo = new Stock(UUID.fromString("35cc2a5e-2fc9-4e09-b5d0-88002d675fb0"), "Test stock two", "Test type two",
                30, 5.5, "kg", 40, new Date());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getSingleStock() throws Exception {

        UUID stockId = UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe");
        Stock stock = new Stock();
        stock.setStockId(stockId);
        stock.setName("Eggs");
        stock.setStockType("Food");
        stock.setStockQuantity(100);
        stock.setStockWeight(0.0);
        stock.setUnitOfMeasurement("kg");
        stock.setLowStockAlertAt(10);
        stock.setUpdatedAt(new Date());

        when(stockRepository.findByStockId(stockId)).thenReturn(stock);

        mockMvc.perform(get("/stock/getSingleStock")
                        .param("stockId", stockId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stockId", is(stockId.toString())))
                .andExpect(jsonPath("$.name", is("Eggs")))
                .andExpect(jsonPath("$.stockType", is("Food")))
                .andExpect(jsonPath("$.stockQuantity", is(100)))
                .andExpect(jsonPath("$.stockWeight", is(0.0)))
                .andExpect(jsonPath("$.unitOfMeasurement", is("kg")))
                .andExpect(jsonPath("$.lowStockAlertAt", is(10)))
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void getAllStock() throws Exception {
        Stock stock1 = new Stock(UUID.randomUUID(), "Stock 1", "Type 1", 10, 5.0, "kg", 5, new Date());
        Stock stock2 = new Stock(UUID.randomUUID(), "Stock 2", "Type 2", 20, 8.0, "lbs", 10, new Date());
        List<Stock> stockList = new ArrayList<>();
        stockList.add(stock1);
        stockList.add(stock2);

        when(stockRepository.findAll()).thenReturn(stockList);

        mockMvc.perform(get("/stock/getAllStocks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].stockId").value(stock1.getStockId().toString()))
                .andExpect(jsonPath("$[0].name").value(stock1.getName()))
                .andExpect(jsonPath("$[0].stockType").value(stock1.getStockType()))
                .andExpect(jsonPath("$[0].stockQuantity").value(stock1.getStockQuantity()))
                .andExpect(jsonPath("$[0].stockWeight").value(stock1.getStockWeight()))
                .andExpect(jsonPath("$[0].unitOfMeasurement").value(stock1.getUnitOfMeasurement()))
                .andExpect(jsonPath("$[0].lowStockAlertAt").value(stock1.getLowStockAlertAt()))
                .andExpect(jsonPath("$[1].stockId").value(stock2.getStockId().toString()))
                .andExpect(jsonPath("$[1].name").value(stock2.getName()))
                .andExpect(jsonPath("$[1].stockType").value(stock2.getStockType()))
                .andExpect(jsonPath("$[1].stockQuantity").value(stock2.getStockQuantity()))
                .andExpect(jsonPath("$[1].stockWeight").value(stock2.getStockWeight()))
                .andExpect(jsonPath("$[1].unitOfMeasurement").value(stock2.getUnitOfMeasurement()))
                .andExpect(jsonPath("$[1].lowStockAlertAt").value(stock2.getLowStockAlertAt()));
    }

    @Test
    void createStock() throws Exception {
        StockRequestModel stockRequestModel = new StockRequestModel();
        stockRequestModel.setName("Test Stock");
        stockRequestModel.setStockType("Type");
        stockRequestModel.setStockQuantity(100);
        stockRequestModel.setStockWeight(50.5);
        stockRequestModel.setUnitOfMeasurement("kg");
        stockRequestModel.setLowStockAlertAt(10);
        stockRequestModel.setUpdatedAt(new Date());

        Stock createdStock = new Stock();
        createdStock.setStockId(UUID.randomUUID());
        createdStock.setName(stockRequestModel.getName());
        createdStock.setStockType(stockRequestModel.getStockType());
        createdStock.setStockQuantity(stockRequestModel.getStockQuantity());
        createdStock.setStockWeight(stockRequestModel.getStockWeight());
        createdStock.setUnitOfMeasurement(stockRequestModel.getUnitOfMeasurement());
        createdStock.setLowStockAlertAt(stockRequestModel.getLowStockAlertAt());
        createdStock.setUpdatedAt(stockRequestModel.getUpdatedAt());

        when(stockRepository.existsByName(stockRequestModel.getName())).thenReturn(false);
        when(stockRepository.save(any(Stock.class))).thenReturn(createdStock);

        mockMvc.perform(post("/stock/recordStock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(stockRequestModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(stockRequestModel.getName()))
                .andExpect(jsonPath("$.stockType").value(stockRequestModel.getStockType()))
                .andExpect(jsonPath("$.stockQuantity").value(stockRequestModel.getStockQuantity()))
                .andExpect(jsonPath("$.stockWeight").value(stockRequestModel.getStockWeight()))
                .andExpect(jsonPath("$.unitOfMeasurement").value(stockRequestModel.getUnitOfMeasurement()))
                .andExpect(jsonPath("$.lowStockAlertAt").value(stockRequestModel.getLowStockAlertAt()));
    }

    @Test
    void updateStock() throws Exception {
        UUID stockId = UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe");

        StockRequestModel stockDetails = new StockRequestModel();
        stockDetails.setName("Updated Stock");
        stockDetails.setStockType("Updated Type");
        stockDetails.setStockQuantity(200);
        stockDetails.setStockWeight(75.5);
        stockDetails.setUnitOfMeasurement("lbs");
        stockDetails.setLowStockAlertAt(5);

        Stock existingStock = new Stock();
        existingStock.setStockId(stockId);
        existingStock.setName("Test Stock");
        existingStock.setStockType("Type");
        existingStock.setStockQuantity(100);
        existingStock.setStockWeight(50.5);
        existingStock.setUnitOfMeasurement("kg");
        existingStock.setLowStockAlertAt(10);
        existingStock.setUpdatedAt(new Date());

        when(stockRepository.findByStockId(stockId)).thenReturn(existingStock);
        when(stockRepository.existsByName(stockDetails.getName())).thenReturn(false);

        mockMvc.perform(put("/stock/updateStock")
                        .param("stockId", stockId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(stockDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(stockDetails.getName())))
                .andExpect(jsonPath("$.stockType", is(stockDetails.getStockType())))
                .andExpect(jsonPath("$.stockQuantity", is(stockDetails.getStockQuantity())))
                .andExpect(jsonPath("$.stockWeight", is(stockDetails.getStockWeight())))
                .andExpect(jsonPath("$.unitOfMeasurement", is(stockDetails.getUnitOfMeasurement())))
                .andExpect(jsonPath("$.lowStockAlertAt", is(stockDetails.getLowStockAlertAt())))
                .andExpect(jsonPath("$.updatedAt", notNullValue()));
    }

    @Test
    void deleteStock() throws Exception{
        UUID stockId = UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe");

        mockMvc.perform(delete("/stock/deleteStock")
                        .param("stockId", stockId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock record deleted successfully!"));

    }

    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}