package pe.edu.uni.app.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor	
public class PagoDto {
	
	int idReparacion;
	int idMetodo;
	double importe;
	
	
}
