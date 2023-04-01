package sp4.controllers;

import sp4.common.models.AGVStatus;
import sp4.common.models.AssmStatus;
import sp4.common.models.WHStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {


    @GetMapping("/status/agv")
    public ResponseEntity<AGVStatus> getAGVStatus()
    {
       return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
    }

    @GetMapping("/status/warehouse")
    public ResponseEntity<WHStatus> getWHStatus()
    {
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
    }

    @GetMapping("/status/assembler")
    public ResponseEntity<AssmStatus> getAssmStatus()
    {
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
    }

}
