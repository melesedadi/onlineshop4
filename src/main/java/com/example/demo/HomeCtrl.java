package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Controller
public class HomeCtrl {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    XferRepository xferRepository;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "homePg";
    }

    @GetMapping("/newcustomer")
    public String newcustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "addNewCust";
    }

    @PostMapping("/processNewCust")
    public String processNewCust(@Valid Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return "addNewCust";
        }

        customerRepository.save(customer);
        return "redirect:/";
    }

    @GetMapping("/addacct/{id}")
    public String addAccount(@PathVariable("id") long id, Model model) {
        // id is customer id
        model.addAttribute("customer", customerRepository.findById(id));

        Account account = new Account();
        model.addAttribute("account", account);

        return "addAcctPg";
    }

    @PostMapping("/processAcct/{id}")
    public String processAccount(@PathVariable("id") long id,
                                 @Valid Account account,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "addAcctPg";
        }

        Customer customer = customerRepository.findById(id);
        account.setOwner(customer);

        Set<Account> allaccounts = customer.getAccounts();
        allaccounts.add(account);

        customer.setAccounts(allaccounts);
        accountRepository.save(account);
        customerRepository.save(customer);

        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detailAccount(@PathVariable("id") long id, Model model) {
        // id is customer id
        model.addAttribute("customer", customerRepository.findById(id));

        return "detailAcctPg";
    }

    @GetMapping("/xfer/{id}")
    public String depoistAccount(@PathVariable("id") long id, Model model) {
        // id is account id
        Account account = accountRepository.findById(id);
        Account testacct = new Account();

        model.addAttribute("account", account);

        testacct.setAcctno(account.getAcctno());
        testacct.setBalance(account.getBalance());
        testacct.setChecking(account.isChecking());
        testacct.setId(account.getId());
        testacct.setOwner(null);
        model.addAttribute("testacct", testacct);

        return "validateAcct";
    }

    @PostMapping("/validateAcct/{id}")  // id is account's id
    public String validateAccount(@PathVariable("id") long id,
                                  @Valid Account testacct, Model model,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "validateAcct";
        }

        // check for password match
        Account realacct = accountRepository.findById(id);
        if (realacct.getPasswd().equalsIgnoreCase(testacct.getPasswd())) {
            // password matched
            model.addAttribute("account", realacct);

            Xfer xfer = new Xfer();
            xfer.setAcctno(realacct.getAcctno());
            xfer.setBalance(realacct.getBalance());
            model.addAttribute("xfer", xfer);
            return "deposit";
        } else
            return "redirect:/";

    }

    @PostMapping("/processXfer/{id}")  // id is account's id
    public String processDeposit(@PathVariable("id") long id,
                                 @Valid Xfer xfer,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "deposit";
        }

        Account account = accountRepository.findById(id);
        double bal = account.getBalance();
        double amt = xfer.getAmount();
        if (xfer.isDeposit())
            bal += amt;
        else
            bal -= amt;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
	    xfer.setDate(dateFormat.format(date)); //2013/10/15 16:16:39
        xfer.setAccount(account);
        xfer.setBalance(bal);
        xferRepository.save(xfer);

        account.setBalance(bal);
        accountRepository.save(account);

        return "redirect:/";

    }

    @GetMapping("/statement/{id}")
    public String listStmt(@PathVariable("id") long id, Model model) {
        // id is account id
        Account account = accountRepository.findById(id);

        Set<Xfer> xfers = xferRepository.findAllByAcctno(account.getAcctno());
        model.addAttribute("xfers", xfers);
        return "listStmt";
    }
}
