package BankingSystem;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class Server {
	
	private static int numCustomers = 0;
	private static int numEmployees = 0;
	private static int totalUserNum = 0;
	
	public static String[][] LoadCustomers(){
		String[] lines = null;
		String[][] ListOCustomers = null;
		try {
			Path path = Paths.get("CustomerList.txt");
			String output = Files.readString(path);
			lines = output.split("\n");

			numCustomers = lines.length - 3;
			lines[0] = "NumberOfCustomers " + numCustomers;

			ListOCustomers = new String[numCustomers][7];

			for (int i = 3; i < lines.length; i++) {
				String[] temp = lines[i].split(",");
				for (int j = 0; j < 7; j++) { // need to test the 7 and 3's positions here...
					ListOCustomers[i-3][j] = temp[j];
				}
			}
			Files.write(path, Arrays.asList(lines));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ListOCustomers;
	}
	// Will need to call these 2 ^v methods multiple times through the running process to double check
	// who is currently loggfed in and logged out at that time. (it will most likely need to be 
	// done when login requests are recieved and transfer requsts are recieved)
	public static String[][] LoadEmployees() {
		String[] lines = null;
		String[][] ListOEmployees = null;
		try {
			Path path = Paths.get("EmployeeList.txt");
			String output = Files.readString(path);
			lines = output.split("\n");

			numEmployees = lines.length - 3;
			lines[0] = "NumberOfEmployees " + numEmployees;

			ListOEmployees = new String[numEmployees][3];

			for (int i = 3; i < lines.length; i++) {
				String[] temp = lines[i].split(",");
				for (int j = 0; j < 3; j++) {
					ListOEmployees[i-3][j] = temp[j];
				}
			}
			Files.write(path, Arrays.asList(lines));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ListOEmployees;
	}
	
	public static int CalcTotalUsers() { // Need to figure out what im doing with this.....
		return totalUserNum=numEmployees+numCustomers;
	}													
	
	public static String[] checkIDAndPin(String ID, String pin, userType UType) {// can be used for both employees and customers.
		
		String[][] list = null;
		
		int num;
		if (UType == userType.CUSTOMER) {
			list = LoadCustomers();
			num = numCustomers;
		}
		else if (UType == userType.EMPLOYEE) {
			list = LoadEmployees();
			num = numEmployees;
		}
		else {
			return null;
		}
		
		for (int i = 0; i < num; i++) {
			if (ID.equals(list[i][0])) {
				if (pin.equals(list[i][1])) {
					String[] currUserInfo = list[i];
					return currUserInfo;
				}
			}
		}
		return null;
	}
	
	public static String[] checkIDAndPinAndSecA(String ID, String pin, String securityA) {
		
		String[][] list = LoadCustomers();
		int num = numCustomers;
		
		for (int i = 0; i < num; i++) {
			if (ID.equals(list[i][0])) {
				if (pin.equals(list[i][1])) {
					if (securityA.equals(list[i][5])) {
						String[] currUserInfo = list[i];
						return currUserInfo;
					}
				}
			}
		}
		return null;
	}
	
	public static String[][] loadAccounts(String ID) {
		
		String[] lines = null; 
		String[] temp = null;
		try { // transfer the text from the file to a string to an array. 
			String output = Files.readString(Paths.get(ID+".txt"));
			lines = output.split("\n"); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		temp = lines[0].split(" ");
		int num = Integer.parseInt(temp[1]);
		
		String[][] ListOAccounts = new String[num][6];
		
		for(int i = 3; i < (num+3); i++) {
			temp = lines[i].split(",");
			for(int j = 0; j < 6; j++)
			ListOAccounts[i-3][j]=temp[j];
		}
		return ListOAccounts;
	}
	
	public static Customer createExistingCustomerObject(String id, String a, String b, String c, String d, String[][] accList) {
		Customer temp = new Customer(Integer.parseInt(id),a,b,c,d);
		
		for (int i = 0; i < accList.length; i++) {
			accountType type = accountType.UNDEFINED;
			if (accList[i][1].equals("CHECKINGS")) {
				type = accountType.CHECKINGS;
			}
			else if (accList[i][1].equals("SAVINGS")) {
				type = accountType.SAVINGS;
			}
			else if (accList[i][1].equals("CREDIT")) {
				type = accountType.CREDIT;
			}
			temp.addAccount(type,Double.parseDouble(accList[i][3]));
		}
		return temp;
	}
	
	public static void editUserStatus(String ID, String status, String file) {
		
		try {
			Path path = Paths.get(file);

			String output = Files.readString(path);
			String[] lines = output.split("\n");

			for (int i = 3; i < lines.length; i++) {
				String[] chunks = lines[i].split(",");
				
				if (chunks[0].equals(ID)) {
					chunks[2] = status;
					lines[i] = String.join(",", chunks);
					break;
				}
			}
			Files.write(path, Arrays.asList(lines));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void PushEditedCustomerInfo(Customer customer) {
		try {
			Path path = Paths.get("CustomerList.txt");

			String output = Files.readString(path);
			String[] lines = output.split("\n");

			for (int i = 3; i < lines.length; i++) {
				String[] chunks = lines[i].split(",");
				
				if (chunks[0].equals(customer.getUserID()+"")) {
					chunks[1] = customer.getPin().trim();
					chunks[2] = customer.getIsLoggedIn()+"";
					chunks[3] = customer.getCustName().trim();
					chunks[4] = customer.getSecurityQ().trim();
					chunks[5] = customer.getSecurityA().trim();
					chunks[6] = customer.getNumAccounts()+"";
					lines[i] = String.join(",", chunks);
					break;
				}
			}
			Files.write(path, Arrays.asList(lines));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void PushNewCustomer(Customer customer) {
		try {
			Path path = Paths.get("CustomerList.txt");
			
			String newCustLine = "\n"+customer.getUserID()+","+customer.getPin().trim()+","+customer.getIsLoggedIn()+
									","+customer.getCustName().trim()+","+customer.getSecurityQ().trim()+","+
									customer.getSecurityA().trim()+","+customer.getNumAccounts();
				
			Files.writeString(path, newCustLine, StandardOpenOption.APPEND);
			
			LoadCustomers();
			CalcTotalUsers();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void PushAccountInfo(Customer cust) {
		String userID = cust.getUserID()+"";
		Account[] accounts = cust.getAccounts();
		
		String pushVal = "NumberOfAccounts "+accounts.length+"\nformat: AccountID,accountType,isFrozen,balance,"
						+ "withdrawalLimit/creditLimit,interestRate\n";
		for (int i = 0; i < accounts.length; i++) {
			
			accountType type = accounts[i].getAccType(); 
			if (type == accountType.CHECKINGS) {
				pushVal += "\n"+i+",CHECKINGS,"+accounts[i].getIsFrozen()+","+accounts[i].getBalance()+",1000,null";
			}
			else if (type == accountType.SAVINGS) {
				pushVal += "\n"+i+",SAVINGS,"+accounts[i].getIsFrozen()+","+accounts[i].getBalance()+",500,null";
			}
			else if (type == accountType.CREDIT) {
				pushVal += "\n"+i+",CREDIT,"+accounts[i].getIsFrozen()+","+accounts[i].getBalance()+",-2000,0.02";
			}
		}
		File file = new File(userID+".txt");
		try {
			FileWriter myWriter = new FileWriter(file, false); //overwrites or creates a new file.
				myWriter.write(pushVal);
				
			myWriter.close();
			
		} catch (IOException e) {
		}
	}
	
	public static void RemoveCustomer(Customer customer){

		try {
			Path path = Paths.get("CustomerList.txt");

			String output = Files.readString(path);
			String[] lines1 = output.split("\n");
			String[] lines2 = new String[lines1.length-1];
			int removalIndex = 0;

			for (int i = 3; i < lines1.length; i++) {
				String[] chunks = lines1[i].split(",");
				
				if (chunks[0].equals(customer.getUserID()+"")) {
					removalIndex = i;
					break;
				}
			}
			int j = 0;
			for (int i = 0; i < lines1.length; i++) {
				if(i != removalIndex) {
					lines2[j] = lines1[i];
					j++;
				}
			}
			Files.write(path, Arrays.asList(lines2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean getIsLoggedIn(User person, userType type) {
		String[][] list = null;
		int num;
		if (type == userType.CUSTOMER) {
			list = LoadCustomers();
			num = numCustomers;
		}
		else {
			list = LoadEmployees();
			num = numEmployees;
		}
		
		
		for (int i = 0; i < num; i++) {
			if ((person.getUserID()+"").equals(list[i][0])) {
				if (list[i][2].equals("true")) {
					return true;// user is logged in.
				}
				else {
					return false;// user is not logged in.
				}
			}
		}
		return true; //can't find user.
	}
	
	public static void setNewUserIDNum(String ID, String file) {
		
		try {
			Path path = Paths.get(file);

			String output = Files.readString(path);
			String[] lines = output.split("\n");
			
			String[] chunks = lines[lines.length-1].split(",");
			
			chunks[0] = ID;
			lines[lines.length-1] = String.join(",", chunks);

			Files.write(path, Arrays.asList(lines));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int getCurrUserIDCounterPos() {
		try {
			Path path = Paths.get("CustomerList.txt");
			String output = Files.readString(path);
			String[] lines = output.split("\n");
			String[] chunks = lines[lines.length-1].split(",");
			
			int highestCustID =Integer.parseInt(chunks[0]);
			
			path = Paths.get("EmployeeList.txt");
			output = Files.readString(path);
			lines = output.split("\n");
			chunks = lines[lines.length-1].split(",");

			int highestEmplID =Integer.parseInt(chunks[0]);
			
			if (highestCustID>highestEmplID) {
				return highestCustID++;
			}
			else if (highestEmplID>highestCustID) {
				return highestEmplID++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0; //if zero is returned there was an error.
	}
	
	
	
	
	
	
	
	public static void main(String[] args)
	{
		ServerSocket server = null;
		try {
			// server is listening on port 1234
			server = new ServerSocket(1234);
			server.setReuseAddress(true);
			while (true) {
				Socket client = server.accept();
				System.out.println("New client connected " + client.getInetAddress().getHostAddress());
				ClientHandler clientSock = new ClientHandler(client);
				new Thread(clientSock).start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (server != null) {
				try {
					server.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ClientHandler class
	private static class ClientHandler implements Runnable {
		private final Socket clientSocket;
		// Constructor
		public ClientHandler(Socket socket)
		{
			this.clientSocket = socket;
		}
		public void run()
		{
			ObjectInputStream objectInputStream = null;
			ObjectOutputStream objectOutputStream = null;
			
			String[][] customerList = LoadCustomers();
			String[][] employeeList = LoadEmployees();
			int numTotalUsers = CalcTotalUsers();
			
			try {
				
				objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
				objectOutputStream.flush();
				objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
				
				while (true) {
					Message msg = (Message) objectInputStream.readObject();
					Message response = null;
					customerList = LoadCustomers();// Double check to make sure they are not logged in after recieving
					employeeList = LoadEmployees();// a login message.
					
					if (msg.getCType() == commandType.LOGIN && msg.getUType() == userType.CUSTOMER) {
						
						String[] currCustInfo = checkIDAndPin(msg.GetUserID(), msg.getText(), userType.CUSTOMER);
						
						if (currCustInfo!=null) {//Customer login sucsessful.
							
							String[][] custAccountList = loadAccounts(currCustInfo[0]);
							
							editUserStatus(msg.GetUserID(), "true", "CustomerList.txt");//changes status of user to logged in on the files.
							customerList = LoadCustomers();//refresh the list after changing something on it.
							
							Customer currCustObj = createExistingCustomerObject(currCustInfo[0],currCustInfo[1],currCustInfo[4], //creates customer and their accounts.
																				currCustInfo[5],currCustInfo[3],custAccountList);
							//send back a customer object and accounts
							response = new Message(userType.CUSTOMER,userStatus.lOGGED_IN,commandType.LOGIN,commandStatus.SUCCESS,
													currCustObj,null,currCustInfo[0],"ATM Login Succsessful, Welcome "+currCustInfo[3]+"\n");
							objectOutputStream.writeObject(response);
							objectOutputStream.flush();
							
							boolean cont = true;
							while (cont) {

								msg = (Message) objectInputStream.readObject();
								
								if (msg.getCType() == commandType.LOGOUT) {//customer logging out
									
									editUserStatus(msg.GetUserID(), "false", "CustomerList.txt");
									customerList = LoadCustomers();
									
									response = new Message(userType.CUSTOMER,userStatus.LOGGED_OUT,commandType.LOGOUT,commandStatus.SUCCESS,
															msg.getCurrCust(),null,currCustInfo[0],currCustInfo[3]+"Customer Logged Out Sucsessfully.\n");
									objectOutputStream.writeObject(response);
									objectOutputStream.flush();
									
									PushAccountInfo(msg.getCurrCust());//puts their account info onto their account file.
									
									cont = false;
								}
								else {// commandType is wrong
									response = new Message(userType.CUSTOMER,userStatus.lOGGED_IN,msg.getCType(),commandStatus.FAILURE,
															currCustObj,null,currCustInfo[0],"Incorrect Message Type\n");
									objectOutputStream.writeObject(response);
									objectOutputStream.flush();
								}	
								customerList = LoadCustomers();
								employeeList = LoadEmployees();
							}
						}
						else { //Customer login not sucsessful.
							response = new Message(userType.CUSTOMER,userStatus.UNDEFINED,commandType.LOGIN,commandStatus.FAILURE,
									null,null,null,"ATM Login Failure\n");
							objectOutputStream.writeObject(response);
							objectOutputStream.flush();
						}
					}
					else if (msg.getCType() == commandType.LOGIN && msg.getUType() == userType.EMPLOYEE) {//employee logging in
						
						String[] currEmplInfo = checkIDAndPin(msg.GetUserID(), msg.getText(), userType.EMPLOYEE);
						
						if (currEmplInfo!=null) {
							
							editUserStatus(msg.GetUserID(), "true", "EmployeeList.txt");
							employeeList = LoadEmployees();
						
							Employee currEmplObj = new Employee(Integer.parseInt(currEmplInfo[0]),currEmplInfo[1]);
							
							response = new Message(userType.EMPLOYEE,userStatus.lOGGED_IN,commandType.LOGIN,commandStatus.SUCCESS,
													null,currEmplObj,currEmplInfo[0],"Teller Employee Login Succsessful");
							objectOutputStream.writeObject(response);
							objectOutputStream.flush();
			
							boolean cont = true;
							while (cont) {

								msg = (Message) objectInputStream.readObject();
								
								if (msg.getCType() == commandType.LOGIN) {
									
									String[] passkeys = msg.getText().split("\\|");
									String[] currCustInfo = checkIDAndPinAndSecA(msg.GetUserID(), passkeys[0], passkeys[1]);
									
									if (currCustInfo!=null) {//Teller Customer login sucsessful.
										 
										String[][] custAccountList = loadAccounts(currCustInfo[0]);
										
										editUserStatus(msg.GetUserID(), "true", "CustomerList.txt");
										customerList = LoadCustomers();
										
										Customer currCustObj = createExistingCustomerObject(currCustInfo[0],currCustInfo[1],currCustInfo[4],
																							currCustInfo[5],currCustInfo[3],custAccountList);
										//send back a customer object and accounts
										response = new Message(userType.CUSTOMER,userStatus.lOGGED_IN,commandType.LOGIN,commandStatus.SUCCESS,
																currCustObj,currEmplObj,currCustInfo[0],"Teller Customer Login Succsessful\n");
										objectOutputStream.writeObject(response);
										objectOutputStream.flush();
										
										boolean cont2 = true;
										while (cont2) {

											msg = (Message) objectInputStream.readObject();
											
											if (msg.getCType() == commandType.LOGOUT) {//Teller Customer logging out.
												
												editUserStatus(msg.GetUserID(), "false", "CustomerList.txt");
												
												response = new Message(userType.CUSTOMER,userStatus.LOGGED_OUT,commandType.LOGOUT,commandStatus.SUCCESS,
																		msg.getCurrCust(),currEmplObj,currCustInfo[0],"Teller Customer Logged Out Sucsessfully.\n");
												objectOutputStream.writeObject(response);
												objectOutputStream.flush();
												
												PushAccountInfo(msg.getCurrCust());
												PushEditedCustomerInfo(msg.getCurrCust());
												customerList = LoadCustomers();
												
												cont2 = false;
											}
											else if (msg.getCType() == commandType.LOOKUP_CUSTOMER) {//Customer to Customer Transfer
												
												Customer recievingCust = null;
												if (!getIsLoggedIn(msg.getCurrCust(),userType.CUSTOMER)) {//target is not logged in
													recievingCust = msg.getCurrCust();
													
													editUserStatus(msg.getCurrCust().getUserID()+"", "true", "CustomerList.txt");
													
													response = new Message(userType.CUSTOMER,userStatus.lOGGED_IN,commandType.LOOKUP_CUSTOMER,commandStatus.SUCCESS,
																			recievingCust,currEmplObj,"","Customer is able to recieve transfer.\n");
													
													msg = (Message) objectInputStream.readObject();
													
													if (msg.getCStatus() == commandStatus.SUCCESS) {//if transaction was sucsessful then push the changes to recieveingCust's accounts.
														PushAccountInfo(msg.getCurrCust());
														response = new Message(userType.CUSTOMER,userStatus.LOGGED_OUT,commandType.TRANSACTION,commandStatus.SUCCESS,
																				recievingCust,currEmplObj,"","Customer recieved transfer.\n");
													}
													else {
														response = new Message(userType.CUSTOMER,userStatus.LOGGED_OUT,commandType.TRANSACTION,commandStatus.FAILURE,
																				recievingCust,currEmplObj,"","Customer did not recieve transfer.\n");
													}
													
													editUserStatus(msg.getCurrCust().getUserID()+"", "false", "CustomerList.txt");
													
													objectOutputStream.writeObject(response);
													objectOutputStream.flush();
													
													// The client should continue to send messages with msg.getCurrCust() equaling the origional 
													// signed in customer in every other scenario/sequence of messages to the server.
													
												}
												else {
													response = new Message(userType.CUSTOMER,userStatus.LOGGED_OUT,commandType.LOOKUP_CUSTOMER,commandStatus.FAILURE,
															recievingCust,currEmplObj,"","Customer is not able to recieve transfer.\n");
													objectOutputStream.writeObject(response);
													objectOutputStream.flush();
												}
												
												response = new Message();
												objectOutputStream.writeObject(response);
												objectOutputStream.flush();
	
											}
											else if (msg.getCType() == commandType.DELETE_CUSTOMER) {//Delete Entire Customer.
											
												RemoveCustomer(msg.getCurrCust());
												
												response = new Message(userType.CUSTOMER,userStatus.UNDEFINED,commandType.DELETE_CUSTOMER,commandStatus.SUCCESS,
																		null,currEmplObj,null,"Customer "+currCustObj.getUserID()+" Deleted Sucsessfully.\n");
												objectOutputStream.writeObject(response);
												objectOutputStream.flush();
												
												//currCustObj = null; //could do this to make a 'clean slate'...
												customerList = LoadCustomers();
												
											}
											
											// Options to remove singluar accounts from customers can be pushed durring logout and do not require
											// their own calls and changes in the server, although when these changes are pushed, the numAccounts 
											// on the CustomersList will need to be adjusted, not only the inividual customer account info.
											
											else {// commandType is wrong
												response = new Message(userType.CUSTOMER,userStatus.lOGGED_IN,msg.getCType(),commandStatus.FAILURE,
																		msg.getCurrCust(),currEmplObj,currCustInfo[0],"Incorrect Message Type\n");
												objectOutputStream.writeObject(response);
												objectOutputStream.flush();
											}	
											customerList = LoadCustomers();
											employeeList = LoadEmployees();
										}
									}
									else {//Teller Customer login unsucsessful.
										response = new Message(userType.CUSTOMER,userStatus.UNDEFINED,commandType.LOGIN,commandStatus.FAILURE,
												null,currEmplObj,null,"Teller Customer Login Failure\n");
										objectOutputStream.writeObject(response);
										objectOutputStream.flush();
									}
								}
								else if (msg.getCType() == commandType.CREATE_CUSTOMER) { 
									
									// Take in the customers and create his account file and 
									// then append the customer onto the CustomerList.txt.
									
									// After that then call a method to change the customers ID in file to the highestIDVal+1.
									
									PushAccountInfo(msg.getCurrCust());
									PushNewCustomer(msg.getCurrCust());
									setNewUserIDNum(getCurrUserIDCounterPos()+"","CustomerList.txt");
									
									response = new Message(userType.CUSTOMER,userStatus.UNDEFINED,commandType.CREATE_CUSTOMER,commandStatus.SUCCESS,
											null,currEmplObj,null,"Cusotmer Sucsessfully Created, Returning to Login Screen\n");
									
									objectOutputStream.writeObject(response);
									objectOutputStream.flush();
								}
								else if (msg.getCType() == commandType.LOGOUT) {//employee logging out
									editUserStatus(msg.GetUserID(), "false", "EmployeeList.txt");
									employeeList = LoadEmployees();
									
									response = new Message(userType.EMPLOYEE,userStatus.LOGGED_OUT,commandType.LOGOUT,commandStatus.SUCCESS,
															null,currEmplObj,currEmplInfo[0],"Employee Logged Out Sucsessfully.\n");
									objectOutputStream.writeObject(response);
									objectOutputStream.flush();
									
									cont = false;
								}
								else {//commandType is wrong
									response = new Message(userType.EMPLOYEE,userStatus.lOGGED_IN,msg.getCType(),commandStatus.FAILURE,
															null,currEmplObj,currEmplInfo[0],"Incorrect Message Type\n");
									objectOutputStream.writeObject(response);
									objectOutputStream.flush();
								}	
								customerList = LoadCustomers();
								employeeList = LoadEmployees();
							}
						}
						else { //Employee login not sucsessful.
							response = new Message(userType.CUSTOMER,userStatus.UNDEFINED,commandType.LOGIN,commandStatus.FAILURE,
									null,null,null,"ATM Login Failure\n");
							objectOutputStream.writeObject(response);
							objectOutputStream.flush();
						}
					}
					else {//Starting message was not of type login
						response = new Message(msg.getUType(),msg.getUStatus(),msg.getCType(),commandStatus.FAILURE,
												null,null,null,"Incorrect Message Type\n");
						objectOutputStream.writeObject(response);
						objectOutputStream.flush();
					}
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (objectInputStream != null) {
						objectInputStream.close();
					}
					if (objectOutputStream != null) {
						objectOutputStream.close();
						clientSocket.close();
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
