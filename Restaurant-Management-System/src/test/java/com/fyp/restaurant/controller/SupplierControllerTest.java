package com.fyp.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.restaurant.model.Supplier;
import com.fyp.restaurant.model.SupplierRequestModel;
import com.fyp.restaurant.repository.SupplierRepository;
import com.fyp.restaurant.service.SystemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SupplierController.class)
class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SystemService systemService;

    @MockBean
    private SupplierRepository supplierRepository;

    Supplier supplierOne;
    Supplier supplierTwo;
    List<Supplier> supplierList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        supplierOne = new Supplier(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"),"Eggs Supplier",
                "Eggs", "Type test", 100, null, null, new Date(), new Date(), "Excellent", "Good", 32.53,
                "Order received", new Date(), new Date());

        supplierTwo = new Supplier(UUID.fromString("35cc2a5e-2fc9-4e09-b5d0-88002d675fb0"),"Supplier 2",
                "Fish", "Type testing 2", null, 32.3, "kg", new Date(), new Date(), "Excellent", "Good", 32.53,
                "Order received", new Date(), new Date());
        supplierList.add(supplierOne);
        supplierList.add(supplierTwo);
    }

    @Test
    void getAllSupplierHistory() throws Exception {
                when(supplierRepository.findAll()).thenReturn(supplierList);

        mockMvc.perform(get("/supplier/getAllSupplierHistory"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void getSupplierProfile() throws Exception {

        Supplier sampleSupplier = supplierList.get(0);

        when(supplierRepository.findBySupplierId(sampleSupplier.getSupplierId())).thenReturn(sampleSupplier);

        mockMvc.perform(get("/supplier/getSingleSupplierHistory")
                        .param("supplierId", sampleSupplier.getSupplierId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createSupplierHistory() throws Exception{
        SupplierRequestModel supplierRequestModel = new SupplierRequestModel();
        supplierRequestModel.setSupplierName("Supplier 1");
        supplierRequestModel.setStockName("Stock 1");
        supplierRequestModel.setStockType("Type 1");
        supplierRequestModel.setStockQuantity(100);
        supplierRequestModel.setStockWeight(32.53);
        supplierRequestModel.setUnitOfMeasurement("kg");
        supplierRequestModel.setEstimatedDeliveryDate(new Date());
        supplierRequestModel.setCostOfStock(123.45);
        supplierRequestModel.setStatus("Active");

        Supplier createdSupplier = new Supplier();
        createdSupplier.setSupplierName(supplierRequestModel.getSupplierName());
        createdSupplier.setStockName(supplierRequestModel.getStockName());
        createdSupplier.setStockType(supplierRequestModel.getStockType());
        createdSupplier.setStockQuantity(supplierRequestModel.getStockQuantity());
        createdSupplier.setStockWeight(supplierRequestModel.getStockWeight());
        createdSupplier.setUnitOfMeasurement(supplierRequestModel.getUnitOfMeasurement());
        createdSupplier.setEstimatedDeliveryDate(supplierRequestModel.getEstimatedDeliveryDate());
        createdSupplier.setCostOfStock(supplierRequestModel.getCostOfStock());
        createdSupplier.setStatus(supplierRequestModel.getStatus());

        when(supplierRepository.save(any(Supplier.class))).thenReturn(createdSupplier);

        mockMvc.perform(post("/supplier/createSupplierHistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(supplierRequestModel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.supplierName").value(createdSupplier.getSupplierName()))
                .andExpect(jsonPath("$.stockName").value(createdSupplier.getStockName()))
                .andExpect(jsonPath("$.stockType").value(createdSupplier.getStockType()))
                .andExpect(jsonPath("$.stockQuantity").value(createdSupplier.getStockQuantity()))
                .andExpect(jsonPath("$.stockWeight").value(createdSupplier.getStockWeight()))
                .andExpect(jsonPath("$.unitOfMeasurement").value(createdSupplier.getUnitOfMeasurement()))
                .andExpect(jsonPath("$.estimatedDeliveryDate").exists())
                .andExpect(jsonPath("$.costOfStock").value(createdSupplier.getCostOfStock()));
    }

    @Test
    void updateSupplierHistory() throws Exception {
        UUID supplierId = UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe");

        SupplierRequestModel supplierRequestModel = new SupplierRequestModel();
        supplierRequestModel.setSupplierName("Updated Supplier");
        supplierRequestModel.setStockName("Updated Stock");
        supplierRequestModel.setStockType("Updated Type");
        supplierRequestModel.setStockQuantity(200);
        supplierRequestModel.setStockWeight(64.87);
        supplierRequestModel.setUnitOfMeasurement("lbs");
        supplierRequestModel.setEstimatedDeliveryDate(new Date());
        supplierRequestModel.setDateDelivered(new Date());
        supplierRequestModel.setStockCondition("Excellent");
        supplierRequestModel.setDeliveryFeedback("Updated Delivery feedback");
        supplierRequestModel.setCostOfStock(456.78);
        supplierRequestModel.setStatus("Inactive");

        Supplier existingSupplier = new Supplier();
        existingSupplier.setSupplierId(supplierId);
        existingSupplier.setSupplierName("Supplier 1");
        existingSupplier.setStockName("Stock 1");
        existingSupplier.setStockType("Type 1");
        existingSupplier.setStockQuantity(100);
        existingSupplier.setStockWeight(32.53);
        existingSupplier.setUnitOfMeasurement("kg");
        existingSupplier.setEstimatedDeliveryDate(new Date());
        existingSupplier.setDateDelivered(new Date());
        existingSupplier.setStockCondition("Good");
        existingSupplier.setDeliveryFeedback("Delivery feedback");
        existingSupplier.setCostOfStock(123.45);
        existingSupplier.setStatus("Active");

        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setSupplierId(existingSupplier.getSupplierId());
        updatedSupplier.setSupplierName(supplierRequestModel.getSupplierName());
        updatedSupplier.setStockName(supplierRequestModel.getStockName());
        updatedSupplier.setStockType(supplierRequestModel.getStockType());
        updatedSupplier.setStockQuantity(supplierRequestModel.getStockQuantity());
        updatedSupplier.setStockWeight(supplierRequestModel.getStockWeight());
        updatedSupplier.setUnitOfMeasurement(supplierRequestModel.getUnitOfMeasurement());
        updatedSupplier.setEstimatedDeliveryDate(supplierRequestModel.getEstimatedDeliveryDate());
        updatedSupplier.setDateDelivered(supplierRequestModel.getDateDelivered());
        updatedSupplier.setStockCondition(supplierRequestModel.getStockCondition());
        updatedSupplier.setDeliveryFeedback(supplierRequestModel.getDeliveryFeedback());
        updatedSupplier.setCostOfStock(supplierRequestModel.getCostOfStock());
        updatedSupplier.setStatus(supplierRequestModel.getStatus());

        when(supplierRepository.findBySupplierId(supplierId)).thenReturn(existingSupplier);
        when(supplierRepository.save(any(Supplier.class))).thenReturn(updatedSupplier);

        mockMvc.perform(put("/supplier/updateSupplierHistory?supplierId={supplierId}", supplierId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(supplierRequestModel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.supplierId").value(updatedSupplier.getSupplierId().toString()))
                .andExpect(jsonPath("$.supplierName").value(updatedSupplier.getSupplierName()))
                .andExpect(jsonPath("$.stockName").value(updatedSupplier.getStockName()))
                .andExpect(jsonPath("$.stockType").value(updatedSupplier.getStockType()))
                .andExpect(jsonPath("$.stockQuantity").value(updatedSupplier.getStockQuantity()))
                .andExpect(jsonPath("$.stockWeight").value(updatedSupplier.getStockWeight()))
                .andExpect(jsonPath("$.unitOfMeasurement").value(updatedSupplier.getUnitOfMeasurement()))
                .andExpect(jsonPath("$.estimatedDeliveryDate").exists())
                .andExpect(jsonPath("$.dateDelivered").exists())
                .andExpect(jsonPath("$.stockCondition").value(updatedSupplier.getStockCondition()))
                .andExpect(jsonPath("$.deliveryFeedback").value(updatedSupplier.getDeliveryFeedback()))
                .andExpect(jsonPath("$.costOfStock").value(updatedSupplier.getCostOfStock()))
                .andExpect(jsonPath("$.status").value(updatedSupplier.getStatus()));
    }

    @Test
    void deleteSupplierHistory() throws Exception{
        UUID supplierId = UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe");

        // Perform the DELETE request
        mockMvc.perform(delete("/supplier/deleteSupplierHistory")
                        .param("supplierId", supplierId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Supplier history deleted successfully!"));
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