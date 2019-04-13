package com.api.revolut.services;

import com.api.revolut.dao.DAOFactory;
import com.api.revolut.exception.DAOException;
import com.api.revolut.objectModel.UserTransaction;
import com.api.revolut.utilities.MoneyUtil;
import org.apache.log4j.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/revolut-app/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionService {
    private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.H2);
    private static Logger log = Logger.getLogger(TransactionService.class);

    @POST
    public Response transferFund(UserTransaction transaction) throws DAOException {

        if (log.isDebugEnabled())
            log.debug("TransferFund invoked with parameter : " + transaction);

        String currency = transaction.getCurrencyCode();
        if (MoneyUtil.INSTANCE.validateCcyCode(currency)) {
            int updateCount = daoFactory.getAccountDAO().transferAccountBalance(transaction);
            if (updateCount == 2) {
                return Response.status(Response.Status.OK).build();
            } else {
                //transaction failed
                throw new WebApplicationException("Transaction failed", Response.Status.BAD_REQUEST);
            }

        } else {
            throw new WebApplicationException("Currency Code Invalid ", Response.Status.BAD_REQUEST);
        }

    }

}
