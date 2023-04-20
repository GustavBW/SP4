package g7.sp4.controllers;

import g7.sp4.common.models.WHItem;
import g7.sp4.protocolHandling.WHConnectionService;
import g7.sp4.protocolHandling.WHConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class WarehouseController {

    @GetMapping("/warehouse/inventory")
    public ResponseEntity<List<WHItem>> getInventory()
    {

        return new ResponseEntity<>(whService.getInventory(), HttpStatusCode.valueOf(200));
    }
    @Autowired
   private WHConnectionService whService;

}
