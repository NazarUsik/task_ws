package com.nixsolutions.usik.client;

import com.nixsolutions.usik.ws.CreateRequest;
import com.nixsolutions.usik.ws.CreateResponse;
import com.nixsolutions.usik.ws.ReadAllRequest;
import com.nixsolutions.usik.ws.ReadAllResponse;
import com.nixsolutions.usik.ws.ReadRequest;
import com.nixsolutions.usik.ws.ReadResponse;
import com.nixsolutions.usik.ws.UpdateRequest;
import com.nixsolutions.usik.ws.UpdateResponse;
import com.nixsolutions.usik.ws.DeleteRequest;
import com.nixsolutions.usik.ws.DeleteResponse;
import com.nixsolutions.usik.ws.ObjectFactory;
import com.nixsolutions.usik.ws.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class UserClient extends WebServiceGatewaySupport {

    private static final Logger logger =
            LogManager.getLogger(UserClient.class.getName());
    private static final ObjectFactory factory =
            new ObjectFactory();

    public CreateResponse getCreate(User user) {
        CreateRequest request = factory.createCreateRequest();
        request.setUser(user);

        logger.info("Create request, user: {}", user.getLogin());
        return (CreateResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    public ReadAllResponse getReadAll(String info) {
        ReadAllRequest request = factory.createReadAllRequest();
        request.setInfo(info);

        logger.info("ReadAll request, info: {}", request.getInfo());
        return (ReadAllResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    public ReadAllResponse getReadAll() {
        return getReadAll("");
    }

    public ReadResponse getRead(String login) {
        ReadRequest request = factory.createReadRequest();
        request.setLogin(login);

        logger.info("Read request, login: {}", request.getLogin());
        return (ReadResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    public UpdateResponse getUpdate(User user) {
        UpdateRequest request = factory.createUpdateRequest();
        request.setUser(user);

        logger.info("Update request, user: {}", user.getLogin());
        return (UpdateResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    public DeleteResponse getDelete(String login) {
        DeleteRequest request = factory.createDeleteRequest();
        request.setLogin(login);

        logger.info("Delete request, login: {}", request.getLogin());
        return (DeleteResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

}
