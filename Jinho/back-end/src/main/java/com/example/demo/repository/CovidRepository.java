package com.example.demo.repository;

import com.example.demo.domain.Covid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CovidRepository extends JpaRepository<Covid,Integer> {

	
	
	@Query(value = "select * from covid where covid_id= :c_id and created_at between date_format(date_add(:Date,interval -2 day),'%Y-%m-%d') and date_format(date_add(:Date,interval 3 day),'%Y-%m-%d')"
			,nativeQuery = true)
    public List<Covid> getCovidByCovidIdAndCreated_atBetween(@Param("c_id")int covid_id,@Param("Date")Timestamp created_at);
}
