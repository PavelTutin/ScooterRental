package by.tutin.dao;

import by.tutin.api.dao.SpotDao;
import by.tutin.exception.DaoException;
import by.tutin.model.Scooter;
import by.tutin.model.Spot;
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
public class SpotDaoImpl extends AbstractDao<Spot> implements SpotDao {
    @Override
    protected Class<Spot> getClazz() {
        return Spot.class;
    }

    @Override
    public int getScootersNumber(Long spotId) {
        try {
            log.info("try to take number of scooters from spot with id:"+spotId);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Scooter> cr = cb.createQuery(Scooter.class);
            Root<Scooter> root = cr.from(Scooter.class);
            cr.select(root).where(cb.equal(root.get("spot"), spotId));

            Query query = entityManager.createQuery(cr);
            return query.getResultList().size();
        }catch (Exception e){
            log.warn("can't take number of scooters from spot with id:"+spotId,e);
            throw new DaoException("can't take number of scooters from spot with id:"+spotId,e);
        }
    }

    @Override
    public void checkForUserLinks(Long id) {
        try {
            log.info("try to delete user links in spot with user id:"+id);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Spot> cr = cb.createQuery(Spot.class);
            Root<Spot> root = cr.from(Spot.class);
            cr.select(root).where(cb.equal(root.get("maintainer"), id));

            Query query = entityManager.createQuery(cr);
            List<Spot> spots = query.getResultList();

            for (Spot spot: spots){
                spot.setMaintainer(null);
                update(spot);
            }

        }catch (Exception e){
            log.warn("can't delete user links in spot with user id:"+id);
            throw new DaoException("can't delete user links in spot with user id:"+id,e);
        }
    }
}
