import java.util.Scanner;
import java.util.InputMismatchException;  
import java.util.ArrayList;
import java.io.*;

public class OnlineShoppingSimulator {
	
	public static void main(String[] args) {
		shoppingSystem();
		infoSystem();
	}

static void shoppingSystem() {
	Scanner scanner = new Scanner(System.in);
	System.out.println("Welcome to the online shop! ");
	System.out.println("-----------------------------");
	System.out.println("Items: ");
	int length = 5;
	String[] items = {"Earbuds", "Speakers", "Headphones", "Phones", "Laptops"};
	double[] prices = {2.00,75.00,20.00,700.00, 1100.00};
	ArrayList<String> itemList = new ArrayList<String>();
	ArrayList<Double> priceList = new ArrayList<Double>();
	for(int i = 0; i<length;i++) {
		System.out.println("ID:" + (int)(i+1) + " -"
				+ "" + items[i] + " ($" + prices[i] + ")");
	}
	String answer = "yes";
	int itemBought;
	String shoppingCart = "s";
	
	do {
		System.out.println("Would you like to buy something? Please put in yes or no: ");
		answer = scanner.next();
		if(answer.equalsIgnoreCase("yes")) {
			System.out.println("What would you like to buy? Please enter your item ID as listed on the menu");
			try {
			itemBought = scanner.nextInt();
			if(itemBought>= 1 && itemBought<=5) {
				itemList.add(items[itemBought-1]);
				priceList.add(prices[itemBought-1]);
			}
			else {
				System.out.println("ID's are from 1-5. Please put in a valid ID number");
			
			}
			}
			catch(InputMismatchException e) {
				System.out.println("This is not a number. Try again.");
				}
			
			answer = scanner.nextLine();
		
	}
	else if(answer.equalsIgnoreCase("no")) {
		break;
	}
	else {
		System.out.println("Invalid input.");
	}
		//onnly ask to see shopping cart if there are items innside
		// &&
		if(itemList.size() >0) {
			System.out.println("Would you like to see your shopping cart?");
			shoppingCart = scanner.next();
			if(shoppingCart.equalsIgnoreCase("yes")) {
				System.out.println("Items bought: ");
				for(int i =0; i<priceList.size(); i++) {
					System.out.println(itemList.get(i) + ": $" + priceList.get(i));
				}
				
			}
			else if(shoppingCart.equalsIgnoreCase("no")) {
				continue;
			}
			else {
				System.out.println("Invalid answer.");	
			}
			
			}

	}while(!(answer.equalsIgnoreCase("no")));
	
	int sum = 0;
	for(double price: priceList) {
		sum += price;
	}
	if(sum != 0) {
		System.out.println("Your total is $" + sum + ".");
	}
	else {
		System.out.println("Thank you for visiting.");
		System.exit(0);
	}
}

static long creditCardSystem() {
	//the credit card algorithms is based on this video: https://www.youtube.com/watch?v=p_mO_jpihiw
	Scanner scanner = new Scanner(System.in);
	long creditCardNum;
	String result = "";
	do {
		//will naturally throw an exception if you put in some string
		System.out.println("Please enter your credit card number: ");
		creditCardNum = scanner.nextLong();
	
	
	long workingCC = creditCardNum;
	long sum =  0;
	int counter = 0;
	long divisor = 10;
	
	//case 1
	while(workingCC>0) {
		long last = workingCC % 10;
		sum += last;
		workingCC /=100;
		
	}
	//case 2
	workingCC = creditCardNum /10; 
	while(workingCC >0) {
		long last = workingCC % 10;
		long lastDigTwo = last *2;
		sum  = sum + (lastDigTwo% 10) + (lastDigTwo / 10)  ;
		workingCC /=100;
	}
	workingCC = creditCardNum;
	while(workingCC != 0) {
		workingCC /=10;
		counter++;
	} 
	for(int i = 0; i<counter - 2; i++ ) {
		divisor *= 10;
	}
	long firstDig = creditCardNum /divisor;
	long firstTwo = creditCardNum / (divisor/10); 
	
	if(sum % 10 == 0) {
		if(firstDig == 4 && (counter == 13 || counter == 16)) {
			result = "VALID: VISA";
		}
		else if((firstDig == 34 || firstDig == 37) && counter == 15) {
			result = "VALID: AMEX"; 
		}
		else if((firstTwo > 50 && firstTwo < 56) && counter == 16) {
			result = "VALID: MASTERCARD";
		}
		else {
			result = "INVALID CARD";
		}
	}
	else {
		result = "INVALID CARD";
	}
	System.out.println(result);
	if(!(result.equals("INVALID CARD"))) {
		System.out.println("Thank you for purchasing. Goodbye!");
		return creditCardNum;
	}
	}while(creditCardNum<=0 || result.equals("INVALID CARD"));
	return 0;
}

static void infoSystem() {
	Scanner scanner = new Scanner(System.in);
	String name;
	String address;
	long num= creditCardSystem();
	if(num == 0) {
		creditCardSystem();
	}
	else {
		System.out.print("What is your name: ");
		name = scanner.nextLine();
		System.out.println("What is your address: ");
		address = scanner.nextLine();
		try {
		BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/g_zhu725/Desktop/info/output.txt", true));
		bw.write("Customer name: " + name + "\n");
		bw.write("Customer address: " + address + "\n");
		bw.write("Customer credit card number: " + num + "\n" );
		bw.write("\n");
		bw.close();
		}
		catch(Exception e) {
			return;
		}
	}
}
}