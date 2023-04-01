package sp4.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WarehouseController {

    @GetMapping("/warehouse/categories")
    public ResponseEntity<String[]> getCategories()
    {
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
    }

    @GetMapping("/warehouse/inventory")
    public ResponseEntity<Map<String,Integer>> getInventory()
    {
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
    }

}
