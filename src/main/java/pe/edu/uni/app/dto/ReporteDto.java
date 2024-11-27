package pe.edu.uni.app.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor	
public class ReporteDto {
	
	 	private int idReparacion;
	    private String fecha;
	    private String tecnico;
	    private String descripcion;
	    private double costoTotal;

}
