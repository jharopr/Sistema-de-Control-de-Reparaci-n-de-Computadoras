package pe.edu.uni.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.uni.app.dto.PagoDto;
import pe.edu.uni.app.service.PagoService;

@RestController
@RequestMapping("/pagos")
public class PagoRest {

    @Autowired
    private PagoService pagoService;

    // Endpoint para registrar un pago
    @PostMapping("/registrar")
    public ResponseEntity<String> registrarPago(@RequestBody PagoDto pagoDto) {
        try {
            pagoService.registrarPago(pagoDto);
            return ResponseEntity.ok("Pago registrado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para consultar el saldo pendiente de una reparación
    @GetMapping("/saldo/{idReparacion}")
    public ResponseEntity<Double> consultarSaldo(@PathVariable int idReparacion) {
        try {
            double saldo = pagoService.solicitarSaldo(idReparacion);
            return ResponseEntity.ok(saldo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}