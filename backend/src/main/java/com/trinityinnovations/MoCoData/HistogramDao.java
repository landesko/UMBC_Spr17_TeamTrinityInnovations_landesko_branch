package com.trinityinnovations.MoCoData;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Created by Dan Lesko on 4/9/2017.
 */
@Repository
public class HistogramDao {

  private EntityManager entityManager;

  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }


  public List<HistogramWrapper> getHistogram(String start_date, String end_date) {
    List<HistogramWrapper> histogramData;


//    String queryString = "SELECT t1.tmstamp, t1.cnt, t2.cnt FROM " +
//      "(SELECT DATE_FORMAT(MIN(arrest_date), '%Y/%m/%d') AS dateBucket, COUNT(*) AS arrestCount FROM arrest " +
//      "WHERE arrest_date BETWEEN \'" + start_date + "\' AND \'" + end_date + "\' " +
//      "GROUP BY ROUND(UNIX_TIMESTAMP(arrest_date) / 86400)) t1 " +
//      "LEFT JOIN " +
//      "(SELECT DATE_FORMAT(MIN(date), '%Y/%m/%d') AS dateBucket, COUNT(*) AS crimeCount FROM crime " +
//      "WHERE date BETWEEN \'" + start_date + "\' AND \'" + end_date + "\' " +
//      "GROUP BY ROUND(UNIX_TIMESTAMP(date) / 86400)) t2 " +
//      "ON t1.tmstamp = t2.tmstamp ";

    String queryString = "  SELECT t1.tmstamp as dateBucket, t1.cnt as arrestCount, t2.cnt as crimeCount\n" +
      "  from\n" +
      "  (SELECT DATE_FORMAT(MIN(arrest_date), '%Y/%m/%d') AS tmstamp,\n" +
      "  COUNT(*) AS cnt\n" +
      "  FROM arrest\n" +
      "  WHERE arrest_date BETWEEN \'" + start_date + "\' AND \'" + end_date + "\'\n " +
      "  GROUP BY ROUND(UNIX_TIMESTAMP(arrest_date) / 86400)) t1\n" +
      "  LEFT JOIN\n" +
      "  (SELECT DATE_FORMAT(MIN(date), '%Y/%m/%d') AS tmstamp,\n" +
      "  COUNT(*) AS cnt\n" +
      "  FROM crime\n" +
      "  WHERE date BETWEEN \'" + start_date + "\' AND \'" + end_date + "\'\n " +
      "  GROUP BY ROUND(UNIX_TIMESTAMP(date) / 86400)) t2\n" +
      "  ON t1.tmstamp = t2.tmstamp";

    histogramData = entityManager.createNativeQuery(queryString).getResultList();

    return histogramData;
  }
}
//  SELECT t1.tmstamp, t1.cnt, t2.cnt
//  from
//  (SELECT DATE_FORMAT(MIN(arrest_date), '%Y/%m/%d') AS tmstamp,
//  COUNT(*) AS cnt
//  FROM arrest
//  WHERE arrest_date BETWEEN "2017-04-02 12:40:00" AND "2017-04-10 12:40:00"
//  GROUP BY ROUND(UNIX_TIMESTAMP(arrest_date) / 86400)) t1
//  LEFT JOIN
//  (SELECT DATE_FORMAT(MIN(date), '%Y/%m/%d') AS tmstamp,
//  COUNT(*) AS cnt
//  FROM crime
//  WHERE date BETWEEN "2017-04-02 12:40:00" AND "2017-04-10 12:40:00"
//  GROUP BY ROUND(UNIX_TIMESTAMP(date) / 86400)) t2
//  ON t1.tmstamp = t2.tmstamp