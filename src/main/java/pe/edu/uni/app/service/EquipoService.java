package pe.edu.uni.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.app.dto.EquipoDto;

import java.util.List;

@Service
public class EquipoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<EquipoDto> listarEquiposPorCliente(int idCliente) {
        String sql = """
            SELECT marca, modelo, numero_serie
            FROM reparacion
            WHERE id_cliente = ?;
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            EquipoDto equipo = new EquipoDto();
            equipo.setMarca(rs.getString("marca"));
            equipo.setModelo(rs.getString("modelo"));
            equipo.setNumero_serie(rs.getString("numero_serie"));
            return equipo;
        }, idCliente);
    }
}
