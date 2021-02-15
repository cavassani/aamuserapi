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
    @Autowired
    UserRepository userRepository;

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
    public List<User> getUsersByRole(Enum<Role> role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(Long id, User userUpdate) {
        if (!id.equals(userUpdate.getId())) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "id in URI does not match vehicle vin to update");
        }

        Optional<User> userSearched = userRepository.findById(id);

        if (!userSearched.isPresent()) {
            //throw new UserNotFoundException("User with ID (" + id + ") not found!");
        }
        User orginalUser = userSearched.get();

        BeanUtils.copyProperties(userUpdate, orginalUser);

        return userRepository.save(userUpdate);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){}

        userRepository.delete(user);
    }
}
