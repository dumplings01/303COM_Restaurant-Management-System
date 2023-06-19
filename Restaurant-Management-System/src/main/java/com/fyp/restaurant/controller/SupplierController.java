package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.Supplier;
import com.fyp.restaurant.model.SupplierRequestModel;
import com.fyp.restaurant.repository.SupplierRepository;
import com.fyp.restaurant.service.SystemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(path="/supplier")
@CrossOrigin("http://localhost:3000")
public class SupplierController {

    private final SupplierRepository supplierRepository;

    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    // get all supplier history
    @GetMapping("/getAllSupplierHistory")
    public Page<Supplier> getAllSupplierHistory(@RequestParam(defaultValue = "0") int currentPage)
    {
        Pageable pageable = PageRequest.of(currentPage, 10);
        return (Page<Supplier>) this.supplierRepository.findAll(pageable);
    }

    // get single supplier history
    @GetMapping("/getSingleSupplierHistory")
    public Supplier getSupplierProfile (@RequestParam(name="supplierId") UUID supplierId)
    {
        return this.supplierRepository.findBySupplierId(supplierId);
    }

    // create supplier profile
    @PostMapping("/createSupplierHistory")
    public ResponseEntity<?> createSupplierHistory(@Valid @RequestBody SupplierRequestModel supplier)
    {
        Supplier value = new SystemService().createSupplierHistory(supplier);

        supplierRepository.save(value);

        return new ResponseEntity<Supplier>(value, HttpStatus.OK);
    }

    // update supplier
    @PutMapping("/updateSupplierHistory")
    public ResponseEntity<?> updateSupplierHistory(@Valid @RequestParam(value="supplierId") UUID supplierId,
                                                   @RequestBody SupplierRequestModel supplierDetails)
    {
        Supplier updateSupplier = supplierRepository.findBySupplierId(supplierId);

        updateSupplier.setSupplierName(supplierDetails.getSupplierName());
        updateSupplier.setStockName(supplierDetails.getStockName());
        updateSupplier.setStockType(supplierDetails.getStockType());
        updateSupplier.setStockQuantity(supplierDetails.getStockQuantity());
        updateSupplier.setStockWeight(supplierDetails.getStockWeight());
        updateSupplier.setUnitOfMeasurement(supplierDetails.getUnitOfMeasurement());
        updateSupplier.setEstimatedDeliveryTime(supplierDetails.getEstimatedDeliveryTime());
        updateSupplier.setTimeDelivered(supplierDetails.getTimeDelivered());
        updateSupplier.setStockCondition(supplierDetails.getStockCondition());
        updateSupplier.setStockCost(supplierDetails.getStockCost());

        supplierRepository.save(updateSupplier);

        return new ResponseEntity<Supplier>(updateSupplier, HttpStatus.OK);

    }

    // delete supplier profile
    @DeleteMapping("/deleteSupplierHistory")
    public ResponseEntity<?> deleteSupplierHistory(@RequestParam(value="supplierId") UUID supplierId)
    {
        supplierRepository.deleteById(supplierId);
        return new ResponseEntity<>("Supplier history deleted successfully!", HttpStatus.OK);
    }
}
