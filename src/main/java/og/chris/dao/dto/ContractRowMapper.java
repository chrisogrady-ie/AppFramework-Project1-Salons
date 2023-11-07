package og.chris.dao.dto;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContractRowMapper implements RowMapper<Contract> {
    @Override
    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Contract(rs.getString(1), rs.getInt(2));
    }
}
