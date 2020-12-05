package com.example.demo.repository;

import com.example.demo.domain.Covid;
import com.example.demo.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface CovidRepository extends JpaRepository<Covid,Integer> {

    @Query(value = "select * from covid where covid_num= ?1 and created_at between date_format(date_add(?2,interval -2 day),'%Y-%m-%d') and date_format(date_add(?2,interval 3 day),'%Y-%m-%d')"
            ,nativeQuery = true)
    public List<Covid> getCovidByCovid_numAndCreated_atBetween(int covid_num,Timestamp created_at);

    @Query(value = "select * from covid where created_at between date_format(date_add(?1,interval -2 day),'%Y-%m-%d') and date_format(date_add(?1,interval 3 day),'%Y-%m-%d')",nativeQuery = true)
    public List<Covid> getLocationsByCreated_atEquals(Timestamp created_at);
}
