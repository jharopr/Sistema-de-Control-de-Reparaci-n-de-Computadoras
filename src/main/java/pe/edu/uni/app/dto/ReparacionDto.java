package pe.edu.uni.app.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReparacionDto {
    private int idCliente;
    private int idEmpleado;
    private int idEstado;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private String descripcionProblema;
    private double costoManoObra;
    private double costoReparacion;
    private String fechaEntregaEstimada;
    // Atributos que solo se retornan
    private double importe;
    private double impuesto;
    private double total;
    
    private double adelanto;
    private double saldo;
    
}
