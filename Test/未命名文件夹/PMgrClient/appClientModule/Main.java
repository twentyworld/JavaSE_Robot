import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import PFMgr.data.Bond;
import PFMgr.data.ETF;
import PFMgr.data.Equity;
import PFMgr.data.FRA;
import PFMgr.data.FundManager;
import PFMgr.data.Future;
import PFMgr.data.Portfolio;
import PFMgr.data.Position;
import PFMgr.data.Price;
import PFMgr.data.Security;
import PFMgr.sessionbean.FundManagerServicesRemote;
import PFMgr.sessionbean.PortfolioServicesRemote;
import PFMgr.sessionbean.PositionServicesRemote;

public class Main {
	public static void main(String[] args) {
		try {
			// Create the JNDI InitialContext.
			Context context = makeContext();

			// demoFundManagerBean(context);
			// demoPortfilioBean(context);
			demoPositionBean(context);	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static Context makeContext() throws NamingException {
		// Create Properties for JNDI InitialContext.
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
		prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		prop.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		prop.put("jboss.naming.client.ejb.context", true);
		
		// Create the JNDI InitialContext.
		Context context = new InitialContext(prop);
		
		return context;
	}
	
	static void demoFundManagerBean(Context context) throws NamingException {
		
		// Create some Fund Managers
		FundManager pat = makeFundManager(context, "Pat", "McKillen", "pat@pmck.co.uk", "44-123456789");
		FundManager braun = makeFundManager(context, "Braun", "Brelin", "braun@outlook.co.uk", "44-321456789");
		FundManager eric = makeFundManager(context, "Eric", "Jones", "eric@ericsoft.co.uk", "44-9884456789");
		FundManager julian = makeFundManager(context, "Julian", "Templeman", "templeman@ict.com", "44- 6453456789");
		
		// Display the Fund Managers
		displayFundManager(pat);	
		displayFundManager(braun);	
		displayFundManager(eric);	
		displayFundManager(julian);	
	}
	
	static FundManager makeFundManager(Context context, String fName, String lName, String email, String tel) throws NamingException {
		
		// Look-up the FundManager bean.
		String appName = "PFMgr";
		String moduleName = "PFMgrEJB";
		String beanName = "FundManagerServices";
		String packageName = "PFMgr.sessionbean";
		String className = "FundManagerServicesRemote";
		String fullJNDIName = String.format("%s/%s/%s!%s.%s",appName, moduleName, beanName, packageName, className);
		
		FundManagerServicesRemote fMgrSvr = (FundManagerServicesRemote) context.lookup(fullJNDIName);
		
		FundManager fmgr = fMgrSvr.createFundManager(fName, lName, email, tel);
		
		return fmgr;
	}

	static void displayFundManager(FundManager fmgr) {
		
		System.out.println("====== FUND MANAGER ==========");	
		
		System.out.println(fmgr.getFirstName());
		System.out.println(fmgr.getLastName());
		System.out.println(fmgr.getEmail());
		System.out.println(fmgr.getTelephone());
			
		System.out.println("====== END OF FUND MANAGER ==========");	
	}

	static void demoPortfilioBean(Context context) throws NamingException {
		
		FundManager pat = makeFundManager(context, "Pat", "McKillen", "pat@pmck.co.uk", "44-123456789");
		
		// Create some Portfolios
		Portfolio portfolioOfBonds = makePortfolio(context, pat.getFirstName() + "'s Bonds Portfolio");
		Portfolio portfolioOfEquities = makePortfolio(context, pat.getFirstName() + "'s Equities Portfolio");
		Portfolio portfolioOfETFs = makePortfolio(context, pat.getFirstName() + "'s ETFs Portfolio");
		Portfolio portfolioOfFRAs = makePortfolio(context, pat.getFirstName() + "'s FRAs Portfolio");
		Portfolio portfolioOfFutures = makePortfolio(context, pat.getFirstName() + "'s Futures Portfolio");	
		
		// Add the Portfolios to the Fund Manager
		pat.addPortfolio(portfolioOfBonds);
		pat.addPortfolio(portfolioOfEquities);
		pat.addPortfolio(portfolioOfETFs);
		pat.addPortfolio(portfolioOfFRAs);
		pat.addPortfolio(portfolioOfFutures);
		
		// Get the Portfolios and display them
		List<Portfolio> portfolios = pat.getPortfolios();
		displayPortfolios(portfolios);
	}
	
	static Portfolio makePortfolio(Context context, String name) throws NamingException {
		
		// Look-up the Portfolio bean.
		String appName = "PFMgr";
		String moduleName = "PFMgrEJB";
		String beanName = "PortfolioServices";
		String packageName = "PFMgr.sessionbean";
		String className = "PortfolioServicesRemote";
		String fullJNDIName = String.format("%s/%s/%s!%s.%s",appName, moduleName, beanName, packageName, className);

		PortfolioServicesRemote pfSvr = (PortfolioServicesRemote) context.lookup(fullJNDIName);
		
		Portfolio portfolio = pfSvr.createPortfolio(name);
		
		return portfolio;
	}
	
	static void displayPortfolios(List<Portfolio> portfolios) {
		
		System.out.println("====== PORTFOLIO ==========");	
		
		for (Portfolio p : portfolios) {
			System.out.println(p.getName());
		}
			
		System.out.println("====== END OF PORTFOLIO ==========");		
	}

	static void demoPositionBean(Context context) throws NamingException {
		
		FundManager pat = makeFundManager(context, "Pat", "McKillen", "pat@pmck.co.uk", "44-123456789");
		Portfolio myPortfolio = makePortfolio(context, pat.getFirstName() + "'s Bonds Portfolio");
		LocalDate today = LocalDate.now();
		
		// Create some Positions
		//Bonds
		Position b1 = makePosition(context, new Price(123.45, 123.55, today, "GBP"), 100, new Bond("BOND111111", "British Petroleum",5.5, LocalDate.of(2030, 12, 31)));
		Position b2 = makePosition(context, new Price(113.45, 113.55, today, "GBP"), 200, new Bond("BOND222222", "Marks & Spencer",8.5, LocalDate.of(2025, 3, 31)));
		Position b3 = makePosition(context, new Price(103.45, 103.55, today, "GBP"), 300, new Bond("BOND333333", "HSBC",2.5, LocalDate.of(2035, 9, 30)));
		
		//Equities
		Position eq1 = makePosition(context, new Price(123.45, 123.55, today, "CNY"), 100, new Equity("EQ11111111", "AliPy"));
		Position eq2 = makePosition(context, new Price(113.45, 113.55, today, "USD"), 200, new Equity("EQ22222222", "IBM"));
		Position eq3 = makePosition(context, new Price(103.45, 103.55, today, "EUR"), 300, new Equity("EQ33333333", "BMW"));

		//ETFs
		Position etf1 = makePosition(context, new Price(123.45, 123.55, today, "CHF"), 100, new ETF("ETF1111111", "SWISS1"));
		Position etf2 = makePosition(context, new Price(113.45, 113.55, today, "EUR"), 200, new ETF("ETF2222222", "EURO11"));
		Position etf3 = makePosition(context, new Price(103.45, 103.55, today, "CNY"), 300, new ETF("ETF3333333", "CHINA1"));

		//Futures
		Position fut1 = makePosition(context, new Price(123.45, 123.55, today, "CNY"), 100, new Future("ETF1111111", "AliPy"));
		Position fut2 = makePosition(context, new Price(113.45, 113.55, today, "USD"), 200, new Future("ETF2222222", "IBM"));
		Position fut3 = makePosition(context, new Price(103.45, 103.55, today, "EUR"), 300, new Future("ETF3333333", "BMW"));
		
		// FRAs
		Position fra1 = makePosition(context, new Price(123.45, 123.55, today, "CNY"), 100, new FRA("FRA1111111", 6,9,5.75));
		Position fra2 = makePosition(context, new Price(113.45, 113.55, today, "USD"), 200, new FRA("FRA2222222", 15,3,2.25));
		Position fra3 = makePosition(context, new Price(103.45, 103.55, today, "EUR"), 300, new FRA("FRA3333333", 3,12,2.5));

		// Add the positions to the portfolio
		myPortfolio.addPosition(b1);
		myPortfolio.addPosition(b2);
		myPortfolio.addPosition(b3);
		
		myPortfolio.addPosition(eq1);
		myPortfolio.addPosition(eq2);
		myPortfolio.addPosition(eq3);
		
		myPortfolio.addPosition(etf1);
		myPortfolio.addPosition(etf2);
		myPortfolio.addPosition(etf3);
		
		myPortfolio.addPosition(fut1);
		myPortfolio.addPosition(fut2);
		myPortfolio.addPosition(fut3);	

		myPortfolio.addPosition(fra1);
		myPortfolio.addPosition(fra2);
		myPortfolio.addPosition(fra3);
		
		// Display the positions
		// Get the Portfolios and display them
		List<Position> positions = myPortfolio.getPositions();
		displayPositions(positions);
	}

	static Position makePosition(Context context, Price price, int qty, Security security) throws NamingException {
		
		// Look-up the Portfolio bean.
		String appName = "PFMgr";
		String moduleName = "PFMgrEJB";
		String beanName = "PositionServices";
		String packageName = "PFMgr.sessionbean";
		String className = "PositionServicesRemote";
		String fullJNDIName = String.format("%s/%s/%s!%s.%s",appName, moduleName, beanName, packageName, className);

		PositionServicesRemote posSvr = (PositionServicesRemote) context.lookup(fullJNDIName);
		
		Position position = posSvr.createPosition(security, qty, price);
		
		return position;
	}
	
	static void displayPositions(List<Position> positions) {
		
		System.out.println("====== POSITIONS ==========");	
		
		for (Position p : positions) {
			displayPosition(p);
		}
			
		System.out.println("====== END OF POSITIONS ==========");		
	}
	
	static void displayPosition(Position position) {
	
		System.out.println(position.getSecurity().getIsin());
		System.out.println(position.getPrice().getBid());
		System.out.println(position.getPrice().getOffer());
		System.out.println(position.getPrice().getCcy());
		System.out.println(position.getPrice().getDt());
		
		Security sec = position.getSecurity();
		if (sec instanceof Bond) {
			Bond b = (Bond) sec;
			System.out.println(b.getIssuer());
			System.out.println(b.getCoupon());
			System.out.println(b.getMaturityDate());
		} else if (sec instanceof Equity) {
			Equity eq = (Equity) sec;
			System.out.println(eq.getSymbol());
		} else if (sec instanceof Equity) {
			ETF etf = (ETF) sec;
			System.out.println(etf.getSymbol());
		} else if (sec instanceof FRA) {
			FRA fra = (FRA) sec;
			System.out.println(fra.getStart());
			System.out.println(fra.getDuration());
			System.out.println(fra.getRate());
		} else if (sec instanceof Future) {
			Future fut = (Future) sec;
			System.out.println(fut.getSymbol());
		}
	}
	
}