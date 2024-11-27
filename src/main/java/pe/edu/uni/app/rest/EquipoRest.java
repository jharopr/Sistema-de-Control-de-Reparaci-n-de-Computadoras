package pe.edu.uni.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.uni.app.dto.EquipoDto;
import pe.edu.uni.app.service.EquipoService;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class EquipoRest {

    @Autowired
    private EquipoService equipoService;

    

    // Endpoint para listar equipos por cliente
    @GetMapping("/listar-por-cliente/{idCliente}")
    public ResponseEntity<List<EquipoDto>> listarEquiposPorCliente(@PathVariable int idCliente) {
        try {
            List<EquipoDto> equipos = equipoService.listarEquiposPorCliente(idCliente);
            return ResponseEntity.ok(equipos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
