package og.chris.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class SalonDaoImpl implements SalonDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public int count(){
        return jdbcTemplate.queryForObject("select count(*) from salons", Integer.class);
    }

}
