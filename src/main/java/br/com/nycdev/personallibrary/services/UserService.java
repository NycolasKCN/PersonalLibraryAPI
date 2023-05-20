package br.com.nycdev.personallibrary.services;

import br.com.nycdev.personallibrary.dtos.UserDto;
import br.com.nycdev.personallibrary.exceptions.UserLoginAlreadyExistsException;
import br.com.nycdev.personallibrary.forms.UserForm;
import br.com.nycdev.personallibrary.models.User;
import br.com.nycdev.personallibrary.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto registerUser(UserForm userForm) throws UserLoginAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByLogin(userForm.login());
        if (userOptional.isPresent()) {
            throw new UserLoginAlreadyExistsException("User with login: " +userForm.login()+ " already exists.");
        }
        User user = new User(userForm);
        userRepository.save(user);
        return new UserDto(user);
    }

    public List<UserDto> getAll() {
        List<UserDto> all = new ArrayList<>();

        userRepository.findAll().forEach(user -> {
            all.add(new UserDto(user));
        });
        return all;
    }
}
