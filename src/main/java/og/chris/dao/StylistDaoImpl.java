package og.chris.dao;

import og.chris.dao.dto.Contract;
import og.chris.dao.dto.ContractRowMapper;
import og.chris.entitites.SalonRowMapper;
import og.chris.entitites.Stylist;
import og.chris.entitites.StylistRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StylistDaoImpl implements StylistDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from stylists", Integer.class);
    }

    @Override
    public List<Stylist> findAllInSalon(int salonId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("salon0_id", salonId);
        return namedParameterJdbcTemplate.query("select * from stylists where salon_id = :salon0_id",
                mapSqlParameterSource, new StylistRowMapper());
    }

    @Override
    public Optional<Stylist> findById(int id) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", id);
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    "select * from stylists where stylist_id = :id",
                    mapSqlParameterSource, new StylistRowMapper()));
        }catch(EmptyResultDataAccessException exception){
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteById(int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        return namedParameterJdbcTemplate.update(
                "delete from stylists where stylist_id = :id",
                mapSqlParameterSource) == 1;
    }

    @Override
    public void save(Stylist stylist) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", stylist.getStylistId());
        mapSqlParameterSource.addValue("name", stylist.getStylistName());
        mapSqlParameterSource.addValue("salon_id", stylist.getSalonId());
        mapSqlParameterSource.addValue("phone", stylist.getPhone());
        mapSqlParameterSource.addValue("salary", stylist.getSalary());
        String SQL = "insert into stylists(stylist_id, stylist_name, stylist_phone, stylist_salary, salon_id) " +
                "values(:id, :name, :phone, :salary, :salon_id)";
        namedParameterJdbcTemplate.update(SQL, mapSqlParameterSource);
    }

    @Override
    public boolean moveStylist(int stylistId, int salonId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("stylist_id", stylistId);
        mapSqlParameterSource.addValue("salon_id", salonId);
        String SQL = "update stylists set salon_id = :salon_id where stylist_id = :stylist_id";
        return namedParameterJdbcTemplate.update(SQL, mapSqlParameterSource) == 1;
    }

    @Override
    public int averageStylistSalary(int salonId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("salon0_id", salonId);
        List<Stylist> s = namedParameterJdbcTemplate.query("select * from stylists where salon_id = :salon0_id",
                mapSqlParameterSource, new StylistRowMapper());
        int size = s.size();
        int counter = 0;
        for (Stylist stylist : s) {
            counter += stylist.getSalary();
        }
        counter = counter / size;
        return counter;
    }

    @Override
    public List<Contract> findAllWithSalon() {
        String SQL = "select sal.salon_name, styl.stylist_id from stylists styl inner join salons sal on sal.salon_id = styl.salon_id";
        return namedParameterJdbcTemplate.getJdbcTemplate().query(SQL, new ContractRowMapper());
    }
}
