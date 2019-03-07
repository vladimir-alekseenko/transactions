package test.valekseenko;

import com.google.inject.Binder;
import com.google.inject.Module;
import test.valekseenko.dao.AccountDAO;
import test.valekseenko.dao.InMemoryMockAccountDAO;
import test.valekseenko.dao.InMemoryTransactionDAO;
import test.valekseenko.dao.TransactionDAO;
import test.valekseenko.endpoints.TransactionEndpoint;
import test.valekseenko.services.*;

public class GuiceModule implements Module {

    @Override
    public void configure(final Binder binder) {
        binder.bind(TransactionEndpoint.class);
        binder.bind(TransactionService.class).to(TransactionServiceImpl.class);
        binder.bind(TransactionDAO.class).to(InMemoryTransactionDAO.class);
        binder.bind(AccountService.class).to(AccountServiceImpl.class);
        binder.bind(AccountDAO.class).to(InMemoryMockAccountDAO.class);
    }
}
