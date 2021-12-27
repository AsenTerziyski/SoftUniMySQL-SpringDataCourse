import exercise.entity.City;
import exercise.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("TestTestTest");

//        Session session = new Configuration().configure().buildSessionFactory().openSession();
//        Transaction transaction = session.beginTransaction();
//        transaction.commit();

//        System.out.println("finishTestTestTest");


//        System.out.println("************************************************");
//        System.out.println("TestTestTest2");
//        User testUser = new User();
//        testUser.setUsername("TestUser");
//        testUser.setPassword("testUserPass");
//        session.save(testUser);
//
//        User testUser2 = new User();
//        testUser2.setUsername("TestUser2");
//        testUser2.setPassword("testUserPass2");
//        session.save(testUser2);
//
//
//        transaction.commit();
//        System.out.println("finishTestTestTest2");

//        System.out.println("************************************************");
//        System.out.println("TestTestTest3");
//        var testUser2 = session.get(User.class,2);
//        session.delete(testUser2);
//        transaction.commit();
//        System.out.println("finishTestTestTest2");
//        System.out.println("************************************************");
//
//        System.out.println("************************************************");
//        System.out.println("TestTestTest4");
//        var testUser4 = session.get(User.class,1);
//        testUser4.setUsername("XXXX");
//        testUser4.setPassword("PPPP");
//        transaction.commit();
//        System.out.println("finishTestTestTest2");
//        System.out.println("************************************************");

        SessionFactory sessionFactory = new Configuration()
                .configure().buildSessionFactory();
        Session session1 = sessionFactory.openSession();
        Transaction transaction = session1.beginTransaction();
        EntityManager session = sessionFactory.openSession();
        TypedQuery<User> query = session.createQuery("SELECT u from User u where u.city.name = :cn", User.class);
        query.setParameter("cn", "Shumen");
        List<User> resultList = query.getResultList();

        resultList.forEach(user -> System.out.println(user));
        resultList.forEach(user -> System.out.println(user.getId() + " => " + user.getCity().getName()));

        var cityQuery = session.createQuery("from City c where c.name = :name", City.class);
        cityQuery.setParameter("name", "Shumen");
        System.out.println(cityQuery.getSingleResult().getName());
        City sh = cityQuery.getSingleResult();

        var manol = new User();
        manol.setPassword("M1111");
        manol.setUsername("M");
        manol.setCity(sh);
        session1.save(manol);
        transaction.commit();



    }
}
