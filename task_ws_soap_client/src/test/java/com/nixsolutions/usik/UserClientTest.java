package com.nixsolutions.usik;

import com.nixsolutions.usik.client.UserClient;
import com.nixsolutions.usik.config.UserClientConfig;
import com.nixsolutions.usik.ws.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UserClientConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserClientTest {

    private static final String ok =
            "HttpStatus: " + HttpStatus.OK.value() + " " + HttpStatus.OK.name();

    @Autowired
    private UserClient client;
    private List<User> users;

    private User createUser(
            long id,
            String password,
            String login,
            String email,
            String firstName,
            String lastName,
            String birthday,
            long roleId
    ) throws DatatypeConfigurationException {
        User user = new User();

        user.setId(id);
        user.setPassword(password);
        user.setLogin(login);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRoleId(roleId);

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(Date.valueOf(birthday));
        user
                .setBirthday(
                        DatatypeFactory
                                .newInstance()
                                .newXMLGregorianCalendar(c));

        return user;
    }

    @Before
    public void setUp() throws DatatypeConfigurationException {
        users = new ArrayList<>();

        users.add(createUser(
                1,
                "$2a$10$ZotEfi9LbLZAn2OPy9JizO4pVgt23wNcCVmJgLvGtWHAQIFc7Beki",
                "user",
                "user1@example.mail.com",
                "user",
                "user",
                "2000-10-20",
                2
        ));

        users.add(createUser(
                2,
                "$2a$10$WQ6kvNUAyC4DV.GaVrDyg.pxEvwfJKdF91QkCWdI91DojyMYjRYgu",
                "user2",
                "user2@example.mail.com",
                "user2",
                "user2",
                "1980-10-20",
                2
        ));

        users.add(createUser(
                3,
                "$2a$10$2BxEd4KLwsiT9RHCJWzxCuohdyTQq2x4tyMiFmBa1XuABb9/yyI6e",
                "user3",
                "user3@example.mail.com",
                "user3",
                "user3",
                "1990-10-20",
                2
        ));

        users.add(createUser(
                4,
                "$2a$10$gwTzOPvm7ce/mm7OE2g5Iu6Kh/NLjlKnmUncVfV24qgb16AsHTF7.",
                "admin",
                "admin@example.mail.com",
                "admin",
                "admin",
                "2010-10-20",
                1
        ));
    }

    @Test
    public void createTest() {
        User user = users.get(0);
        user.setLogin("newUser");

        CreateResponse response = client.getCreate(user);
        assertEquals(
                "Status must bu equals",
                ok,
                response.getResponseMessage()
        );

        assertThat(client.getRead(user.getLogin()).getUser()).isNotNull();
    }

    @Test
    public void readAllTest() {
        ReadAllResponse response = client.getReadAll("test");
        List<User> users = response.getUsers();

        for (int i = 0; i < users.size(); i++) {
            assertEquals(
                    "Login must be equals",
                    this.users.get(i).getLogin(),
                    users.get(i).getLogin())
            ;
        }
    }

    @Test
    public void readTest() {
        User user = users.get(0);

        ReadResponse response = client.getRead(user.getLogin());
        User responseUser = response.getUser();

        assertEquals(
                "Users must be equals",
                user.getLogin() + user.getEmail(),
                responseUser.getLogin() + responseUser.getEmail()
        );
    }

    @Test
    public void updateTest() {
        User user = users.get(0);
        user.setLastName("uss");

        UpdateResponse response = client.getUpdate(user);
        assertEquals(
                "Status must bu equals",
                ok,
                response.getResponseMessage()
        );

        assertEquals(
                "Must be equals",
                client.getRead(user.getLogin()).getUser().getLastName(),
                user.getLastName()
        );
    }

    @Test
    public void deleteTest() {
        User user = users.get(3);

        DeleteResponse response = client.getDelete(user.getLogin());
        assertEquals(
                "Status must bu equals",
                ok,
                response.getResponseMessage()
        );
    }

}
