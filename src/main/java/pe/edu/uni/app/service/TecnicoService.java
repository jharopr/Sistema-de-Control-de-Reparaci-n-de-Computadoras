package pe.edu.uni.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.app.dto.TecnicoDto;
@Service
public class TecnicoService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public TecnicoDto registrarTecnico(TecnicoDto bean) {
		//registrando tecnico 
		String sql = """
				select * from empleado 
				insert into EMPLEADO (nombre, email, telefono, clave)
				values (? , ? , ? , ? )
				""";
		Object[] datos = {bean.getNombre(), bean.getEmail(), bean.getTelefono(), bean.getClave()};
		jdbcTemplate.update(sql, datos);
		// Finalizar
		return bean;
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public TecnicoDto actualizarTecnico(int id, TecnicoDto bean) {
		//validaciones 
		validarEmpleado(id);
		//Actualizando tecnico
		String sql ="""
				UPDATE EMPLEADO
				SET nombre = ?, 
				    email = ?,
				    telefono = ?,
				    clave = ?
				    
				WHERE id_empleado = ?;
				""";
		Object[] datos = {bean.getNombre(), bean.getEmail(), bean.getTelefono(), bean.getClave(), id};
		jdbcTemplate.update(sql, datos);
		//finalizando
		return bean;
	}
	
	
	
	private void validarEmpleado(int id) {
		// TODO Auto-generated method stub
				String sql = """
						select count(1) count from empleado where id_empleado = ?;
						""";
				int cont = jdbcTemplate.queryForObject(sql, Integer.class, id); //codigo sql, clase , parametro
				if(cont != 1 ) {
					throw new RuntimeException(" Empleado " + id +" no existe" );
				}
	}
	
}
