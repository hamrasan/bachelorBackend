package cz.fel.cvut.hamrasan.gardener.seeder;

import cz.fel.cvut.hamrasan.gardener.dao.*;
import cz.fel.cvut.hamrasan.gardener.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class DbSeeder implements
        ApplicationListener<ContextRefreshedEvent> {

    private PlantDao plantDao;
    private UserDao userDao;
    private GardenDao gardenDao;
    private PlantCategoryDao plantCategoryDao;
    private RainDao rainDao;
    private SubcategoryDao subcategoryDao;
    private UserPlantDao userPlantDao;

    @Autowired
    public DbSeeder(PlantDao plantDao, UserDao userDao, PlantCategoryDao plantCategorydao, GardenDao gardenDao, RainDao rainDao, SubcategoryDao subcategoryDao, UserPlantDao userPlantDao) {

        this.plantDao = plantDao;
        this.userDao = userDao;
        this.plantCategoryDao = plantCategorydao;
        this.subcategoryDao= subcategoryDao;
        this.gardenDao = gardenDao;
        this.rainDao = rainDao;
        this.userPlantDao = userPlantDao;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //TODO - vykona sa hned po spusteni
        System.out.println("Vypis po stupusteni aplikacie.");
        createUsers();
        createCategories();
        createSubcategories();
        createPlants();
        createGarden();
        createUserPlant();
        createRain();
    }

    @Transactional
    void createPlants(){

//        Subcategory subcategory2 = new Subcategory("None", plantCategoryDao.find((long) 2), plants);
//        subcategoryDao.persist(subcategory2);

        for (Subcategory sub : subcategoryDao.findAll()) {
            if(sub.getName().equals("Raj??iny") && sub.getCategory().getName().equals("zelenina")) {
                Plant plant = new Plant("Raj??ina ve??k??", "rajciny.jpg", 12, 35,  "Leto", sub);
                plantDao.persist(plant);
                Plant plant2 = new Plant("Raj??ina cherry", "cherry.jpg", 10, 35,  "Leto", sub);
                plantDao.persist(plant2);
            }

            if(sub.getName().equals("Jahody") && sub.getCategory().getName().equals("ovocie")) {
                Plant plant2 = new Plant("Jahoda celoro??n??", "jahody.jpg", 12, 40,  "Celoro??ne", sub );
                plantDao.persist(plant2);
            }

            if(sub.getName().equals("K??stkoviny") && sub.getCategory().getName().equals("ovocie")) {
                Plant plant2 = new Plant("Marhu??a", "marhule.jpg", -12, 40,  "J??l-August", sub );
                plantDao.persist(plant2);
                Plant plant12 = new Plant("Brosky??a", "broskyna.jpg", -12, 40,  "J??l-August", sub );
                plantDao.persist(plant12);
            }

            if(sub.getName().equals("None") && sub.getCategory().getName().equals("ovocie")){
                Plant plant5 = new Plant("Ban??novn??k", "banany.jpg", 20, 30,  "Leto", sub);
                plantDao.persist(plant5);
                Plant plant6 = new Plant("Gran??tovn??k", "granatovejablko.jpg", 0, 35,  "Jese??", sub);
                plantDao.persist(plant6);
            }

            if(sub.getName().equals("Jablone") && sub.getCategory().getName().equals("ovocie")){
                Plant plant5 = new Plant("Golden Delicious", "goldenJablko.jpg",-30 , 30,  "Okt??ber", sub);
                plantDao.persist(plant5);
                Plant plant6 = new Plant("Jonagold", "jonagold.jpg",-30 , 30,  "Okt??ber", sub);
                plantDao.persist(plant6);
            }

            if(sub.getName().equals("Citrusy") && sub.getCategory().getName().equals("ovocie")){
                Plant plant5 = new Plant("Pomaran??ovn??k ????nsky", "pomaranc.jpg", -4, 35,  "J??l", sub);
                plantDao.persist(plant5);
                Plant plant6 = new Plant("Citr??novn??k", "citron.jpg", -4, 35,  "J??l", sub);
                plantDao.persist(plant6);
            }

            if(sub.getName().equals("Bobu??oviny") && sub.getCategory().getName().equals("ovocie")) {
                Plant plant12 = new Plant("??u??oriedka kanadsk??", "blueberries.jpg", -30, 40,  "Celoro??ne", sub );
                plantDao.persist(plant12);
                Plant plant13 = new Plant("Ostru??ina malinov??", "maliny2.jpg", -20, 30,  "Celoro??ne", sub );
                plantDao.persist(plant13);
            }

            if(sub.getName().equals("Kore??ov?? zelenina") && sub.getCategory().getName().equals("zelenina")) {
                Plant plant3 = new Plant("Re??kev siata prav??", "redkvicka.jpg", 2, 35,  "Apr??l", sub);
                plantDao.persist(plant3);
                Plant plant4 = new Plant("Mrkva oby??ajn??", "mrkva.jpg", 3, 35,  "Leto", sub);
                plantDao.persist(plant4);
            }

            if(sub.getName().equals("Cibu??ov?? zelenina") && sub.getCategory().getName().equals("zelenina")) {
                Plant plant3 = new Plant("Cibu??a ??lt??", "cibulaZlta.jpg", 15, 35,  "J??l-August", sub);
                plantDao.persist(plant3);
                Plant plant4 = new Plant("Cibu??a ??erven??", "cibulacervena.jpg", 15, 35,  "J??l-August", sub);
                plantDao.persist(plant4);
                Plant plant5 = new Plant("Cesnak kuchynsk?? ", "cesnak.jpg", 0, 15,  "J??n-J??l", sub);
                plantDao.persist(plant5);
            }

            if(sub.getName().equals("None") && sub.getCategory().getName().equals("zelenina")) {
                Plant plant7 = new Plant("Uhorka ??al??tov??", "uhorkaSalat.jpg", 15, 30,  "J??n-J??l", sub);
                plantDao.persist(plant7);
                Plant plant8 = new Plant("Paprika ??erven??", "paprikaCervenaSiroka.jpg", 21, 35,  "J??l/August", sub);
                plantDao.persist(plant8);
                Plant plant9 = new Plant("??u??ok zemiakov??", "zemiak.jpg", 7, 25,  "J??n-September", sub);
                plantDao.persist(plant9);
                Plant plant19 = new Plant("Chilly papri??ka", "chilly.jpg", 20, 35,  "August-November", sub);
                plantDao.persist(plant19);
                Plant plant2 = new Plant("Arty??ok", "articoky.jpg", 10, 35,  "J??l-August", sub);
                plantDao.persist(plant2);
                Plant plant3 = new Plant("Avok??do", "avokado.jpg", -4, 40,  "Celoro??ne", sub);
                plantDao.persist(plant3);
            }

            if(sub.getName().equals("None") && sub.getCategory().getName().equals("bylinky")) {
                Plant plant4 = new Plant("Bazalka prav??", "bazalka.jpg", 13, 35,  "Leto", sub);
                plantDao.persist(plant4);
            }

            if(sub.getName().equals("None") && sub.getCategory().getName().equals("kvety")) {
                Plant plant4 = new Plant("Krokus", "crocus.jpg", -5, 15,  "Jar/Jese??", sub);
                plantDao.persist(plant4);
                Plant plant5 = new Plant("Tulip??n", "tulipany.jpg", -5, 22,  "Apr??l-M??j", sub);
                plantDao.persist(plant5);
                Plant plant15 = new Plant("Levandu??a ??zkolist??", "levandula.jpg", -20, 32,  "J??n-J??l", sub);
                plantDao.persist(plant15);
                Plant plant16 = new Plant("??alia ??zijsk??", "lalia.jpg", 10, 18,  "M??j-September", sub);
                plantDao.persist(plant16);
                Plant plant17 = new Plant("Hortenzia kalinolist??", "hortenzia.jpg", -5, 30,  "Leto", sub);
                plantDao.persist(plant17);
            }

        }
    }

    @Transactional
    void createUsers(){
        User user = new User("Jozef", "Pro??ko", BCrypt.hashpw("hesloo",BCrypt.gensalt()), "jozef@gmail.com", Gender.MAN);
        userDao.persist(user);
        User user1 = new User("Polina", "Nazarenko", BCrypt.hashpw("hesloo",BCrypt.gensalt()), "polina@gmail.com", Gender.WOMAN);
        userDao.persist(user1);
    }

    @Transactional
    void createGarden(){
        List<User> users= userDao.findAll();

        List<UserPlant> userPlants = new ArrayList<UserPlant>();

        if (users.size()>0){
            Garden garden = new Garden("Z??hrada za domom","Zahradazadomom","Praha",users.get(0),userPlants);
            gardenDao.persist(garden);
        }
    }


    @Transactional
    void createUserPlant(){
        List<Plant> plants = plantDao.findAll();

        UserPlant userPlant = new UserPlant(LocalDate.now(),plants.get(0).getMinTemperature(), plants.get(0).getMaxTemperature(), plants.get(0).getSeason()
                ,plants.get(0), gardenDao.findAll().get(0));
        userPlantDao.persist(userPlant);

    }

    @Transactional
    void createCategories(){
        List<Subcategory> subcategories = new ArrayList<Subcategory>();
        PlantCategory category = new PlantCategory("zelenina", subcategories );
        plantCategoryDao.persist(category);

        PlantCategory category2 = new PlantCategory("ovocie", new ArrayList<>() );
        plantCategoryDao.persist(category2);

        PlantCategory category3 = new PlantCategory("bylinky", new ArrayList<>() );
        plantCategoryDao.persist(category3);

        PlantCategory category4 = new PlantCategory("kvety", new ArrayList<>() );
        plantCategoryDao.persist(category4);
    }

    @Transactional
    void createSubcategories(){

        for (PlantCategory category: plantCategoryDao.findAll()) {
            Subcategory subcategory = new Subcategory("None", category, new ArrayList<>());
            subcategoryDao.persist(subcategory);

            if(category.getName().equals("ovocie")){
                Subcategory subcategory1 = new Subcategory("Jahody", category, new ArrayList<>());
                Subcategory subcategory2 = new Subcategory("Citrusy", category, new ArrayList<>());
                Subcategory subcategory3 = new Subcategory("Bobu??oviny", category, new ArrayList<>());
                Subcategory subcategory4 = new Subcategory("Jablone", category, new ArrayList<>());
                Subcategory subcategory5 = new Subcategory("K??stkoviny", category, new ArrayList<>());
                subcategoryDao.persist(subcategory1);
                subcategoryDao.persist(subcategory2);
                subcategoryDao.persist(subcategory3);
                subcategoryDao.persist(subcategory4);
                subcategoryDao.persist(subcategory5);
            }
            else if(category.getName().equals("zelenina")){
                Subcategory subcategory1 = new Subcategory("Kore??ov?? zelenina", category, new ArrayList<>());
                Subcategory subcategory2 = new Subcategory("Raj??iny", category, new ArrayList<>());
                Subcategory subcategory3 = new Subcategory("Cibu??ov?? zelenina", category, new ArrayList<>());
                subcategoryDao.persist(subcategory1);
                subcategoryDao.persist(subcategory2);
                subcategoryDao.persist(subcategory3);
            }
        }
    }

    @Transactional
    void createRain(){
        rainDao.persist(new Rain(LocalDateTime.now(), false, gardenDao.find((long) 1)));
    }

}
