package monprojet.dao;

import java.util.List;
import monprojet.dto.PopulationParPays;
import org.springframework.data.jpa.repository.JpaRepository;
import monprojet.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring 
//

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query (value=" SELECT SUM(CITY.POPULATION) "
            + " FROM COUNTRY "
            + " INNER JOIN CITY ON CITY.country_id = COUNTRY.id "
            + " WHERE COUNTRY.id = :id ",
            nativeQuery = true)
    public int populationPays(@Param("id")int id);
    
    @Query (value=" SELECT COUNTRY.name, SUM(CITY.POPULATION) "
            + " FROM COUNTRY "
            + " INNER JOIN CITY ON CITY.country_id = COUNTRY.id "
            + " GROUP BY COUNTRY.name "
            + " ORDER BY COUNTRY.id ",
            nativeQuery = true)
    public List<PopulationParPays> nameCountryPopulation();
}
