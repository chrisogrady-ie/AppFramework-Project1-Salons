package og.chris.entitites;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalonRowMapper implements RowMapper<Salon> {
    @Override
    public Salon mapRow(ResultSet rs, int rowNum) throws SQLException {
        Salon s = new Salon();
        s.setId(rs.getInt(1));
        s.setName(rs.getString(2));
        s.setAddress(rs.getString(3));
        s.setPhone(rs.getString(4));
        s.setDaysOpen(rs.getString(5));
        return s;
    }
}
