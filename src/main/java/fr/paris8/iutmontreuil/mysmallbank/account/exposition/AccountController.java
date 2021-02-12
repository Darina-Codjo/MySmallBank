package fr.paris8.iutmontreuil.mysmallbank.account.exposition;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.AccountService;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDTO> listAllAccounts() {
        List<Account> accounts = accountService.listAllAccount();
        return AccountMapper.toDTOs(accounts);
    }


    @GetMapping("/{account-uid}")
    public AccountDTO getAccount(@PathVariable("account-uid") String accountUid) {
        Account account = accountService.getAccount(accountUid);
        return AccountMapper.toHolderDTO(account);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) throws URISyntaxException {
        Account account= AccountMapper.toAccount(accountDTO);
        Account createdAccount = accountService.createAccounts(account);
        return ResponseEntity.created(new URI("/accounts/" + createdAccount.getUid())).body(accountDTO);
    }

    @PostMapping("/batch")
    public List<AccountDTO> createAccount(@RequestBody List<AccountDTO> accountDTO) {
        List<Account> account = AccountMapper.toAccount(accountDTO);
        List<Account> createdAccount = accountService.createAccounts(account);
        return AccountMapper.toDTOs(createdAccount);
    }

    @PutMapping("/{account-uid}")
    public ResponseEntity<Void> updateAccount(@PathVariable("account-uid") String accountUid) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
    @DeleteMapping("/{account-uid}")
    public void deleteAccount(@PathVariable("account-uid") String accountUid) {
       accountService.delete(accountUid);
    }

}
