package com.goltsov.springdemo.dao;

import com.goltsov.springdemo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    // inject bean
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {

        Session session = sessionFactory.getCurrentSession();

        Query<Customer> query = session.createQuery("from Customer " +
                                                        "order by lastName",
                                                        Customer.class);

        List<Customer> customers = query.getResultList();

        return customers;
    }

    @Override
    public void saveCustomer(Customer customer) {

        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(customer);
    }

    @Override
    public Customer getCustomer(int id) {

        Session session = sessionFactory.getCurrentSession();

        Customer customer = session.get(Customer.class, id);

        return customer;
    }

    @Override
    public void deleteCustomer(int id) {

        Session session = sessionFactory.getCurrentSession();

        Customer customer = session.get(Customer.class, id);

// way 2
//        Query query = session.createQuery("delete from Customer where id=:customerId");
//        query.setParameter("customerId", id);
//        query.executeUpdate();

        session.delete(customer);

    }

    @Override
    public List<Customer> searchCustomers(String theSearchName) {

        Session currentSession = sessionFactory.getCurrentSession();
        Query theQuery;

        if (theSearchName != null && !theSearchName.isEmpty()) {
            theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        } else {
            theQuery = currentSession.createQuery("from Customer", Customer.class);
        }

        return (List<Customer>) theQuery.getResultList();

    }


}

