package pe.edu.uni.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.uni.app.dto.ClienteDto;
import pe.edu.uni.app.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteRest {

    @Autowired
    private ClienteService clienteService;

    // Endpoint para registrar un cliente
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarCliente(@RequestBody ClienteDto clienteDto) {
    	try {
			clienteDto = clienteService.registrarCliente(clienteDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(clienteDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error al registrar el cliente" + e.getMessage());
		}
    }

    // Endpoint para actualizar un cliente
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ClienteDto> actualizarCliente(@PathVariable int id, @RequestBody ClienteDto clienteDto) {
        try {
            ClienteDto clienteActualizado = clienteService.actualizarCliente(id, clienteDto);
            return ResponseEntity.ok(clienteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    // Endpoint para buscar un cliente por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ClienteDto> buscarClientePorId(@PathVariable int id) {
        try {
            ClienteDto cliente = clienteService.buscarClientePorId(id);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    // Endpoint para listar todos los clientes
    @GetMapping("/listar")
    public ResponseEntity<List<ClienteDto>> listarClientes() {
        try {
            List<ClienteDto> clientes = clienteService.listarClientes();
            return ResponseEntity.ok(clientes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
