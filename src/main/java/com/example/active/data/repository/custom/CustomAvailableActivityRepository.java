package com.example.active.data.repository.custom;

import com.example.active.data.entity.AvailableActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomAvailableActivityRepository {
    @Query(
            value = "SELECT * FROM (\n"+
                    "SELECT \n" +
                    "available_activity.id,\n" +
                    "available_activity.time,\n" +
                    "available_activity.available,\n" +
                    "available_activity.facility_reservation_id,\n" +
                    "available_activity.activity_id,\n" +
                    "available_activity.last_updated,\n" +
                    "ST_Distance_Sphere(POINT(lng, lat), POINT(:lng, :lat)) distance\n" +
                    "FROM available_activity available_activity\n" +
                    "JOIN activity activity \n" +
                    "ON available_activity.activity_id=activity.id\n" +
                    "JOIN facility_reservation facility_reservation \n" +
                    "ON available_activity.facility_reservation_id=facility_reservation.id\n" +
                    "JOIN facility facility\n" +
                    "ON facility.id=facility_reservation.facility_id\n" +
                    "WHERE activity.title like CONCAT('%',:query,'%')\n"+
                    "AND available_activity.available = COALESCE(:available, available_activity.available)\n" +
                    "AND available_activity.time > CURRENT_TIME()\n" +
                    ") available_activity",
            countQuery = "SELECT COUNT(*) FROM\n"+
                    "(SELECT\n" +
                    "available_activity.id,\n" +
                    "(:lat + :lng) distance\n" +
                    "FROM available_activity available_activity\n" +
                    "JOIN activity activity \n" +
                    "ON available_activity.activity_id=activity.id\n" +
                    "WHERE activity.title like CONCAT('%',:query,'%')\n" +
                    "AND available_activity.available = COALESCE(:available, available_activity.available)\n" +
                    "AND available_activity.time > CURRENT_TIME()\n" +
                    ") A\n",
            nativeQuery = true)
    Page<AvailableActivity> findAll(Pageable pageable,
                                    @Param("query") String query,
                                    @Param("available") Boolean available,
                                    @Param("lat") Double lat,
                                    @Param("lng") Double lng);

    @Query(
            value = "SELECT * FROM (\n"+
                    "SELECT\n" +
                    "available_activity.id,\n" +
                    "available_activity.time,\n" +
                    "available_activity.available,\n" +
                    "available_activity.facility_reservation_id,\n" +
                    "available_activity.activity_id,\n" +
                    "available_activity.last_updated,\n" +
                    "activity.title title,\n" +
                    "ST_Distance_Sphere(POINT(lng, lat), POINT(:lng, :lat)) distance\n" +
                    "FROM available_activity available_activity\n" +
                    "JOIN activity activity \n" +
                    "ON available_activity.activity_id=activity.id\n" +
                    "JOIN type type\n" +
                    "ON type.id=activity.type_id\n" +
                    "JOIN category category\n" +
                    "ON category.id=type.category_id\n"+
                    "JOIN facility_reservation facility_reservation\n" +
                    "ON available_activity.facility_reservation_id=facility_reservation.id\n" +
                    "JOIN facility facility\n" +
                    "ON facility.id=facility_reservation.facility_id\n" +
                    "WHERE activity.title like CONCAT('%',:query,'%')\n" +
                    "AND category.keyy like :category\n" +
                    "AND available_activity.available = COALESCE(:available, available_activity.available)\n" +
                    "AND available_activity.time > CURRENT_TIME()\n" +
                    ") available_activity",
            countQuery = "SELECT COUNT(*) FROM\n"+
                    "(SELECT\n" +
                    "available_activity.id,\n" +
                    "(:lat + :lng) distance\n" +
                    "FROM available_activity available_activity\n" +
                    "JOIN activity activity\n" +
                    "ON available_activity.activity_id=activity.id\n" +
                    "JOIN type type\n" +
                    "ON type.id=activity.type_id\n" +
                    "JOIN category category\n" +
                    "ON category.id=type.category_id\n"+
                    "WHERE activity.title like CONCAT('%',:query,'%')\n" +
                    "AND category.keyy like :category" +
                    "AND available_activity.available = COALESCE(:available, available_activity.available)\n" +
                    "AND available_activity.time > CURRENT_TIME()\n" +
                    ") A\n",
            nativeQuery = true)
    Page<AvailableActivity> findByCategory(Pageable pageable,
                                           @Param("query") String query,
                                           @Param("category") String category,
                                           @Param("available") Boolean available,
                                           @Param("lat") Double lat,
                                           @Param("lng") Double lng);

    @Query(
            value = "SELECT * FROM (\n"+
                    "SELECT\n" +
                    "available_activity.id,\n" +
                    "available_activity.time,\n" +
                    "available_activity.available,\n" +
                    "available_activity.facility_reservation_id,\n" +
                    "available_activity.activity_id,\n" +
                    "available_activity.last_updated,\n" +
                    "activity.title title,\n" +
                    "ST_Distance_Sphere(POINT(lng, lat), POINT(:lng, :lat)) distance\n" +
                    "FROM available_activity available_activity\n" +
                    "JOIN activity activity \n" +
                    "ON available_activity.activity_id=activity.id\n" +
                    "JOIN type type\n" +
                    "ON type.id=activity.type_id\n" +
                    "JOIN category category\n" +
                    "ON category.id=type.category_id\n"+
                    "JOIN facility_reservation facility_reservation\n" +
                    "ON available_activity.facility_reservation_id=facility_reservation.id\n" +
                    "JOIN facility facility\n" +
                    "ON facility.id=facility_reservation.facility_id\n" +
                    "WHERE activity.title like CONCAT('%',:query,'%')\n" +
                    "AND category.keyy like :category\n" +
                    "AND type.keyy like :type\n" +
                    "AND available_activity.available = COALESCE(:available, available_activity.available)\n" +
                    "AND available_activity.time > CURRENT_TIME()\n" +
                    ") available_activity",
            countQuery = "SELECT COUNT(*) FROM\n"+
                    "(SELECT\n" +
                    "available_activity.id,\n" +
                    "(:lat + :lng) distance\n" +
                    "FROM available_activity available_activity\n" +
                    "JOIN activity activity\n" +
                    "ON available_activity.activity_id=activity.id\n" +
                    "JOIN type type\n" +
                    "ON type.id=activity.type_id\n" +
                    "JOIN category category\n" +
                    "ON category.id=type.category_id\n"+
                    "WHERE activity.title like CONCAT('%',:query,'%')\n" +
                    "AND category.keyy like :category\n" +
                    "AND type.keyy like :type\n" +
                    "AND available_activity.available = COALESCE(:available, available_activity.available)\n" +
                    "AND available_activity.time > CURRENT_TIME()\n" +
                    ") A\n",
            nativeQuery = true)
    Page<AvailableActivity> findByCategoryAndType(Pageable pageable,
                                                  @Param("query") String query,
                                                  @Param("category") String category,
                                                  @Param("available") Boolean available,
                                                  @Param("type") String type,
                                                  @Param("lat") Double lat,
                                                  @Param("lng") Double lng);

    @Query(
            value = "SELECT * FROM (\n"+
                    "SELECT\n" +
                    "available_activity.id,\n" +
                    "available_activity.time,\n" +
                    "available_activity.available,\n" +
                    "available_activity.facility_reservation_id,\n" +
                    "available_activity.activity_id,\n" +
                    "available_activity.last_updated,\n" +
                    "activity.title title,\n" +
                    "ST_Distance_Sphere(POINT(lng, lat), POINT(:lng, :lat)) distance\n" +
                    "FROM available_activity available_activity\n" +
                    "JOIN activity activity \n" +
                    "ON available_activity.activity_id=activity.id\n" +
                    "JOIN type type\n" +
                    "ON type.id=activity.type_id\n" +
                    "JOIN facility_reservation facility_reservation\n" +
                    "ON available_activity.facility_reservation_id=facility_reservation.id\n" +
                    "JOIN facility facility\n" +
                    "ON facility.id=facility_reservation.facility_id\n" +
                    "WHERE activity.title like CONCAT('%',:query,'%')\n" +
                    "AND type.keyy like :type\n" +
                    "AND available_activity.available = COALESCE(:available, available_activity.available)\n" +
                    "AND available_activity.time > CURRENT_TIME()\n" +
                    ") available_activity",
            countQuery = "SELECT COUNT(*) FROM\n"+
                    "(SELECT\n" +
                    "available_activity.id,\n" +
                    "(:lat + :lng) distance\n" +
                    "FROM available_activity available_activity\n" +
                    "JOIN activity activity\n" +
                    "ON available_activity.activity_id=activity.id\n" +
                    "JOIN type type\n" +
                    "ON type.id=activity.type_id\n" +
                    "WHERE activity.title like CONCAT('%',:query,'%')\n" +
                    "AND type.keyy like :type\n" +
                    "AND available_activity.available = COALESCE(:available, available_activity.available)\n" +
                    "AND available_activity.time > CURRENT_TIME()\n" +
                    ") a",
            nativeQuery = true)
    Page<AvailableActivity> findByType(Pageable pageable,
                                       @Param("query") String query,
                                       @Param("available") Boolean available,
                                       @Param("type") String type,
                                       @Param("lat") Double lat,
                                       @Param("lng") Double lng);
}
