/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Nahid
 */
public class ManageEmployee {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            ManageEmployee me = new ManageEmployee();
            me.listEmployee();
//            Integer empID = me.addEmployee("Nahid","Quliyev",500);
//           me.updateEmployee(1, 274); 
//             me.updateEmployee(empID, 274);
//            me.listEmployee();
//            me.deleteEmployee(1);
//            me.listEmployee();
        } catch (Throwable ex) {
            System.out.println("Failed to  create Session Factory object " + ex);
            //take a look
//            throw new ExceptionInInitializerError(ex);
        }

    }

    public Integer addEmployee(String firstname, String lastname, int salary) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try {
            tx = session.beginTransaction();
            Employee employee = new Employee(firstname, lastname, salary);
            employeeID = (Integer) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    public void listEmployee() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
                Employee employee = (Employee) iterator.next();
                System.err.println("firstName" + employee.getFirstName());
                System.err.println("lastName" + employee.getLastName());
                System.err.println("salary" + employee.getSalary());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateEmployee(Integer id, Integer salary) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, id);
            employee.setSalary(salary);
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public void deleteEmployee(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, id);
            session.delete(employee);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
