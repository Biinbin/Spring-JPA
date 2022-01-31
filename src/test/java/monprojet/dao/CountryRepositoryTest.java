package monprojet.dao;

import java.util.List;
import javax.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.log4j.Log4j2;
import monprojet.dto.PopulationParPays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import monprojet.entity.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryDAO;

    @Test
    void lesNomsDePaysSontTousDifferents() {
        log.info("On vérifie que les noms de pays sont tous différents ('unique') dans la table 'Country'");
        
        Country paysQuiExisteDeja = new Country("XX", "France");
        try {
            countryDAO.save(paysQuiExisteDeja); // On essaye d'enregistrer un pays dont le nom existe   

            fail("On doit avoir une violation de contrainte d'intégrité (unicité)");
        } catch (DataIntegrityViolationException e) {
            // Si on arrive ici c'est normal, l'exception attendue s'est produite
        }
    }

    @Test
    @Sql("test-data.sql") // On peut charger des donnnées spécifiques pour un test
    void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Country'");
        int combienDePaysDansLeJeuDeTest = 3 + 1; // 3 dans data.sql, 1 dans test-data.sql
        long nombre = countryDAO.count();
        assertEquals(combienDePaysDansLeJeuDeTest, nombre, "On doit trouver 4 pays" );
    }
    
    @Test
    void populationCountry(){
        int pop = 12+7+2;
        int countryPopFr = countryDAO.populationPays(1);
        assertEquals(pop, countryPopFr);
    }
    
    @Test
    @Sql("test-data.sql") //On utilise cette table pour faire les tests
    void nomPaysPopulation(){
        List<PopulationParPays> laListe = countryDAO.nameCountryPopulation();
        //France
        PopulationParPays popFrance = laListe.get(0);
        assertEquals(21, popFrance.getPopu());
        
       //US
       PopulationParPays popUs = laListe.get(2);
       assertEquals(27, popUs.getPopu());
    }
}