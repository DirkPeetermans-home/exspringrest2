package be.abis.exercise.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import be.abis.exercise.model.Course;

@Repository
@Profile("production")
public class JDBCCourseRepository implements CourseRepository {

	private JdbcOperations operations;
	
	@Autowired
	public JDBCCourseRepository(JdbcOperations operations) {
		this.operations=operations;
	}


	@Override
	public List<Course> findAllCourses() {
		return operations.query("select * from courses",new CourseRowMapper());
	}

	@Override
	public Course findCourse(int id) {
		Course courseFound =null;
		try {
			courseFound=operations.queryForObject("select * from courses where cid=?", new CourseRowMapper(),id);
		} catch (EmptyResultDataAccessException erdae){
			courseFound=null;
		}
		return courseFound;
	}

	@Override
	public Course findCourse(String shortTitle) {
		Course courseFound =null;
		try {
			courseFound = operations.queryForObject("select * from courses where cstitle=?", new CourseRowMapper(),shortTitle);
		} catch (EmptyResultDataAccessException erdae){
			courseFound=null;
		}
		return courseFound;
	}
	
	private static final class CourseRowMapper implements RowMapper<Course> {

		@Override
		public Course mapRow(ResultSet rs, int arg1) throws SQLException {
			return new Course(rs.getString("cid"),
					           rs.getString("cstitle"), 
					           rs.getString("cltitle"),
					           rs.getInt("caprice"),
					           rs.getDouble("cdur"));
		}
		
	}

}
