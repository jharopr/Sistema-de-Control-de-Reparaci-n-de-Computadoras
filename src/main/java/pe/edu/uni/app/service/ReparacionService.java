package pe.edu.uni.app.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.app.dto.ReparacionDto;

@Service
public class ReparacionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ReparacionDto registrarReparacion(ReparacionDto reparacionDto) {
    	//validaciones
    	validarCliente(reparacionDto.getIdCliente());
    	
    	double importe = reparacionDto.getCostoManoObra() + reparacionDto.getCostoReparacion();
    	double impuesto = importe * 0.18;
    	double total = importe + impuesto;
    	Timestamp fechaentrega;
    	double saldo = total - reparacionDto.getAdelanto();
    	//Registrando
    	fechaentrega = Timestamp.valueOf(reparacionDto.getFechaEntregaEstimada());
        String sql = """
                INSERT INTO REPARACION (
                    id_cliente, id_empleado, id_estado, fecha_inicio, fecha_entrega_estimada, marca, modelo,
                    numero_serie, descripcion_problema, costo_mano_obra, costo_reparacion, importe,
                    impuesto, total, adelanto, saldo
                ) VALUES (?, NULL, 1, GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        
        jdbcTemplate.update(sql, reparacionDto.getIdCliente(),fechaentrega,
                reparacionDto.getMarca(), reparacionDto.getModelo(), reparacionDto.getNumeroSerie(),
                reparacionDto.getDescripcionProblema(), reparacionDto.getCostoManoObra(),
                reparacionDto.getCostoReparacion(), importe, impuesto, total,reparacionDto.getAdelanto(), saldo);
        
        reparacionDto.setIdEstado(1);
        reparacionDto.setImporte(importe);
        reparacionDto.setImpuesto(impuesto);
        reparacionDto.setTotal(total);
        return reparacionDto;
    }

  
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ReparacionDto actualizarReparacion(int id, ReparacionDto reparacionDto) {
		//Validaciones
		validarIdReparacion(id);
		validarEstado(reparacionDto.getIdEstado());
		
		//----------------------------------------------------------------
        String sql = """
                UPDATE REPARACION
                SET id_estado = ?, descripcion_problema = ?, costo_mano_obra = ?, costo_reparacion = ?
                WHERE id_reparacion = ?
            """;
        jdbcTemplate.update(sql, reparacionDto.getIdEstado(), reparacionDto.getDescripcionProblema(),
                reparacionDto.getCostoManoObra(), reparacionDto.getCostoReparacion(), id);

        return reparacionDto;
    }

    private void validarIdReparacion(int idReparacion) {
		// TODO Auto-generated method stub
    			String sql = """
    					select count(1) count from ESTADO_REPARACION where id_reparacion = ?;
    					""";
    			int cont = jdbcTemplate.queryForObject(sql, Integer.class, idReparacion); //codigo sql, clase , parametro
    			if(cont != 1 ) {
    				throw new RuntimeException(" Nro de estado no existe");
    			}
	}


	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void asignarTecnico(int id, int idTecnico) {
    	//validaciones
		validarIdReparacion(id);
    	validarEmpleado(idTecnico);
    	//----------------------
        String sql = """
                UPDATE REPARACION
                SET id_empleado = ?
                WHERE id_reparacion = ?
            """;
        jdbcTemplate.update(sql, idTecnico, id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void agregarCostos(int id, ReparacionDto reparacionDto) {
    	//Validaciones
    	validarIdReparacion(id);
    	double importe = reparacionDto.getCostoManoObra() + reparacionDto.getCostoReparacion();
    	double impuesto = importe * 0.18;
    	double total = importe + impuesto;
    	//
        String sql = """
                UPDATE REPARACION
                SET costo_mano_obra = ?, costo_reparacion = ?,importe = ?, impuesto = ?, total = ?
                WHERE id_reparacion = ?
            """;
        jdbcTemplate.update(sql, reparacionDto.getCostoManoObra(), reparacionDto.getCostoReparacion(),
                importe, impuesto,total , id);
    }

    public double calcularCostoTotal(int id) {
    	//validaciones
    	validarIdReparacion(id);
    	//----------------------------------------------------
        String sql = """
                SELECT costo_mano_obra + costo_reparacion + impuesto
                FROM REPARACION
                WHERE id_reparacion = ?
            """;
        return jdbcTemplate.queryForObject(sql, Double.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void notificarCliente(int id) {
    	//validacioens
    	validarIdReparacion(id);
        // Implementar lógica de notificación (correo, SMS, etc.)
        String sql = """
                SELECT email
                FROM CLIENTE c
                INNER JOIN REPARACION r ON c.id_cliente = r.id_cliente
                WHERE r.id_reparacion = ?
            """;
        String email = jdbcTemplate.queryForObject(sql, String.class, id);

        // Suponiendo que enviamos un correo
        System.out.println("Notificación enviada a " + email);
    }
    


	private void validarEstado(int idEstado) {
		// TODO Auto-generated method stub
		String sql = """
				select count(1) count from ESTADO_REPARACION where id_estado = ?;
				""";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, idEstado); //codigo sql, clase , parametro
		if(cont != 1 ) {
			throw new RuntimeException(" Nro de estado no existe");
		}
	}
	
	
	private void validarCliente(int idCliente) {
		// TODO Auto-generated method stub
		String sql = """
				select count(1) count from cliente where id_cliente = ?;
				""";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, idCliente); //codigo sql, clase , parametro
		if(cont != 1 ) {
			throw new RuntimeException(" CLiente"+ idCliente +" no existe");
		}
	}
	
	
	private void validarEmpleado(int idEmpleado) {
		// TODO Auto-generated method stub
		String sql = """
				select count(1) count from empleado where id_empleado = ?;
				""";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, idEmpleado); //codigo sql, clase , parametro
		if(cont != 1 ) {
			throw new RuntimeException(" Empleado " + idEmpleado +" no existe" );
		}
	}
}
