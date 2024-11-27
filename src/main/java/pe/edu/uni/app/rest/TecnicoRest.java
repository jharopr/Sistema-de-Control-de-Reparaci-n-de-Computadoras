package pe.edu.uni.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.uni.app.dto.TecnicoDto;
import pe.edu.uni.app.service.TecnicoService;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoRest {

    @Autowired
    private TecnicoService tecnicoService;

    // Endpoint para registrar un técnico
    @PostMapping("/registrar")
    public ResponseEntity<TecnicoDto> registrarTecnico(@RequestBody TecnicoDto tecnicoDto) {
        try {
            TecnicoDto tecnicoRegistrado = tecnicoService.registrarTecnico(tecnicoDto);
            return ResponseEntity.ok(tecnicoRegistrado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Endpoint para actualizar un técnico
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<TecnicoDto> actualizarTecnico(@PathVariable int id, @RequestBody TecnicoDto tecnicoDto) {
        try {
            TecnicoDto tecnicoActualizado = tecnicoService.actualizarTecnico(id, tecnicoDto);
            return ResponseEntity.ok(tecnicoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}