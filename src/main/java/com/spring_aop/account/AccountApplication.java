package com.spring_aop.account;

import com.spring_aop.account.dao.AccountDAO;
import com.spring_aop.account.dao.MembershipDAO;
import com.spring_aop.account.service.TrafficFortuneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AccountApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(AccountApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AccountDAO theAccountDAO,
											   MembershipDAO theMembershipDAO,
											   TrafficFortuneService theTrafficFortuneService) {

		return runner -> {
			// demoTheBeforeAdvice(theAccountDAO, theMembershipDAO);
			// demoTheAfterReturningAdvice(theAccountDAO);
			// demoTheAfterThrowingAdvice(theAccountDAO);
			// demoTheAfterAdvice(theAccountDAO);
			 demoTheAroundAdvice(theTrafficFortuneService);
			// demoTheAroundAdviceHandleException(theTrafficFortuneService);
			//demoTheAroundAdviceReThrowException(theTrafficFortuneService);
		};
	}

	private void demoTheAroundAdviceReThrowException(TrafficFortuneService theTrafficFortuneService) {
		System.out.println("\nMain Program: demoTheAroundAdviceReThrowException");

		System.out.println("Calling getFortune()");

		boolean tripWire = true;
		String data = theTrafficFortuneService.getFortune(tripWire);

		System.out.println("\nMy fortune is: " + data);

		System.out.println("Finished");
	}

	private void demoTheAroundAdviceHandleException(TrafficFortuneService theTrafficFortuneService) {
		System.out.println("\nMain Program: demoTheAroundAdviceHandleException");

		System.out.println("Calling getFortune()");

		boolean tripWire = true;
		String data = theTrafficFortuneService.getFortune(tripWire);

		System.out.println("\nMy fortune is: " + data);

		System.out.println("Finished");
	}

	private void demoTheAroundAdvice(TrafficFortuneService theTrafficFortuneService) {
		System.out.println("\nMain Program: demoTheAroundAdvice");

		System.out.println("Calling getFortune()");

		String data = theTrafficFortuneService.getFortune();

		System.out.println("\nMy fortune is: " + data);

		System.out.println("Finished");
	}

	private void demoTheAfterAdvice(AccountDAO theAccountDAO) {
		List<Account> theAccounts = null;

		try {
			boolean tripWire = false;
			theAccounts = theAccountDAO.findAccounts(tripWire);
		}
		catch(Exception exc) {
			System.out.println("\n\nMain Program: ... caught exception: " + exc);
		}
		System.out.println("\n\nMain Program: demoTheAfterAdvice");
		System.out.println("-----");

		System.out.println(theAccounts);
		System.out.println("\n");
	}

	private void demoTheAfterThrowingAdvice(AccountDAO theAccountDAO) {
		List<Account> theAccounts = null;

		try {
			boolean tripWire = true;
			theAccounts = theAccountDAO.findAccounts(tripWire);
		}
		catch(Exception exc) {
			System.out.println("\n\nMain Program: ... caught exception: " + exc);
		}
		System.out.println("\n\nMain Program: demoTheAfterThrowingAdvice");
		System.out.println("-----");

		System.out.println(theAccounts);
		System.out.println("\n");
	}

	private void demoTheAfterReturningAdvice(AccountDAO theAccountDAO) {
		List<Account> theAccounts = theAccountDAO.findAccounts();

		System.out.println("\n\nMain Program: demoTheAfterReturningAdvice");
		System.out.println("-----");

		System.out.println(theAccounts);
		System.out.println("\n");
	}

	private void demoTheBeforeAdvice(AccountDAO theAccountDAO, MembershipDAO theMembershipDAO) {
		Account myAccount = new Account();
		myAccount.setName("John");
		myAccount.setLevel("Gold");

		theAccountDAO.addAccount(myAccount, true);
		theAccountDAO.doWork();

		theAccountDAO.setName("foobar");
		theAccountDAO.setServiceCode("silver");

		String name = theAccountDAO.getName();
		String code = theAccountDAO.getServiceCode();

		theMembershipDAO.addSillyMember();
		theMembershipDAO.goToSleep();
	}

}
