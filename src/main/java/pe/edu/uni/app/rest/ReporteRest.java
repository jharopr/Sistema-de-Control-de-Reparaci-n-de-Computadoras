package pe.edu.uni.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.uni.app.dto.ReporteDto;
import pe.edu.uni.app.service.ReporteService;

import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteRest {

    @Autowired
    private ReporteService reporteService;

    // Endpoint para generar reporte por técnico
    @GetMapping("/tecnico/{idTecnico}")
    public ResponseEntity<List<ReporteDto>> generarReportePorTecnico(@PathVariable int idTecnico) {
        try {
            List<ReporteDto> reportes = reporteService.generarReportePorTecnico(idTecnico);
            return ResponseEntity.ok(reportes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Endpoint para obtener el costo total de todas las reparaciones
    @GetMapping("/costos/totales")
    public ResponseEntity<Double> generarReporteCostosTotales() {
        try {
            double totalCostos = reporteService.generarReporteCostosTotales();
            return ResponseEntity.ok(totalCostos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}