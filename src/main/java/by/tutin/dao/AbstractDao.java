package by.tutin.dao;

import by.tutin.api.dao.GenericDao;
import by.tutin.exception.DaoException;
import by.tutin.model.AEntity;
import lombok.extern.log4j.Log4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@Log4j
public abstract class AbstractDao<T extends AEntity> implements GenericDao<T> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    protected abstract Class<T> getClazz();

    @Override
    public void save(T entity) {
        try {
            log.info(String.format("Dao. try to save %s with id %d", entity.getClass(), entity.getId()));
            entityManager.persist(entity);
        } catch (Exception e) {
            log.warn(String.format("Dao. can't save %s with id %d", entity.getClass(), entity.getId()), e);
            throw new DaoException(String.format("can't save %s with id %d", entity.getClass(), entity.getId()), e);
        }

    }

    @Override
    public T getById(Long id) {
        try {
            log.info("Dao. try to get entity by id " + id);
            return entityManager.find(getClazz(), id);
        } catch (Exception e) {
            log.warn("Dao. Can't getById " + id, e);
            throw new DaoException("Can't getById " + id, e);
        }
    }

    @Override
    public List<T> getAll() {
        try {
            log.info("Dao. try to getAll ");
            return entityManager.createQuery(String.format("from %s", getClazz().getName()), getClazz()).getResultList();
        } catch (Exception e) {
            log.warn("Dao. Can't getALl", e);
            throw new DaoException("Can't getALl", e);
        }
    }

    @Override
    public void delete(T entity) {
        try {
            log.info(String.format("Dao. try to delete %s with id %d", entity.getClass(), entity.getId()));
            entityManager.remove(entity);
        } catch (Exception e) {
            log.warn(String.format("Dao. can't delete %s with id %d", entity.getClass(), entity.getId()), e);
            throw new DaoException(String.format("can't save %s with id %d", entity.getClass(), entity.getId()), e);
        }
    }


    @Override
    public T update(T entity) {
        try {
            log.info(String.format("Dao. try to update %s with id %d", entity.getClass(), entity.getId()));
            return entityManager.merge(entity);
        } catch (Exception e) {
            log.warn(String.format("Dao. can't update %s with id %d", entity.getClass(), entity.getId()), e);
            throw new DaoException(String.format("can't update %s with id %d", entity.getClass(), entity.getId()), e);
        }
    }
}
