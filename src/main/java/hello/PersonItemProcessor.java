package hello;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);
    
    private Object dataOfPreviousStep;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
 
    @Override
    public Person process(final Person person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Person transformedPerson = new Person(firstName, lastName);
        log.info("Converting (" + person + ") into (" + transformedPerson + ")");
        
        
        
        return checkForduplicates(transformedPerson);
    }
    

    private Person checkForduplicates(Person transformedPerson) {
    	 String query = "SELECT first_name, last_name FROM people GROUP BY first_name, last_name HAVING COUNT(*)>1";

 		List<Person> results = jdbcTemplate.query(query, new RowMapper<Person>() {
 			@Override
 			public Person mapRow(ResultSet rs, int row) throws SQLException {
 				return new Person(rs.getString(1), rs.getString(2));
 			}
 		});
 		
 		if (!results.isEmpty()) {
 			log.info("Duplicated record (" + results.get(0) + ") ");
 			return null;
 		}
 		return transformedPerson;
	}


	public Object getDataOfPreviousStep() {
		return dataOfPreviousStep;
	}


	public void setDataOfPreviousStep(Object dataOfPreviousStep) {
		this.dataOfPreviousStep = dataOfPreviousStep;
	}

}
