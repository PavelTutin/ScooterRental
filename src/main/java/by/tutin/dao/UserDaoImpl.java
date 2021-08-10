package by.tutin.dao;

import by.tutin.api.dao.UserDao;
import by.tutin.exception.DaoException;
import by.tutin.model.User;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
@Log4j
@Repository
@Transactional
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    @Override
    protected Class<User> getClazz() {
        return User.class;
    }

    @Override
    public User getByUsername(String username) {
        try {
            log.info("try to take user by username :"+username);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root).where(cb.equal(root.get("username"), username));

            Query query = entityManager.createQuery(cr);
            return query.getResultList().size()==0? null : (User) query.getResultList().get(0);
        }catch (Exception e){
            log.warn("can't take user by username :"+username,e);
            throw new DaoException("can't take user by username :"+username,e);
        }
    }

}
