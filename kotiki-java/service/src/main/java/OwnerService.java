import models.Cat;
import models.Owner;

import java.util.List;

public class OwnerService {

    private final OwnerDaoImpl ownerDao;

    public OwnerService(OwnerDaoImpl ownerDao) {
        this.ownerDao = ownerDao;
    }

    public Owner findOwner(String id) {
        return ownerDao.findById(id);
    }

    public void saveOwner(Owner owner) {
        ownerDao.save(owner);
    }

    public void deleteOwner(Owner owner) {
        ownerDao.delete(owner);
    }

    public void updateOwner(Owner owner) {
        ownerDao.update(owner);
    }

}