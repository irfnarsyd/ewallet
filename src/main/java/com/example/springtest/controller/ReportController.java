package com.example.springtest.controller;


import com.example.springtest.dto.GetReportObjectDTO;
import com.example.springtest.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;


@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService service;

    @GetMapping("/getreport/{date}")
    public ResponseEntity<Object> getReport(@PathVariable LocalDate date){
        return new ResponseEntity<>(new GetReportObjectDTO(service.getReport(date)),HttpStatus.OK);
    }
}
