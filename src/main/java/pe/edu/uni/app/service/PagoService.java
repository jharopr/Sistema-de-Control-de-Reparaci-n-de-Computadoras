package pe.edu.uni.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.app.dto.PagoDto;
@Service
public class PagoService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public PagoDto registrarPago(PagoDto bean) {
		//Validaciones
		int idReparacion = validarReparacion(bean.getIdReparacion());
		int Metodo = validarMetodo(bean.getIdMetodo());
		
		
		//procesando el pago
		 String sql = """
				select * from PAGO
				insert into pago (id_reparacion, id_metodo, fecha, importe)
				values ( ?, ?, ? , GETDATE() , ?  )
				""";
		Object[] datos = { bean.getIdReparacion(), bean.getIdMetodo(), bean.getImporte()};
		jdbcTemplate.update(sql, datos);
		// Finalizar
		return bean;
	}
	
	
	public double solicitarSaldo(int idReparacion) {
		String sql = "select saldo from REPARACION where id_reparacion = ? ";
		double saldo = jdbcTemplate.queryForObject(sql, Integer.class);
		
		return saldo;
	}

	
	
	
	private int validarMetodo(int idMetodo) {
		// TODO Auto-generated method stub
		String sql = """
				select count(1) count from metodo
				where id_metodo= ?
				""";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, idMetodo); //codigo sql, clase , parametro
		if(cont != 1 ) {
			throw new RuntimeException(" Metodo de pago no existe");
		}
		
		
		return idMetodo;
	}

	private int validarReparacion(int idReparacion) {
		// TODO Auto-generated method stub
		String sql = """
				select count(1) count from REPARACION
				where id_reparacion = ?
				""";
		int cont = jdbcTemplate.queryForObject(sql, Integer.class, idReparacion); //codigo sql, clase , parametro
		if(cont != 1 ) {
			throw new RuntimeException(" Esa reparacion no existe");
		}
		
		
		return idReparacion;
	}
}
