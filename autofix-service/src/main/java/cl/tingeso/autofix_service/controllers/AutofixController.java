package cl.tingeso.autofix_service.controllers;

import cl.tingeso.autofix_service.services.AutofixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/reportes")
public class AutofixController {
    @Autowired
    private AutofixService autofixService;

    @GetMapping("/reporte1")
    public ArrayList<ArrayList<Integer>> generarReporte1(@RequestParam("month") String month, @RequestParam("year") String year){
        return autofixService.reporte1(month, year);
    }

    @GetMapping("/reporte2")
    public ArrayList<ArrayList<Integer>> generarReporte2(@RequestParam("month") String month, @RequestParam("year") String year){
        return autofixService.reporte2(month, year);
    }
}
