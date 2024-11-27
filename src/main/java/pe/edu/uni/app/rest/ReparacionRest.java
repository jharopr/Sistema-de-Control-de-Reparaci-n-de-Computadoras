package pe.edu.uni.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.uni.app.dto.ReparacionDto;
import pe.edu.uni.app.service.ReparacionService;

@RestController
@RequestMapping("/reparaciones")
public class ReparacionRest {

    @Autowired
    private ReparacionService reparacionService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarReparacion(@RequestBody ReparacionDto reparacionDto) {
    	try {
			reparacionDto = reparacionService.registrarReparacion(reparacionDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(reparacionDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error al registrar la reparacion" + e.getMessage());
		}
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ReparacionDto> actualizarReparacion(@PathVariable int id, @RequestBody ReparacionDto reparacionDto) {
        try {
            ReparacionDto reparacionActualizada = reparacionService.actualizarReparacion(id, reparacionDto);
            return ResponseEntity.ok(reparacionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/asignar-tecnico/{id}/{idTecnico}")
    public ResponseEntity<String> asignarTecnico(@PathVariable int id, @PathVariable int idTecnico) {
        try {
            reparacionService.asignarTecnico(id, idTecnico);
            return ResponseEntity.ok("Técnico asignado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/agregar-costos/{id}")
    public ResponseEntity<String> agregarCostos(@PathVariable int id, @RequestBody ReparacionDto reparacionDto) {
        try {
            reparacionService.agregarCostos(id, reparacionDto);
            return ResponseEntity.ok("Costos actualizados correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/calcular-costo-total/{id}")
    public ResponseEntity<Double> calcularCostoTotal(@PathVariable int id) {
        try {
            double total = reparacionService.calcularCostoTotal(id);
            return ResponseEntity.ok(total);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/notificar-cliente/{id}")
    public ResponseEntity<String> notificarCliente(@PathVariable int id) {
        try {
            reparacionService.notificarCliente(id);
            return ResponseEntity.ok("Notificación enviada al cliente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
