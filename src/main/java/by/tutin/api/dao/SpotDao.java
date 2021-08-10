package by.tutin.api.dao;

import by.tutin.model.Spot;

public interface SpotDao extends GenericDao<Spot>{

    int getScootersNumber(Long spotId);


    void checkForUserLinks(Long id);
}
