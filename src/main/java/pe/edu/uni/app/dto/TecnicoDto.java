package pe.edu.uni.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor	
public class TecnicoDto {
	
	String nombre;
	String email;
	String telefono;
	String clave;
}
