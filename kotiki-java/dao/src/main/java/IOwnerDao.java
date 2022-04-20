public interface IOwnerDao<T1> {
    T1 findById(String id);
    void save(T1 owner);
    void update(T1 t1);
    void delete(T1 t1);
}
