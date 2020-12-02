package com.example.demo.repository;

import com.example.demo.domain.Location;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Repository
public interface LocationRepository extends JpaRepository<Location,Integer> {
	//받아온 날짜 +2일까지로 수정
	@Query(value = "select * FROM location where user_id= :user_id and created_at between  date_format(date_add(:Date,interval 0 day),'%Y-%m-%d') and date_format(date_add(:Date,interval 2 day),'%Y-%m-%d');",nativeQuery = true)
    public List<Location> getLocationsByUserIdAndCreated_atEquals(@Param("user_id")String uesr_id,@Param("Date")Timestamp created_at);

    @Query(value = "select * FROM location where date_format(created_at, '%Y-%m-%d')=date_format(?1, '%Y-%m-%d') and user_id!=?2",nativeQuery = true)
    public List<Location> getLocationsByCreated_atEqualsAndUserIdIsNot(Timestamp date,String uid);

    @Query(value = "select * from location " +
            "where created_at between date_format(date_add(?1,interval -2 day),'%Y-%m-%d') and date_format(date_add(?1,interval 3 day),'%Y-%m-%d') " +
            "order by user_id"
            ,nativeQuery = true)
    public List<Location> getLocationsByCreated_atBetween(Date date);

    @Query(value = "select * from location " +
            "where user_id=?1 and created_at between date_format(date_add(?2,interval -2 day),'%Y-%m-%d') and date_format(date_add(?2,interval 3 day),'%Y-%m-%d') " +
            "order by user_id"
            ,nativeQuery = true)
    public List<Location> getLocationsByUserIdAndCreated_atBetween(String uid,Date date);


}

