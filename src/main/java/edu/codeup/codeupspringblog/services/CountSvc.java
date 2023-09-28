package edu.codeup.codeupspringblog.services;

import edu.codeup.codeupspringblog.repositories.ContactRepository;
import edu.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CountSvc {

    private ContactRepository contactsDao;
    private UserRepository usersDao;

    public CountSvc(ContactRepository contactsDao, UserRepository usersDao) {
        this.contactsDao = contactsDao;
        this.usersDao = usersDao;
    }

    public long returnPostsUsersCount() {
        return contactsDao.count() + usersDao.count();
    }
}
