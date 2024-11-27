package pe.edu.uni.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import pe.edu.uni.app.dto.ReporteDto;
@Service
public class ReporteService {
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	// Generar reporte de reparaciones por técnico
	public List<ReporteDto> generarReportePorTecnico(int idTecnico) {
        String sql = """
               SELECT r.id_reparacion, r.fecha_entrega_estimada, t.nombre AS tecnico, r.descripcion_problema, r.total
                FROM REPARACION r
                JOIN EMPLEADO t ON r.id_empleado = t.id_empleado
                WHERE t.id_empleado = 1
                ORDER BY r.fecha_entrega_estimada;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReporteDto(
                rs.getInt("id_reparacion"),
                rs.getString("fecha"),
                rs.getString("tecnico"),
                rs.getString("descripcion"),
                rs.getDouble("costo_total")
        ), idTecnico);
    }
	
	
	// Generar reporte de costos totales
	public double generarReporteCostosTotales() {
        String sql = """
                SELECT SUM(total) AS total_costos
                FROM REPARACION;
                """;

        return jdbcTemplate.queryForObject(sql, Double.class);
    }
}
