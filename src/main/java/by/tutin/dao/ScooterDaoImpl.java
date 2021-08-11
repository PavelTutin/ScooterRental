package by.tutin.dao;

import by.tutin.api.dao.ScooterDao;
import by.tutin.exception.DaoException;
import by.tutin.model.Scooter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Log4j
@Repository
@Transactional
public class ScooterDaoImpl extends AbstractDao<Scooter> implements ScooterDao {
    @Override
    protected Class<Scooter> getClazz() {
        return Scooter.class;
    }


    @Override
    public List<Scooter> getScootersBySpot(Long spotId) {
        try {
            log.info("try to take scooters by spot with id:" + spotId);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Scooter> cr = cb.createQuery(Scooter.class);
            Root<Scooter> root = cr.from(Scooter.class);
            cr.select(root).where(cb.equal(root.get("spot"), spotId));

            Query query = entityManager.createQuery(cr);
            return query.getResultList();
        } catch (Exception e) {
            log.warn("can't take scooters by spot with id:" + spotId, e);
            throw new DaoException("can't take scooters by spot with id:" + spotId, e);
        }
    }

}
