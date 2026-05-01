package BankingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankGUI implements BankUserInterface {

	private JFrame frame;
	
	private Client client;
	private Customer currCust;
	
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
		JButton createCustomerButton = new JButton("Create Customer");
		JButton exitButton = new JButton("Exit");
		
		// add the buttons to the panel
		mainPanel.add(customerLoginButton);
		mainPanel.add(employeeLoginButton);
		mainPanel.add(createCustomerButton);
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

		createCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCreateCustomer();
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
		
		//NOT FINISHED
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
	
	private void doDeposit() {
		JPanel panel = new JPanel(new GridLayout(2, 2));
		
		JTextField idField = new JTextField();
		JTextField amountField = new JTextField();

		panel.add(new JLabel("Account ID: "));			// maybe have a way for customers to check their account IDs
		panel.add(idField);
		panel.add(new JLabel("Ammount: "));
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
		//finish later
	}
	private void doTransfer() {
		//finish later
	}


	// using to test gui, not needed if we make a main driver.
	public static void main(String[] args) {
		BankGUI gui = new BankGUI();
		gui.processCommands();
		
	}
}