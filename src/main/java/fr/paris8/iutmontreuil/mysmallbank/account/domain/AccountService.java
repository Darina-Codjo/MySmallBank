package fr.paris8.iutmontreuil.mysmallbank.account.domain;

import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.AccountType;
import fr.paris8.iutmontreuil.mysmallbank.common.ValidationError;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountRepository;
import fr.paris8.iutmontreuil.mysmallbank.common.exception.BusinessException;
import fr.paris8.iutmontreuil.mysmallbank.common.exception.ValidationErrorException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> listAllAccount() {
        return accountRepository.listAccount();
    }

    public Account getAccount(String accountUid) {
        return accountRepository.getAccount(accountUid);
    }

    public Account createAccounts(Account account) throws ValidationErrorException {
        List<ValidationError> validationErrorsAccount = validateAccount(account);
        List<ValidationError> errorList = errorList(validationErrorsAccount);
        holderExist(account);
        if (!errorList.isEmpty()) {
            throw new ValidationErrorException(errorList);
        }
        return accountRepository.create(account);
    }

    public ValidationError validateBalance(Account account){
        if (account.getBalance() <= 0){
            ValidationError validationErrors = new ValidationError("must have balance");
            return validationErrors;
        }
        if (account.getBalance() < AccountType.PEL.getMinimumBalance()){
            ValidationError validationError = new ValidationError("must have minimum balance for PEL account type : 300");
            return validationError;
        }
       return null;
    }

    public ValidationError validateCategory(Account account){
        if (account.getCategory() == null){
            ValidationError validationErrors = new ValidationError("must have an account category");
            return validationErrors;
        }
        return null;
    }

    private List<ValidationError> validateAccount(Account account) {
        List<ValidationError> validationErrors = new ArrayList<ValidationError>();

        validationErrors.add(validateBalance(account));
        validationErrors.add(validateCategory(account));

        if (validationErrors == null || validationErrors.isEmpty()){
           List<ValidationError> noError = new ArrayList<ValidationError>();
           return noError;
        }
        return validationErrors;
    }
    public List<ValidationError> errorList(List<ValidationError> list){
        List<ValidationError> errorList = new ArrayList<ValidationError>();
        for (ValidationError error : list) {
            if (error != null){
                errorList.add(error);
            }
        }
        return errorList;
    }

    public void holderExist(Account account){
        if (account.getHolder() == null){
            throw new BusinessException(account + "doesn't have holder");
        }
        if (account.getHolder().getId() == null){
            throw new BusinessException(account.getHolder() + "doesn't have id");
        }
    }

    public List<Account> createAccounts(List<Account> account) {
        List<Account> accountList = new ArrayList<>();
        for (Account holderAccount : account) {
            if (validateAccount(holderAccount).isEmpty()){
                accountList.add(createAccounts(holderAccount));
            }
            else {
                throw new ValidationErrorException(validateAccount(holderAccount));
            }
        }
        return accountList;
    }

    public void delete(String accountUid) {
        Account accountToDelete = getAccount(accountUid);
        if (accountToDelete.getBalance() >= 0){
            accountRepository.deleteAccount(accountUid);
       }
        else {
            throw new BusinessException("cannot delete with negative balance");
        }
    }

}
