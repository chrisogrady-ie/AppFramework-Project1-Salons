package og.chris.entitites;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StylistRowMapper implements RowMapper<Stylist> {
    @Override
    public Stylist mapRow(ResultSet rs, int rowNum) throws SQLException {
        Stylist st = new Stylist();
        st.setStylistId(rs.getInt(1));
        st.setStylistName(rs.getString(2));
        st.setPhone(rs.getString(3));
        st.setSalary(rs.getInt(4));
        st.setSalonId(rs.getInt(5));
        return st;
    }
}
