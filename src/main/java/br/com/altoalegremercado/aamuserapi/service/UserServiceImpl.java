package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.domain.model.Role;
import br.com.altoalegremercado.aamuserapi.domain.model.User;
import br.com.altoalegremercado.aamuserapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User getUserByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    @Override
    public User getUserByCnpj(String cnpj) {
        return userRepository.findByCnpj(cnpj);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(Long id, User userUpdate) throws Exception {
        if (!id.equals(userUpdate.getId())) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "id in URI does not match user to update");
        }

        Optional<User> userSearched = userRepository.findById(id);

        User originalUser = userSearched.orElseThrow(() -> new Exception("User with ID (" + id + ") not found!"));

        BeanUtils.copyProperties(userUpdate, originalUser);

        return userRepository.save(userUpdate);
    }

    @Override
    public Long deleteUser(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()){
           throw  new Exception("User with ID (" + id + ") not found!");
        }
        
        userRepository.deleteById(user.get().getId());
        return id;
    }
}
