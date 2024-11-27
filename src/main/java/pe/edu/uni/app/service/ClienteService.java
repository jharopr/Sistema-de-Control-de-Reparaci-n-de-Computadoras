package pe.edu.uni.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.app.dto.ClienteDto;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ClienteDto registrarCliente(ClienteDto bean) {
        

        String sql = """
            INSERT INTO cliente ( nombre, email, telefono)
            VALUES ( ?, ?, ?);
        """;
        Object[] datos = {bean.getNombre(), bean.getEmail(), bean.getTelefono()};
        jdbcTemplate.update(sql, datos);
        return bean;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ClienteDto actualizarCliente(int id, ClienteDto bean) {
        String sql = """
            UPDATE cliente
            SET nombre = ?, email = ?, telefono = ?
            WHERE id_cliente = ?;
        """;
        Object[] datos = {bean.getNombre(), bean.getEmail(), bean.getTelefono(), id};
        jdbcTemplate.update(sql, datos);
        return bean;
    }

    public ClienteDto buscarClientePorId(int id) {
        String sql = """
            SELECT nombre, email, telefono
            FROM cliente
            WHERE id_cliente = ?;
        """;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            ClienteDto cliente = new ClienteDto();
            cliente.setNombre(rs.getString("nombre"));
            cliente.setEmail(rs.getString("email"));
            cliente.setTelefono(rs.getString("telefono"));
            return cliente;
        }, id);
    }

    public List<ClienteDto> listarClientes() {
        String sql = """
            SELECT nombre, email, telefono
            FROM cliente;
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ClienteDto cliente = new ClienteDto();
            cliente.setNombre(rs.getString("nombre"));
            cliente.setEmail(rs.getString("email"));
            cliente.setTelefono(rs.getString("telefono"));
            return cliente;
        });
    }
}
