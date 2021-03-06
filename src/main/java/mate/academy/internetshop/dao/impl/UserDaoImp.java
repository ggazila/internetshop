package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoImp implements UserDao {
    private static Long generatedUserId = 0L;
    private static Long generatedRoleId = 0L;

    @Override
    public User create(User user) {
        user.setUserId(generatedUserId++);
        for (Role role : user.getRoles()) {
            role.setId(generatedRoleId++);
        }
        Storage.users.add(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Optional.ofNullable(Storage.users.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find user with id: " + id)));
    }

    @Override
    public User update(User user) {
        User updatedUser = get(user.getId()).orElseThrow();
        updatedUser.setName(user.getName());
        updatedUser.setSurname(user.getSurname());
        updatedUser.setLogin(user.getLogin());
        updatedUser.setPassword(user.getPassword());
        return user;
    }

    @Override
    public boolean deleteById(Long id) {
        return Storage.users.removeIf(user -> user.getId().equals(id));

    }

    @Override
    public boolean delete(User user) {
        return Storage.users.remove(user);
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Storage.users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public Optional<User> findByToken(String token) {
        return Storage.users.stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst();
    }
}
