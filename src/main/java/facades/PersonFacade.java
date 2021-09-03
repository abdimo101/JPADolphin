package facades;

import dat.jpademo.entities.Person;

import javax.persistence.*;
import java.util.List;

public class PersonFacade {
    private static EntityManagerFactory emf;
    private static PersonFacade instance;
    public List<Person> getAllPersons(){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Person> query = em.createQuery("select person from Person person",Person.class);
            return query.getResultList();
        }
        finally {
            em.close();
        }
    }
    public Person getPersonById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Person person = em.find(Person.class, id);
            return person;
        } finally {
            em.close();
        }
    }
    public Person createPerson(String name, int year){
        Person person = new Person(name, year);
            return person;
        }

 public Person updatePerson(Person p, String name, int year){
        p.setName(name);
        p.setYear(year);
        return p;
    }

 //public int deletePerson(){}


    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        PersonFacade facade = PersonFacade.getPersonFacade(emf);
        EntityManager em = emf.createEntityManager();
        Person p1 = facade.createPerson("Blondie", 1960);
        Person p2 = facade.createPerson("Makrel", 1975);

        facade.updatePerson(p1,"Katrine", 2010);

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();

        System.out.println("p1 navn: " + facade.getPersonById(p1.getP_id()).getName() + " "+  p1.getYear());
        System.out.println("p2 navn: " + facade.getPersonById(p2.getP_id()).getName() + " "+  p2.getYear());
        //for (Person person : facade.getAllPersons()) {
        //            System.out.println(person.getName() + " " + person.getYear());
        //        }



    }
}
