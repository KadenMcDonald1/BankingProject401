package BankingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankGUI implements BankUserInterface {

	private JFrame frame;
	
	private Client client;
	private Customer currCust;
	private Employee currEmp;
	
	public BankGUI () {
		client = new Client();
		client.connect("localhost", 1234);
		
	}

	public void processCommands() {
		createWindow();
	}

	//Create the frame, panel, and buttons for the starting window
	private void createWindow() {
		frame = new JFrame("Bank GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550, 850);
		frame.setLocationRelativeTo(null);
		JPanel mainPanel = new JPanel();				// create one panel to hold all the buttons

		// create 1 column for all the buttons in the login screen
		mainPanel.setLayout(new GridLayout(0, 1));
		
		JButton customerLoginButton = new JButton("Customer Login");
		JButton employeeLoginButton = new JButton("Teller Login");
		JButton exitButton = new JButton("Exit");
		
		// add the buttons to the panel
		mainPanel.add(customerLoginButton);
		mainPanel.add(employeeLoginButton);
		mainPanel.add(exitButton);

		// connect buttons to methods
		customerLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCustomerLogin();
			}
		});

		employeeLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doEmployeeLogin();
			}
		});


		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.close();
				System.exit(0);
			}
		});
		
		frame.setContentPane(mainPanel);
		frame.setVisible(true);


	}

	private void doCustomerLogin() {
		JPanel panel = new JPanel(new GridLayout(2, 2));			// panel to get log in info

		JTextField idField = new JTextField();
		JPasswordField pinField = new JPasswordField();				// makes password hidden when typing

		panel.add(new JLabel("Customer ID: "));
		panel.add(idField);
		panel.add(new JLabel("PIN: "));
		panel.add(pinField);

		int result = JOptionPane.showConfirmDialog(frame, panel, "Customer Login", JOptionPane.OK_CANCEL_OPTION);

		// if the user cancels or closes the window then return
		if (result != JOptionPane.OK_OPTION) {
			return;
		}

		// get the name and pin
		String id = idField.getText();
		String pin = new String(pinField.getPassword());

		Message loginMsg = new Message(userType.CUSTOMER, userStatus.UNDEFINED, commandType.LOGIN, commandStatus.UNDEFINED, null, null, id, pin);
		
		Message response = client.sendMessage(loginMsg);
		
		if (response == null) {
			JOptionPane.showMessageDialog(frame, "Error");
			return;
		}
		
		if (response.getCStatus() == commandStatus.SUCCESS) {
			currCust = response.getCurrCust();
			JOptionPane.showMessageDialog(frame, response.getText());		//if login worked show success message
			
			//close login frame and go to customer window
			frame.dispose();
			openCustomerWindow();
		} else {
			JOptionPane.showMessageDialog(frame, response.getText());		// otherwise show the failure message
		}
	}

	
	
	private void doEmployeeLogin() {
		JPanel panel = new JPanel(new GridLayout(2, 2));			// panel to get log in info

		JTextField idField = new JTextField();
		JPasswordField pinField = new JPasswordField();				// makes password hidden when typing

		panel.add(new JLabel("Employee ID:"));
		panel.add(idField);
		panel.add(new JLabel("PIN:"));
		panel.add(pinField);

		int result = JOptionPane.showConfirmDialog(frame, panel, "Employee Login", JOptionPane.OK_CANCEL_OPTION);

		// if the user cancels or closes the window then return
		if (result != JOptionPane.OK_OPTION) {
			return;
		}

		// get the id and pin
		String id = idField.getText();
		String pin = new String(pinField.getPassword());
		
		Message loginMsg = new Message(userType.EMPLOYEE, userStatus.UNDEFINED, commandType.LOGIN, commandStatus.UNDEFINED, null, null, id, pin);
		
		Message response = client.sendMessage(loginMsg);
		
		if (response == null) {
			JOptionPane.showMessageDialog(frame, "Error");
			return;
		}
		
		if (response.getCStatus() == commandStatus.SUCCESS) {
			currEmp = response.getActEmployee();
			JOptionPane.showMessageDialog(frame, response.getText());		//if login worked show success message
			
			//close login frame and go to customer window
			frame.dispose();
			openEmployeeWindow();
		} else {
			JOptionPane.showMessageDialog(frame, response.getText());		// otherwise show the failure message
		}
	}

	
	private void doCreateCustomer() {
		JPanel panel = new JPanel(new GridLayout(4, 2));

		
		JTextField nameField = new JTextField();
		JTextField pinField = new JTextField();
		JTextField secQField = new JTextField();
		JTextField secAField = new JTextField();

		panel.add(new JLabel("Name:"));
		panel.add(nameField);
		panel.add(new JLabel("PIN:"));
		panel.add(pinField);

		panel.add(new JLabel("Security Question:"));
		panel.add(secQField);
		panel.add(new JLabel("Security Answer:"));
		panel.add(secAField);

		int result = JOptionPane.showConfirmDialog(frame, panel, "Create Customer", JOptionPane.OK_CANCEL_OPTION);

		// if the user cancels or closes the window then return
		if (result != JOptionPane.OK_OPTION) {
			return;
		}

		// get the name, pin, and security info
		String name = nameField.getText();
		String pin = pinField.getText();
		String secQ = secQField.getText();
		String secA = secAField.getText();

		// NOT FINISHED
	}
	
	
	
	
	private void openCustomerWindow() {
		JFrame customerFrame = new JFrame();
		customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		customerFrame.setSize(550, 850);
		customerFrame.setLocationRelativeTo(null);
		JPanel customerPanel = new JPanel();				// create one panel to hold all the buttons
		
		// create 1 column for all the buttons
		customerPanel.setLayout(new GridLayout(0, 1));
		
		JButton depositButton = new JButton("Deposit");
		JButton withdrawButton = new JButton("Withdraw");
		JButton transferButton = new JButton("Transfer");
		JButton logoutButton = new JButton("Logout");
		
		// add the buttons to the panel
		customerPanel.add(depositButton);
		customerPanel.add(withdrawButton);
		customerPanel.add(transferButton);
		customerPanel.add(logoutButton);

		// connect buttons to methods
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doDeposit();
			}
		});

		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doWithdraw();
			}
		});

		transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doTransfer();
			}
		});

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message logoutMsg = new Message(userType.CUSTOMER, userStatus.LOGGED_IN, commandType.LOGOUT,
						commandStatus.UNDEFINED, currCust, null, currCust.getUserID() + "", "");

				Message response = client.sendMessage(logoutMsg);

				if (response == null) {
					JOptionPane.showMessageDialog(frame, "Error");
					return;
				}
				
				customerFrame.dispose();
				currCust = null;			//reset whoever was currently logged in
				createWindow();				// go back to log in screen
			}
		});
		
		customerFrame.setContentPane(customerPanel);
		customerFrame.setVisible(true);
		
		
	}
	
	private void openEmployeeWindow() {
		JFrame employeeFrame = new JFrame();
		employeeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		employeeFrame.setSize(550, 850);
		employeeFrame.setLocationRelativeTo(null);
		JPanel employeePanel = new JPanel();				// create one panel to hold all the buttons
		
		// create 1 column for all the buttons
		employeePanel.setLayout(new GridLayout(0, 1));
		
		JButton findCustomerButton = new JButton("Find Customer");
		JButton createCustomerButton = new JButton("Create Customer");
		JButton logoutButton = new JButton("Logout");
		
		// add the buttons to the panel
		employeePanel.add(findCustomerButton);
		employeePanel.add(createCustomerButton);
		employeePanel.add(logoutButton);

		// connect buttons to methods
		findCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doFindCustomer();
			}
		});

		createCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCreateCustomer();
			}
		});

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message logoutMsg = new Message(userType.EMPLOYEE, userStatus.LOGGED_IN, commandType.LOGOUT,
						commandStatus.UNDEFINED, null, currEmp, currEmp.getUserID() + "", "");

				Message response = client.sendMessage(logoutMsg);

				if (response == null) {
					JOptionPane.showMessageDialog(employeeFrame, "Error");
					return;
				}
				
				employeeFrame.dispose();
				currEmp = null;			//reset whoever was currently logged in
				createWindow();				// go back to log in screen
			}
		});
		
		employeeFrame.setContentPane(employeePanel);
		employeeFrame.setVisible(true);
		
		
	}
	
	
	
	
	
	private void doDeposit() {
		JPanel panel = new JPanel(new GridLayout(2, 2));
		
		JTextField idField = new JTextField();
		JTextField amountField = new JTextField();

		panel.add(new JLabel("Account ID: "));
		panel.add(idField);
		panel.add(new JLabel("Amount: "));
		panel.add(amountField);

		int result = JOptionPane.showConfirmDialog(null, panel, "Deposit", JOptionPane.OK_CANCEL_OPTION);

		// if the user cancels or closes the window then return
		if (result != JOptionPane.OK_OPTION) {
			return;
		}

		// get the id and amount from user as text
		String idText = idField.getText();
		String amountText = amountField.getText();
		
		try {
			int accountID = Integer.parseInt(idText);
			double amount = Double.parseDouble(amountText);
			
			// find the matching account 
			Account account = currCust.getAccountByID(accountID);
			
			//if there isnt an account with that ID then return
			if (account == null) {
				JOptionPane.showMessageDialog(null, "Account not found");
				return;
			} else if (account.deposit(amount)) {	// show success message if the deposit returns true otherwise the deposit failed
				JOptionPane.showMessageDialog(null, "Deposit Successful!");
			} else {
				JOptionPane.showMessageDialog(null, "Deposit Failed");
			}
		} catch (NumberFormatException e) {
			
		}
	}
	private void doWithdraw() {
		JPanel panel = new JPanel(new GridLayout(2, 2));
		
		JTextField idField = new JTextField();
		JTextField amountField = new JTextField();

		panel.add(new JLabel("Account ID: "));
		panel.add(idField);
		panel.add(new JLabel("Amount: "));
		panel.add(amountField);

		int result = JOptionPane.showConfirmDialog(null, panel, "Withdraw", JOptionPane.OK_CANCEL_OPTION);

		// if the user cancels or closes the window then return
		if (result != JOptionPane.OK_OPTION) {
			return;
		}

		// get the id and amount from user as text
		String idText = idField.getText();
		String amountText = amountField.getText();
		
		try {
			int accountID = Integer.parseInt(idText);
			double amount = Double.parseDouble(amountText);
			
			// find the matching account 
			Account account = currCust.getAccountByID(accountID);
			
			//if there isnt an account with that ID then return
			if (account == null) {
				JOptionPane.showMessageDialog(null, "Account not found");
				return;
			} else if (account.withdrawal(amount)) {	// show success message if the deposit returns true otherwise the deposit failed
				JOptionPane.showMessageDialog(null, "Withdrawal Successful!");
			} else {
				JOptionPane.showMessageDialog(null, "Withdrawal Failed");
			}
		} catch (NumberFormatException e) {
			
		}
	}
	
	private void doTransfer() {
		JPanel panel = new JPanel(new GridLayout(3, 2));
		
		JTextField fromField = new JTextField();
		JTextField toField = new JTextField();
		JTextField amountField = new JTextField();


		panel.add(new JLabel("From Account ID: "));	
		panel.add(fromField);
		panel.add(new JLabel("to Account ID: "));	
		panel.add(toField);
		panel.add(new JLabel("Amount: "));
		panel.add(amountField);

		int result = JOptionPane.showConfirmDialog(null, panel, "Transfer", JOptionPane.OK_CANCEL_OPTION);

		// if the user cancels or closes the window then return
		if (result != JOptionPane.OK_OPTION) {
			return;
		}

		String fromText = toField.getText();
		String toText = fromField.getText();
		String amountText = amountField.getText();
		
		try {
			int fromID = Integer.parseInt(fromText);
			int toID = Integer.parseInt(toText);

			double amount = Double.parseDouble(amountText);
			
			if (fromID == toID) {
				JOptionPane.showMessageDialog(null, "ERROR: Choose two different accounts");
			}
			
			// find the matching account 
			Account fromAccount = currCust.getAccountByID(fromID);
			Account toAccount = currCust.getAccountByID(toID);


			//if there isn't an account with that ID then return
			if (fromAccount == null || toAccount == null) {
				JOptionPane.showMessageDialog(null, "ERROR: One or both accounts were not found");
				return;
			}
			
			boolean success = currCust.transferBetweenOwnedAccounts(fromID, toID, amount);

			if (success) {	// show success message if the deposit returns true otherwise the deposit failed
				JOptionPane.showMessageDialog(null, "Transfer Successful!");
			} else {
				JOptionPane.showMessageDialog(null, "Transfer Failed");
			}
		} catch (NumberFormatException e) {
			
		}
	}


	private void doFindCustomer() {
		JPanel panel = new JPanel(new GridLayout(3, 2));

		JTextField idField = new JTextField();
		JTextField pinField = new JTextField();
		JTextField secAField = new JTextField();

		panel.add(new JLabel("Customer ID:"));
		panel.add(idField);

		panel.add(new JLabel("PIN:"));
		panel.add(pinField);

		panel.add(new JLabel("Security Answer:"));
		panel.add(secAField);

		int result = JOptionPane.showConfirmDialog(null, panel, "Employee Customer Login", JOptionPane.OK_CANCEL_OPTION);

		if (result != JOptionPane.OK_OPTION) {
			return;
		}

		String id = idField.getText().trim();
		String pin = pinField.getText().trim();
		String secA = secAField.getText().trim();

		String combined = pin + "|" + secA;

		// build message and send request
		Message msg = new Message(userType.CUSTOMER, userStatus.UNDEFINED, commandType.LOGIN, commandStatus.UNDEFINED, null, currEmp, id, combined);
		Message response = client.sendMessage(msg);

		if (response == null) {
			JOptionPane.showMessageDialog(null, "Error");
			return;
		}

		if (response.getCStatus() == commandStatus.SUCCESS) {

			currCust = response.getCurrCust();

			JOptionPane.showMessageDialog(null, response.getText());

			openEmployeeControlWindow();

		} else {
			JOptionPane.showMessageDialog(null, response.getText());
		}
	}
	
	private void openEmployeeControlWindow() {
		// create a new window for the employee working on a customer
		JFrame employeeControlFrame = new JFrame("Employee Control Window");
		employeeControlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		employeeControlFrame.setSize(550, 850);
		employeeControlFrame.setLocationRelativeTo(null);

		JPanel employeeControlPanel = new JPanel();
		employeeControlPanel.setLayout(new GridLayout(0, 1));

		JButton depositButton = new JButton("Deposit");
		JButton withdrawButton = new JButton("Withdraw");
		JButton transferButton = new JButton("Transfer");
		JButton freezeButton = new JButton("Freeze Account");
		JButton thawButton = new JButton("Thaw Account");
		JButton backButton = new JButton("Back");

		employeeControlPanel.add(depositButton);
		employeeControlPanel.add(withdrawButton);
		employeeControlPanel.add(transferButton);
		employeeControlPanel.add(freezeButton);
		employeeControlPanel.add(thawButton);
		employeeControlPanel.add(backButton);

		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doDeposit();
			}
		});

		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doWithdraw();
			}
		});

		transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doTransfer();
			}
		});

		// freeze one of the selected customer's accounts
		freezeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog(employeeControlFrame, "Enter Account ID to freeze:");

				// stop if user cancels
				if (input == null) {
					return;
				}

				try {
					int accountID = Integer.parseInt(input.trim());

					// find the account by ID from the currently selected customer
					Account account = currCust.getAccountByID(accountID);

					if (account == null) {
						JOptionPane.showMessageDialog(employeeControlFrame, "Account not found.");
						return;
					}

					account.freeze();
					JOptionPane.showMessageDialog(employeeControlFrame, "Account frozen successfully.");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(employeeControlFrame, "Please enter a valid account ID.");
				}
			}
		});

		thawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog(employeeControlFrame, "Enter Account ID to thaw:");

				if (input == null) {
					return;
				}

				try {
					int accountID = Integer.parseInt(input.trim());

					// find the account by id
					Account account = currCust.getAccountByID(accountID);

					if (account == null) {
						JOptionPane.showMessageDialog(employeeControlPanel, "Account not found.");
						return;
					}

					account.thaw();
					JOptionPane.showMessageDialog(employeeControlPanel, "Account thawed successfully.");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(employeeControlPanel, "Please enter a valid account ID.");
				}
			}
		});

		// keep the employee logged in but log the customer out
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message logoutMsg = new Message(userType.CUSTOMER, userStatus.LOGGED_IN, commandType.LOGOUT, commandStatus.UNDEFINED,
						currCust, currEmp, currCust.getUserID() + "", "");

				Message response = client.sendMessage(logoutMsg);

				if (response == null) {
					JOptionPane.showMessageDialog(employeeControlFrame, "Error");
					return;
				}

				// forget the current customer and close the window
				currCust = null;
				employeeControlFrame.dispose();
			}
		});

		employeeControlFrame.setContentPane(employeeControlPanel);
		employeeControlFrame.setVisible(true);
	}
	
	
	// using to test gui, not needed if we make a main driver.
	public static void main(String[] args) {
		BankGUI gui = new BankGUI();
		gui.processCommands();
		
	}
}