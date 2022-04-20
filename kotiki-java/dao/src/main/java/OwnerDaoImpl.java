import models.Owner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

public class OwnerDaoImpl implements IOwnerDao<Owner> {
    @Override
    public Owner findById(String id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Owner result = session.get(Owner.class, id);
        session.close();
        return result;
    }

    @Override
    public void save(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(owner);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(owner);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(owner);
        tx1.commit();
        session.close();
    }
}