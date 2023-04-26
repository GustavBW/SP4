package g7.sp4.controllers;

import g7.sp4.common.models.AGVStatus;
import g7.sp4.common.models.AssmStatus;
import g7.sp4.common.models.WHStatus;
import g7.sp4.protocolHandling.AGVConnectionService;
import g7.sp4.protocolHandling.AssmConnectionService;
import g7.sp4.protocolHandling.WHConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
    @Autowired
    private WHConnectionService whService;
    @Autowired
    private AGVConnectionService agvService;
    @Autowired
    private AssmConnectionService assmService;


    @GetMapping("/status/agv")
    public ResponseEntity<AGVStatus> getAGVStatus()
    {
       return new ResponseEntity<>(agvService.getStatus(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/status/warehouse")
    public ResponseEntity<WHStatus> getWHStatus() throws Exception {
        return new ResponseEntity<>(whService.getStatus(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/status/assembler")
    public ResponseEntity<AssmStatus> getAssmStatus()
    {
        return new ResponseEntity<>(assmService.getStatus(), HttpStatusCode.valueOf(200));
    }

}
