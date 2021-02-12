package fr.paris8.iutmontreuil.mysmallbank.account.infrastructure;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AccountRepository {

    private final AccountDAO accountDAO;
    private final HolderDAO holderDAO;

    public AccountRepository(AccountDAO accountDAO, HolderDAO holderDAO) {
        this.accountDAO = accountDAO;
        this.holderDAO = holderDAO;
    }

    // TODO: on the Holder Repository model
    public Account getAccount(String id){
        AccountEntity account = accountDAO.getOne(id);
        return AccountMapper.toAccount(account);
    }

    public List<Account> listAccount(){
        return accountDAO.findAll().stream().map(AccountMapper::toAccount).collect(Collectors.toList());
    }

    public Account create(Account account){
        AccountEntity accountToSave = AccountMapper.toEntity(account);
        AccountEntity createAccount = accountDAO.save(accountToSave);
        return AccountMapper.toAccount(createAccount);
    }

    public void deleteAccount(String accountId) {
        Account accountToDelete = AccountMapper.toAccount(accountDAO.getOne(accountId));
        accountDAO.deleteById(accountToDelete.getUid());
    }
}
