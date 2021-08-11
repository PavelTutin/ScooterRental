package by.tutin.dao;

import by.tutin.api.dao.OrderDao;
import by.tutin.exception.DaoException;
import by.tutin.model.Order;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Log4j
@Repository
@Transactional
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    @Override
    protected Class<Order> getClazz() {
        return Order.class;
    }

    @Override
    public List<Order> getOrdersByScooterId(Long scooterId) {
        try {
            log.info("try to take orders by scooter with id:" + scooterId);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> cr = cb.createQuery(Order.class);
            Root<Order> root = cr.from(Order.class);
            cr.select(root).where(cb.equal(root.get("scooter"), scooterId));

            Query query = entityManager.createQuery(cr);
            return query.getResultList();
        } catch (Exception e) {
            log.warn("can't take orders by scooter with id:" + scooterId, e);
            throw new DaoException("can't take orders by scooter with id:" + scooterId, e);
        }
    }

    @Override
    public void checkForSpot(Long id) {
        try {
            log.info("try to check spot for links with spotId " + id);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> cr = cb.createQuery(Order.class);
            Root<Order> root = cr.from(Order.class);

            Predicate predicateForStartSpot = cb.equal(root.get("startSpot"), id);
            Predicate predicateForEndSpot = cb.equal(root.get("endSpot"), id);
            Predicate predicateForSpot = cb.or(predicateForStartSpot, predicateForEndSpot);

            cr.select(root).where(predicateForSpot);

            Query query = entityManager.createQuery(cr);
            List<Order> orders = query.getResultList();

            System.out.println(orders.size());
            for (Order order : orders) {
                order.setStartSpot(null);
                order.setEndSpot(null);
                update(order);
            }
        } catch (Exception e) {
            log.warn("can't delete links. spotId " + id, e);
            throw new DaoException("can't delete links", e);
        }
    }

    @Override
    public void checkForUserLinks(Long id) {
        try {
            log.info("try to delete user links with user id:" + id);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> cr = cb.createQuery(Order.class);
            Root<Order> root = cr.from(Order.class);
            cr.select(root).where(cb.equal(root.get("user"), id));

            Query query = entityManager.createQuery(cr);
            List<Order> orders = query.getResultList();

            for (Order order : orders) {
                order.setUser(null);
                update(order);
            }

        } catch (Exception e) {
            log.warn("can't delete user links with user id:" + id, e);
            throw new DaoException("can't delete user links with user id:" + id, e);
        }
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        try {
            log.info("try to take orders by user with id:" + userId);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> cr = cb.createQuery(Order.class);
            Root<Order> root = cr.from(Order.class);
            cr.select(root).where(cb.equal(root.get("user"), userId));

            Query query = entityManager.createQuery(cr);
            return query.getResultList();
        } catch (Exception e) {
            log.warn("can't take orders by user with id:" + userId, e);
            throw new DaoException("can't take orders by user with id:" + userId, e);
        }
    }
}
