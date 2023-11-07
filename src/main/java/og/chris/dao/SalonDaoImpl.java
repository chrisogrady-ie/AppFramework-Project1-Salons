package og.chris.dao;

import og.chris.entitites.Salon;
import og.chris.entitites.SalonRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SalonDaoImpl implements SalonDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int count(){
        return jdbcTemplate.queryForObject("select count(*) from salons", Integer.class);
    }

    @Override
    public List<Salon> findAll() {
        return jdbcTemplate.query("select * from salons", new SalonRowMapper());
    }

    @Override
    public Optional<Salon> findById(int id) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", id);
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    "select * from salons where salon_id = :id",
                    mapSqlParameterSource, new SalonRowMapper()));
        }catch(EmptyResultDataAccessException exception){
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteById(int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        return namedParameterJdbcTemplate.update(
                "delete from salons where salon_id = :id",
                mapSqlParameterSource) == 1;
    }

    @Override
    public void save(Salon salon) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", salon.getId());
        mapSqlParameterSource.addValue("name", salon.getName());
        mapSqlParameterSource.addValue("address", salon.getAddress());
        mapSqlParameterSource.addValue("phone", salon.getPhone());
        mapSqlParameterSource.addValue("days_open", salon.getDaysOpen());
        String SQL = "insert into salons(salon_id, salon_name, salon_address, salon_phone, salon_days_open) " +
                "values(:id, :name, :address, :phone, :days_open)";
        namedParameterJdbcTemplate.update(SQL, mapSqlParameterSource);
    }

    @Override
    public boolean editName(String newName, int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("new_name", newName);
        mapSqlParameterSource.addValue("id", id);
        String SQL = "update salons set salon_name = :new_name where salon_id = :id";
        return namedParameterJdbcTemplate.update(SQL, mapSqlParameterSource) == 1;
    }

    @Override
    public boolean editDaysOpen(String daysOpen, int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("days_open", daysOpen);
        mapSqlParameterSource.addValue("id", id);
        String SQL = "update salons set salon_days_open = :days_open where salon_id = :id";
        return namedParameterJdbcTemplate.update(SQL, mapSqlParameterSource) == 1;
    }

    @Override
    public List<Salon> findSalonByName(String searchName) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", searchName);
        return namedParameterJdbcTemplate.query(
                "select * from salons where salon_name = :name",
                mapSqlParameterSource, new SalonRowMapper());

    }

    @Override
    public List<Salon> findSalonByNumDaysOpen(String numDays) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("days_open", numDays);
        return namedParameterJdbcTemplate.query(
                "select * from salons where salon_days_open = :days_open",
                mapSqlParameterSource, new SalonRowMapper());
    }


}
